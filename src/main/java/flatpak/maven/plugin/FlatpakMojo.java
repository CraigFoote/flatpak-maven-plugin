package flatpak.maven.plugin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Developer;
import org.apache.maven.model.License;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.ArtifactRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import flatpak.maven.plugin.exceptions.MetaInfoException;
import flatpak.maven.plugin.models.Branding;
import flatpak.maven.plugin.models.ContentRating;
import flatpak.maven.plugin.models.DesktopEntry;
import flatpak.maven.plugin.models.Image;
import flatpak.maven.plugin.models.Launchable;
import flatpak.maven.plugin.models.Manifest;
import flatpak.maven.plugin.models.MetaInfo;
import flatpak.maven.plugin.models.Module;
import flatpak.maven.plugin.models.Release;
import flatpak.maven.plugin.models.Screenshot;
import flatpak.maven.plugin.models.Source;
import flatpak.maven.plugin.models.Url;

@Mojo(threadSafe = true, name = "generate", defaultPhase = LifecyclePhase.PACKAGE, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresProject = true)
public class FlatpakMojo extends AbstractMojo {

	private static final String APP_SHARE = "/app/share";
	private static final String OPENJDK = "openjdk";
	private static final String SIMPLE = "simple";

	private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
		try (FileInputStream fis = new FileInputStream(file)) {
			byte[] byteArray = new byte[1024];
			int bytesCount = 0;
			while ((bytesCount = fis.read(byteArray)) != -1) {
				digest.update(byteArray, 0, bytesCount);
			}
		}
		byte[] bytes = digest.digest();
		StringBuilder sb = new StringBuilder();
		for (byte element : bytes) {
			sb.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	@Parameter(defaultValue = "${project.build.directory}/app", required = true)
	private File appDirectory;

	@Parameter(defaultValue = "${project.artifactId}", required = true)
	private String appModuleName;

	@Parameter(defaultValue = "false")
	private boolean attachedArtifacts = true;

	/**
	 * List of automatic modules
	 */
	@Parameter
	private List<String> automaticArtifacts;

	@Parameter
	private Branding branding;

	/**
	 * The string for the "categories" property in the generated .desktop file.
	 * Expected to be in the format "cat1;cat2".
	 */
	@Parameter
	private String categories;

	/**
	 * List of classpath jars (overrides automatic detection of type)
	 */
	@Parameter
	private List<String> classpathArtifacts;

	@Parameter
	private ContentRating contentRating;

	@Parameter
	private DesktopEntry desktopEntry;

	@Parameter
	private List<String> excludeArtifacts;

	@Parameter(defaultValue = "${project.build.sourceDirectory}/flatpak", required = true) // TODO
	private File flatpakDataDirectory;

	@Parameter
	private String gschema;

	@Parameter
	private String iconPath;

	@Parameter(defaultValue = "false")
	private boolean ignoreSnapshotRemotes;

	@Parameter
	private String[] imageTypes = new String[] { "svg", "png", "gif", "jpg", "jpeg" };

	@Parameter(defaultValue = "true")
	private boolean includeVersion;

	@Parameter(defaultValue = "${maven.compiler.source}")
	private int javaSdkExtensionVersion;

	@Parameter
	private String[] launcherPostCommands;

	@Parameter
	private String[] launcherPreCommands;

	private final Logger logger = LoggerFactory.getLogger(FlatpakMojo.class);

	@Parameter
	private boolean mainArtifactIsModule;

	@Parameter(required = true)
	private String mainClass;

	@Parameter
	private Manifest manifest;

	@Parameter
	private MetaInfo metaInfo;

	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	private MavenProject project;

	@Parameter
	private List<Release> releases;

	@Parameter(defaultValue = "true")
	private boolean remotesFromOriginalSource;

	@Parameter(defaultValue = "${repositorySystemSession}", readonly = true, required = true)
	private RepositorySystemSession repoSession;

	@Parameter(defaultValue = "${project.remoteProjectRepositories}", readonly = true, required = true)
	private List<RemoteRepository> repositories;

	private RepositorySystem repoSystem;

	@Parameter(required = true)
	private String runtime;

	@Parameter(required = true)
	private String runtimeVersion;

	@Parameter
	private List<Screenshot> screenshots;

	@Parameter(required = true)
	private String sdk;

	@Parameter(defaultValue = "${session}", readonly = true, required = true)
	protected MavenSession session;

	@Parameter
	private String splashPath; // TODO

	@Parameter
	private String startupWMClass;

	/**
	 * List of system modules (overrides automatic detection of type)
	 */
	@Parameter
	private List<String> systemModules;

	@Parameter
	private File thumbnailsDirectory; // TODO

	@Parameter(defaultValue = "true")
	private boolean usingModules;

	@Parameter(defaultValue = "false")
	private boolean verifyRemotes;

	@Parameter
	private List<String> vmArgs;

	/**
	 * Constructor.
	 *
	 * @param repoSystem {@link RepositorySystem}
	 */
	@Inject
	public FlatpakMojo(RepositorySystem repoSystem) {
		this.repoSystem = repoSystem;
		this.metaInfo = new MetaInfo();
		this.manifest = new Manifest();
		this.desktopEntry = new DesktopEntry();
	}

	private void addDesktopEntry(Module appModule) {
		if (!desktopEntry.isIgnore()) {
			logger.info("Creating .desktop file...");
			desktopEntry.setType("Application");
			desktopEntry.setName(project.getName());
			desktopEntry.setComment(project.getDescription());
			desktopEntry.setExec(manifest.getCommand());
			desktopEntry.setIcon(manifest.getAppId());
			desktopEntry.setCategories(categories);
			if (startupWMClass != null && !startupWMClass.isEmpty()) {
				desktopEntry.setStartupWMClass(startupWMClass);
			}
			File desktopFile = getDesktopEntryFile();
			// add related entries in manifest
			appModule.getBuildCommands().add(formatInstall(desktopFile.getName(), "/app/share/applications"));
			appModule.getSources().add(new Source(desktopFile.getName()));
			logger.info("Successfully created .desktop file for " + desktopFile.getName());
		}
	}

	/**
	 * Handle Gtk GSetting schema.
	 *
	 * @param appModule {@link Module}
	 * @throws MojoExecutionException if 'gschema' is an invalid uri
	 */
	private void addGSchema(Module appModule) throws URISyntaxException {
		if (gschema != null && !gschema.isEmpty()) {
			String path = ".." + File.separator + ".." + File.separator + new URI(gschema).getPath(); // TODO
			String fileName = Paths.get(gschema).getFileName().toString();

			String message = String.format("Adding %s", fileName);
			logger.info(message);

			appModule.getSources().add(new Source(path));
			appModule.getBuildCommands().add("install -D " + fileName + " /app/share/glib-2.0/schemas/" + fileName);
			appModule.getBuildCommands().add("glib-compile-schemas /app/share/glib-2.0/schemas");
		}
	}

	private void addIcon(Module appModule) throws IOException {
		if (appModule != null && iconPath != null) {
			logger.info("Handling icon file...");
			File iconfile = new File(iconPath);
			String ext = getExtension(iconPath);
			String appIconFileName = manifest.getAppId() + "." + ext;
			copy("Icon file", iconfile, new File(appDirectory, appIconFileName), iconfile.lastModified());
			appModule.getBuildCommands().add(formatInstall(appIconFileName,
					"/app/share/icons/hicolor/" + getIconDirForTypeAndSize(iconfile) + "/apps"));
			appModule.getSources().add(new Source(appIconFileName));
			logger.info("Icon file complete.");
		}
	}

	// TODO thumbnails
//	private void addImageFiles() throws IOException {
//		for (File f : getImageFiles(thumbnailsDirectory)) {
//			copy("Copying thumbnail to flatpak build folder.", f, new File(appDirectory, f.getName()),
//					f.lastModified());
//		}
//	}

	private void addLauncher(Module appModule, List<String> classPaths, List<String> modulePaths,
			boolean mainArtifactIsModule) throws IOException {
		logger.info("Adding launcher...");
		appModule.getBuildCommands().add(formatInstall(manifest.getCommand(), "/app/bin"));
		appModule.getSources().add(new Source(manifest.getCommand()));
		try (OutputStream out = new FileOutputStream(new File(appDirectory, manifest.getCommand()))) {
			writeLauncher(new OutputStreamWriter(out), classPaths, modulePaths, mainArtifactIsModule);
		}
		logger.info("Launcher added.");
	}

	private void addManifestDefaults() {
		logger.info("Adding defaults to manifest...");
		manifest.setAppId(project.getGroupId() + "." + project.getArtifactId());
		manifest.setRuntime(runtime);
		manifest.setRuntimeVersion(runtimeVersion);
		manifest.setSdk(sdk);
		manifest.setCommand(project.getArtifactId());

		if (manifest.getFinishArgs().isEmpty()) {
			manifest.getFinishArgs().add("--socket=wayland");
			manifest.getFinishArgs().add("--share=ipc");
			manifest.getFinishArgs().add("--share=network");
			manifest.getFinishArgs().add("--filesystem=home");
		}
		logger.info("Defaults added to manifest.");
	}

	private void addMetaInfo(Module appModule) throws MetaInfoException {
		if (metaInfo.getType() == null || desktopEntry.getType().isEmpty()) {
			if (desktopEntry == null || desktopEntry.isIgnore()) {
				metaInfo.setType("console-application");
			} else {
				metaInfo.setType("desktop-application");
			}
		}

		metaInfo.setId(manifest.getAppId());
		metaInfo.setName(project.getName());
		metaInfo.setSummary(firstSentence(project.getDescription()));
		metaInfo.setDescription("<p>" + project.getDescription() + "</p>");
		metaInfo.setProjectLicense(getProjectLicenseName());
		metaInfo.setMetadataLicense(getMetaDataLicenseName());

		Url metaInfoUrl = getMetaInfoUrl();
		if (metaInfoUrl != null) {
			metaInfo.setUrl(metaInfoUrl);
		}

		if ((metaInfo.getProjectGroup() == null || metaInfo.getProjectGroup().isEmpty())
				&& (project.getOrganization() != null && project.getOrganization().getName() != null
						&& !project.getOrganization().getName().isEmpty())) {
			metaInfo.setProjectGroup(project.getOrganization().getName());
		}

		if (metaInfo.getDeveloper() == null && project.getDevelopers() != null && !project.getDevelopers().isEmpty()) {
			Developer developer = project.getDevelopers().get(0);
			/*
			 * We need a custom Developer class to allow marking the id as an attribute.
			 */
			flatpak.maven.plugin.models.Developer localDev = new flatpak.maven.plugin.models.Developer(
					developer.getId(), developer.getName());
			metaInfo.setDeveloper(localDev);
		}

		if (metaInfo.getLaunchable() == null) {
			metaInfo.setLaunchable(new Launchable("desktop-id", manifest.getAppId() + ".desktop"));
		}

		addScreenshots();

		if (this.branding != null) {
			this.metaInfo.setBranding(this.branding);
		}

		if (this.contentRating != null) {
			this.metaInfo.setContentRating(this.contentRating);
		}

		if (this.releases != null) {
			this.metaInfo.setReleases(this.releases);
		}

		File metaInfoFile = getMetaInfoFile();

		appModule.getBuildCommands().add(formatInstall(metaInfoFile.getName(), "/app/share/appdata"));
		appModule.getSources().add(new Source(metaInfoFile.getName()));
	}

	private void addScreenshots() throws MetaInfoException {
		for (Screenshot screenshot : screenshots) {
			metaInfo.getScreenshots().add(screenshot);
			URI uri = URI.create(screenshot.getImage().getValue());
			File sourceFile;
			try {
				sourceFile = fileFromUri(uri);
				calculateSizes(sourceFile, screenshot.getImage());
			} catch (IOException e) {
				throw new MetaInfoException(e.getMessage());
			}
			File destFile = new File(appDirectory, sourceFile.getName());
			try {
				copy("Copying screenshot to flatpak build folder.", sourceFile, destFile, sourceFile.lastModified());
			} catch (IOException e) {
				throw new MetaInfoException(e.getMessage());
			}
		}
	}

	private void addSdkExtensionModule() {
		Module sdkExtensionModule = manifest.getModule(OPENJDK);
		if (sdkExtensionModule == null) {
			String jdkName = OPENJDK;
			for (String ext : manifest.getSdkExtensions()) {
				if (ext.contains(OPENJDK)) {
					int idx = ext.lastIndexOf('.');
					jdkName = idx == -1 ? ext : ext.substring(idx + 1);
				}
			}
			sdkExtensionModule = new Module();
			sdkExtensionModule.setBuildSystem(SIMPLE);
			sdkExtensionModule.setName(OPENJDK);
			sdkExtensionModule.getBuildCommands().add("/usr/lib/sdk/" + jdkName + "/install.sh");
			manifest.getModules().add(0, sdkExtensionModule);
		}
	}

	private void addSdkExtensions() {
		boolean hasJdkExtension = false;
		for (String sdkExtension : manifest.getSdkExtensions()) {
			if (sdkExtension.contains(OPENJDK)) {
				hasJdkExtension = true;
			}
		}
		if (!hasJdkExtension) {
			if (javaSdkExtensionVersion < 12) {
				manifest.getSdkExtensions().add("org.freedesktop.Sdk.Extension.openjdk11");
			} else {
				manifest.getSdkExtensions().add("org.freedesktop.Sdk.Extension.openjdk17");
			} // TODO 21, 25
		}
	}

	private void addSplash(Module appModule) throws IOException {
		if (splashPath != null && !splashPath.isEmpty()) {
			File splashfile = new File(splashPath);
			String ext = getExtension(splashPath);
			String splashFileName = manifest.getAppId() + "." + ext;
			copy("splash file", splashfile, new File(appDirectory, splashFileName), splashfile.lastModified());
			appModule.getBuildCommands().add(formatInstall(splashFileName, "/app/share/pixmaps"));
			appModule.getSources().add(new Source(splashFileName));
		}
	}

	private void calculateSizes(File file, Image image) throws IOException {
		BufferedImage bimg = ImageIO.read(file);
		int width = bimg.getWidth();
		int height = bimg.getHeight();
		image.setHeight(height);
		image.setWidth(width);
	}

	private boolean containsArtifact(Collection<String> artifactNames, Artifact artifact) {
		if (artifactNames == null) {
			return false;
		}
		String gac = artifact.getGroupId() + ":" + artifact.getArtifactId() + ":" + artifact.getClassifier();
		if (artifactNames.contains(gac)) {
			return true;
		}
		gac = artifact.getGroupId() + ":" + artifact.getArtifactId();
		if (artifactNames.contains(gac)) {
			return true;
		}
		gac = artifact.getArtifactId();
		if (artifactNames.contains(gac)) {
			return true;
		}
		gac = artifact.getGroupId();
		return artifactNames.contains(gac);
	}

	private void copy(String reason, File p1, File p2, long mod) throws IOException {
		String message = String.format("Copy %s - %s to %s", reason, p1.getAbsolutePath(), p2.getAbsolutePath());
		logger.debug(message);
		p2.getParentFile().mkdirs();
		try (OutputStream out = new FileOutputStream(p2)) {
			Files.copy(p1.toPath(), out);
		}
		boolean setLastModified = p2.setLastModified(mod);
		if (!setLastModified) {
			throw new IOException("Unable to set 'lastModified' on file " + p2);
		}
	}

	private boolean doArtifact(Module appModule, org.apache.maven.artifact.Artifact a, List<String> classPaths,
			List<String> modulePaths) throws MojoExecutionException, IOException, NoSuchAlgorithmException {
		String message = String.format("Processing %s", a.getFile().getName());
		logger.debug(message);

		StringBuilder builder = new StringBuilder();
		builder.append(a.getGroupId());
		builder.append(":");
		builder.append(a.getArtifactId());
		builder.append(":");
		builder.append(a.getType());
		if (a.getClassifier() != null) {
			builder.append(":");
			builder.append(a.getClassifier());
		}
		builder.append(":");
		builder.append(a.getVersion());
		org.eclipse.aether.artifact.Artifact aetherArtifact = new DefaultArtifact(builder.toString());

		ArtifactResult resolutionResult = resolveRemoteArtifact(new HashSet<>(), project, aetherArtifact,
				this.repositories);
		if (resolutionResult == null) {
			throw new MojoExecutionException("Artifact " + aetherArtifact.getGroupId() + ":"
					+ aetherArtifact.getArtifactId() + " could not be resolved.");
		}
		aetherArtifact = resolutionResult.getArtifact();

		if (containsArtifact(excludeArtifacts, aetherArtifact)) {
			message = String.format("Artifact %s is explicitly excluded.", a.getArtifactId());
			logger.info(message);
			return false;
		}

		File file = aetherArtifact.getFile(); // TODO
		if (!file.exists()) {
			message = String.format(
					"Artifact %s has no attached file. Its content will not be copied in the target model directory.",
					aetherArtifact.getArtifactId());
			logger.warn(message);
			return false;
		}

		install(appModule, a, resolutionResult, file);
		if (isModule(aetherArtifact)) {
			modulePaths.add(getFileName(aetherArtifact));
			return true;
		}
		classPaths.add(getFileName(aetherArtifact));
		return false;
	}

	@Override
	public void execute() throws MojoExecutionException {
		addManifestDefaults();
		addSdkExtensionModule();

		Module appModule = manifest.getModule(appModuleName);
		if (appModule == null) {
			appModule = new Module();
			appModule.setName(appModuleName);
			manifest.getModules().add(appModule);
		}
		appModule.setBuildSystem(SIMPLE);
		appModule.setName(manifest.getCommand());

		addSdkExtensions();
		appDirectory.mkdirs();

		List<String> classPaths = new ArrayList<>();
		List<String> modulePaths = new ArrayList<>();

		try {
			addIcon(appModule);
			addSplash(appModule);
			addDesktopEntry(appModule);
			addMetaInfo(appModule);
			addGSchema(appModule);

			for (org.apache.maven.artifact.Artifact a : project.getArtifacts()) {
				doArtifact(appModule, a, classPaths, modulePaths);
			}
			if (attachedArtifacts) {
				for (org.apache.maven.artifact.Artifact a : project.getAttachedArtifacts()) {
					mainArtifactIsModule |= doArtifact(appModule, a, classPaths, modulePaths);
				}
			}

			mainArtifactIsModule |= doArtifact(appModule, project.getArtifact(), classPaths, modulePaths);

			addLauncher(appModule, classPaths, modulePaths, mainArtifactIsModule);

			try (OutputStream out = new FileOutputStream(new File(appDirectory, manifest.getAppId() + ".yml"))) {
				writeManifest(manifest, new OutputStreamWriter(out));
			}

			if (!desktopEntry.isIgnore()) {
				try (OutputStream out = new FileOutputStream(getDesktopEntryFile())) {
					writeDesktopEntry(out, desktopEntry);
				}
			}

			File metaInfoFile = getMetaInfoFile();
			try (PrintWriter out = new PrintWriter(metaInfoFile)) {
				writeMetaInfo(metaInfo, out);
			}
		} catch (IOException | NoSuchAlgorithmException | URISyntaxException | MetaInfoException e) {
			throw new MojoExecutionException("Failed to write manifest.", e);
		}
	}

	private File fileFromUri(URI uri) throws IOException {
		URL url = uri.toURL();
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(15000);

		String name = Paths.get(uri.getPath()).getFileName().toString();
		Path target = appDirectory.toPath().resolve(name);

		try (InputStream in = connection.getInputStream()) {
			Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
		}

		boolean lastModifiedSet = target.toFile().setLastModified(connection.getLastModified());
		if (!lastModifiedSet) {
			throw new IllegalArgumentException("Failed to set 'last-modified' on target file: " + target);
		}
		return target.toFile();
	}

	private String firstSentence(String description) {
		int idx = description.indexOf(". ");
		if (idx == -1) {
			idx = description.indexOf(".");
		}
		if (idx == -1) {
			return description;
		}
		return description.substring(0, idx);
	}

	private String formatInstall(String entryPath, String dir) {
		return formatInstall(entryPath, entryPath, dir);
	}

	private String formatInstall(String sourcePath, String entryPath, String dir) {
		return String.format("install -D %s %s/%s", sourcePath, dir, entryPath);
	}

	private String getBasePath(String path) {
		int idx = path.lastIndexOf('/');
		return idx == -1 ? path : path.substring(idx + 1);
	}

	private File getDesktopEntryFile() {
		return new File(appDirectory, manifest.getAppId() + ".desktop");
	}

	private String getExtension(String filename) {
		int idx = filename.lastIndexOf('.');
		return idx == -1 ? filename : filename.substring(idx + 1).toLowerCase();
	}

	private String getFileName(Artifact aetherArtifact) {
		return aetherArtifact.getFile().getName(); // TODO
	}

	private String getFileName(org.apache.maven.artifact.Artifact a) {
		return getFileName(a.getArtifactId(), a.getVersion(), a.getClassifier(), a.getType());
	}

	private String getFileName(String artifactId, String version, String classifier, String type) {
		StringBuilder name = new StringBuilder();
		name.append(artifactId);
		if (includeVersion) {
			name.append("-");
			name.append(version);
		}
		if (classifier != null && !classifier.isEmpty()) {
			name.append("-");
			name.append(classifier);
		}
		name.append(".");
		name.append(type);
		return name.toString();
	}

	private String getIconDirForTypeAndSize(File iconFile) {
		assert (iconFile != null);
		String ext = getExtension(iconFile.getName());
		if (ext.equals("svg")) {
			return "scalable";
		}
		try {
			BufferedImage bim = ImageIO.read(iconFile);
			int size = Math.max(bim.getWidth(), bim.getHeight());
			for (int s : new int[] { 512, 256, 192, 128, 96, 72, 64, 48, 40, 36, 32, 28, 24, 22, 20, 16, 8 }) {
				if (size >= s) {
					return s + "x" + s;
				}
			}
		} catch (IOException e) {
			String message = String.format("Unable to read icon file %s.", iconFile);
			logger.error(message);
		}
		// fallback
		return "256x256";
	}

	/**
	 * Get the metadata license name from pom tags. Defaults to "FSAP" if no
	 * metadata license is found in pom.
	 *
	 * @return {@link String}
	 */
	private String getMetaDataLicenseName() {
		String metaDataLicenseName = null;
		if (!project.getLicenses().isEmpty()) {
			// iterate and find metadata license
			for (License license : project.getLicenses()) {
				if (license.getComments().toLowerCase().contains("metadata")) {
					metaDataLicenseName = license.getName();
					break;
				}
			}
		}
		if (metaDataLicenseName == null || metaDataLicenseName.isEmpty()) {
			logger.info("Required metadata license not specified in pom.xml. Defaulting to 'FSFAP'.");
			metaDataLicenseName = "FSFAP";
		}
		return metaDataLicenseName;
	}

	private File getMetaInfoFile() {
		return new File(appDirectory, manifest.getAppId() + ".metainfo.xml");
	}

	/**
	 * Calculate the metaInfo url based on pom elements.
	 *
	 * @return {@link Url}
	 */
	private Url getMetaInfoUrl() {
		Url url = null;
		if (project.getUrl() != null) {
			url = new Url("homepage", project.getUrl());
		} else if (project.getScm() != null && project.getScm().getUrl() != null
				&& !project.getScm().getUrl().isEmpty()) {
			url = new Url("vcs-browser", project.getScm().getUrl());
		} else if (project.getIssueManagement() != null && project.getIssueManagement().getUrl() != null
				&& !project.getIssueManagement().getUrl().isEmpty()) {
			url = new Url("bugtracker", project.getIssueManagement().getUrl());
		} else if (project.getDevelopers() != null && !project.getDevelopers().isEmpty()) {
			Developer developer = project.getDevelopers().get(0);
			if (developer.getUrl() != null && !developer.getUrl().isEmpty()) {
				url = new Url("contact", developer.getUrl());
			}
		}
		return url;
	}

	/**
	 * Get the project license name from pom tags, e.g.:
	 *
	 * <pre>{@code
	 * <licenses>
	 *   <license>
	 *     <name>GPL-3.0-or-later</name>
	 *     <url>https://spdx.org/licenses/GPL-3.0-or-later.html</url>
	 *     <distribution>repo</distribution>
	 *     <comments>project</comments>
	 *   </license>
	 * </licenses>
	 *}</pre>
	 *
	 * @return {@link String}
	 * @throws MetaInfoException if no project license is specified
	 */
	private String getProjectLicenseName() throws MetaInfoException {
		// no default - must supply project license in pom
		String projectLicenseName = null;
		if (!project.getLicenses().isEmpty()) {
			// iterate and find metadata license
			for (License license : project.getLicenses()) {
				if (license.getComments().toLowerCase().contains("project")) {
					projectLicenseName = license.getName();
					break;
				}
			}
		}
		if (projectLicenseName == null || projectLicenseName.isEmpty()) {
			throw new MetaInfoException("Project license must be set with comment value 'project'.");
		}
		return projectLicenseName;
	}

	private void install(Module appModule, org.apache.maven.artifact.Artifact a, ArtifactResult resolutionResult,
			File file) throws IOException, NoSuchAlgorithmException {
		// TODO install a schema
		String entryPath = getFileName(a);
		String message = String.format("Adding %s", a.getFile().getName());
		logger.info(message);
		String remoteUrl = validateUrl(mavenUrl(resolutionResult));
		Source entry = new Source();
		if (remotesFromOriginalSource) {
			if (isRemote(remoteUrl)) {
				entry.setType("file");
				entry.setUrl(remoteUrl);
				entry.setSha256(getFileChecksum(MessageDigest.getInstance("SHA-256"), a.getFile()));
				appModule.getBuildCommands().add(formatInstall(getBasePath(remoteUrl), entryPath, APP_SHARE));
			} else {
				copy("Copy jar from Maven to flatpak build directory.", file, new File(appDirectory, entryPath),
						file.lastModified());
				entry.setType("file");
				entry.setPath(entryPath);
				appModule.getBuildCommands().add(formatInstall(entryPath, APP_SHARE));
			}
		} else {
			entry.setType("file");
			entry.setPath(entryPath);
			copy("Copy local jar to flatpak build directory.", a.getFile(), new File(appDirectory, entryPath),
					file.lastModified());
			appModule.getBuildCommands().add(formatInstall(entryPath, APP_SHARE));
		}
		appModule.getSources().add(entry);
	}

	private boolean isModule(Artifact a) throws IOException {
		if (!usingModules) {
			return false;
		}
		if (automaticArtifacts != null && containsArtifact(new LinkedHashSet<>(automaticArtifacts), a)) {
			return true;
		}
		if (classpathArtifacts != null && containsArtifact(new LinkedHashSet<>(classpathArtifacts), a)) {
			return false;
		}
		/* Detect */
		return isModuleJar(a);
	}

	private boolean isModuleJar(Artifact a) throws IOException {
		// TODO use of deprecated API
		try (JarFile jarFile = new JarFile(a.getFile())) {
			Enumeration<JarEntry> enumOfJar = jarFile.entries();
			java.util.jar.Manifest mf = jarFile.getManifest();
			if (mf != null && mf.getMainAttributes().getValue("Automatic-Module-Name") != null) {
				return true;
			}
			while (enumOfJar.hasMoreElements()) {
				JarEntry entry = enumOfJar.nextElement();
				if (entry.getName().equals("module-info.class")
						|| entry.getName().matches("META-INF/versions/.*/module-info.class")) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isRemote(String path) {
		return path != null && (path.startsWith("http:") || path.startsWith("https:"));
	}

	private String mavenUrl(ArtifactResult result) {
		if (result.getArtifact().isSnapshot() && ignoreSnapshotRemotes) {
			return null;
		}

		ArtifactRepository repo = result.getRepository();
		MavenProject mProject = this.project;
		if (mProject != null) {
			String url = mavenUrlForProject(result, repo, mProject);
			if (url != null) {
				return url;
			}
		}
		while (mProject != null) {
			List<MavenProject> collectedProjects = mProject.getCollectedProjects();
			if (collectedProjects != null) {
				for (MavenProject p : collectedProjects) {
					String url = mavenUrlForProject(result, repo, p);
					if (url != null) {
						return url;
					}
				}
			}
			mProject = mProject.getParent();
		}
		return null;
	}

	private String mavenUrl(String base, String groupId, String artifactId, String baseVersion, String version,
			String classifier) {
		StringBuilder builder = new StringBuilder();
		builder.append(base + '/');
		builder.append(groupId.replace('.', '/') + "/");
		builder.append(artifactId + "/");
		builder.append(baseVersion + "/");
		builder.append(artifactId + "-" + version);
		if (classifier != null && !classifier.isEmpty()) {
			builder.append('-' + classifier);
		}
		builder.append(".jar");
		return builder.toString();
	}

	private String mavenUrlForProject(ArtifactResult result, ArtifactRepository repo, MavenProject mavenProject) {
		assert (result != null && repo != null && mavenProject != null);
		for (RemoteRepository r : mavenProject.getRemoteProjectRepositories()) {
			if (r.getId().equals(repo.getId())) {
				String url = r.getUrl();
				return mavenUrl(url, result.getArtifact().getGroupId(), result.getArtifact().getArtifactId(),
						result.getArtifact().getBaseVersion(), result.getArtifact().getVersion(),
						result.getArtifact().getClassifier());
			}
		}
		return null;
	}

	private ArtifactResult resolve(Set<MavenProject> visitedProjects, Artifact aetherArtifact) {
		for (MavenProject p : session.getAllProjects()) {
			if (!visitedProjects.contains(p)) {
				try {
					ArtifactResult resolutionResult = resolveRemoteArtifact(visitedProjects, p, aetherArtifact,
							p.getRemoteProjectRepositories());
					if (resolutionResult != null) {
						return resolutionResult;
					}
				} catch (MojoExecutionException mee) {
					logger.error("An error occurred resolving remote artifacts.", mee);
				}
			}
		}
		return null;
	}

	// TODO
	private ArtifactResult resolveRemoteArtifact(Set<MavenProject> visitedProjects, MavenProject project,
			org.eclipse.aether.artifact.Artifact aetherArtifact, List<RemoteRepository> repos)
			throws MojoExecutionException {
		ArtifactRequest req = new ArtifactRequest().setRepositories(repos).setArtifact(aetherArtifact);
		visitedProjects.add(project);

		try {
			return this.repoSystem.resolveArtifact(this.repoSession, req);
		} catch (ArtifactResolutionException e) {
			if (project.getParent() == null) {
				/* Reached the root (reactor), now look in child module repositories too */
				return resolve(visitedProjects, aetherArtifact);
			}
			if (!visitedProjects.contains(project.getParent())) {
				return resolveRemoteArtifact(visitedProjects, project.getParent(), aetherArtifact,
						project.getParent().getRemoteProjectRepositories());
			}
		}
		return null;
	}

	private void scriptArgs(List<String> vmopts, List<String> classPaths, List<String> modulePaths) {
		assert (vmopts != null && classPaths != null && modulePaths != null);
		if (splashPath != null) {
			vmopts.add("-splash:" + "/app/share/pixmaps/" + manifest.getAppId() + "." + getExtension(splashPath));
		}

		if (!modulePaths.isEmpty()) {
			vmopts.add("-p");
			vmopts.add(String.join(File.pathSeparator,
					modulePaths.stream().map(s -> "/app/share/" + s).collect(Collectors.toList())));
		}
		if (!classPaths.isEmpty()) {
			vmopts.add("-cp");
			vmopts.add(String.join(File.pathSeparator,
					classPaths.stream().map(s -> "/app/share/" + s).collect(Collectors.toList())));
		}
		if (systemModules != null && !systemModules.isEmpty()) {
			vmopts.add("--add-modules");
			vmopts.add(String.join(",", systemModules));
		}
		if (vmArgs != null) {
			for (String vmArg : vmArgs) {
				vmopts.add(vmArg);
			}
		}
	}

	private String validateUrl(String address) {
		if (address == null) {
			return address;
		}
		try {
			URL url = new URI(address).toURL();
			URLConnection conx = url.openConnection();
			conx.getInputStream().close();
			return address;
		} catch (Exception e) {
			logger.warn(MessageFormat.format("{0} will use local copy as remote failed verification check.", address));
			return null;
		}
	}

	private void writeDesktopEntry(OutputStream out, DesktopEntry desktopEntry) {
		assert (out != null && desktopEntry != null);
		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(out))) {
			writer.println("[Desktop Entry]");
			writer.println("Version=1.0");
			writer.println(String.format("Name=%s", desktopEntry.getName()));
			writer.println(String.format("Exec=%s", desktopEntry.getExec()));
			writer.println(String.format("Icon=%s", desktopEntry.getIcon()));
			writer.println(String.format("Type=%s", desktopEntry.getType()));
			if (desktopEntry.getComment() != null && !desktopEntry.getComment().isEmpty()) {
				writer.println(String.format("Comment=%s", desktopEntry.getComment()));
			}
			if (desktopEntry.getCategories() != null && desktopEntry.getCategories().isEmpty()) {
				writer.println(String.format("Categories=%s", desktopEntry.getCategories()));
			}
			if (desktopEntry.getStartupWMClass() != null && !desktopEntry.getStartupWMClass().isEmpty()) {
				writer.println(String.format("StartupWMClass=%s", desktopEntry.getStartupWMClass()));
			}
		}
	}

	private void writeLauncher(Writer writer, List<String> classPaths, List<String> modulePaths,
			boolean mainArtificateIsModule) {
		try (PrintWriter pw = new PrintWriter(writer, true)) {
			pw.println("#!/bin/bash");
			if (launcherPreCommands != null) {
				for (String s : launcherPreCommands) {
					pw.println(s);
				}
			}
			StringBuilder execLine = new StringBuilder("/app/jre/bin/java ");
			List<String> vmopts = new ArrayList<>();
			scriptArgs(vmopts, classPaths, modulePaths);
			execLine.append(String.join(" ", vmopts));
			execLine.append(" ");
			if (mainArtificateIsModule) {
				execLine.append("-m ");
			}
			execLine.append(mainClass);
			pw.println(execLine.toString());
			if (launcherPostCommands != null) {
				for (String s : launcherPostCommands) {
					pw.println(s);
				}
			}
		}
	}

	private void writeManifest(Manifest manifest, Writer writer) throws IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.writeValue(writer, manifest);
	}

	private void writeMetaInfo(MetaInfo metaInfo, PrintWriter writer) throws IOException {
		XmlMapper mapper = new XmlMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(writer, metaInfo);

		// TODO finish or find a way to config mapper to allow <p>
//		writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//		writer.println("<component>");
//		writer.println("\t" + metaInfo.getReleases().getFirst().getDescription());
//		writer.println("</component>");
//		writer.flush();
//		writer.close();
	}
}
