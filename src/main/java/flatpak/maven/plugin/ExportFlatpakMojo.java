package flatpak.maven.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exports a provided flatpak repository to a .flatpak file.
 */
@Mojo(threadSafe = true, name = "export-flatpak", defaultPhase = LifecyclePhase.PACKAGE, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresProject = true)
public class ExportFlatpakMojo extends AbstractMojo {

	private static final String FLATPAK = ".flatpak";
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportFlatpakMojo.class);

	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	private MavenProject project;

	/**
	 * Constructor.
	 */
	public ExportFlatpakMojo() {
		// empty
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		if (isWindows) {
			throw new MojoExecutionException("Windows builds are not supported.");
		}

		ProcessBuilder builder = new ProcessBuilder();
		builder.command("flatpak", "build-bundle", "repo",
				project.getGroupId() + "." + project.getArtifactId() + FLATPAK,
				project.getGroupId() + "." + project.getArtifactId());

		Path workingPath = Paths.get(project.getBasedir().getAbsolutePath(), "target", "app");
		File workingFolder = workingPath.toFile();
		if (!workingFolder.exists()) {
			throw new MojoExecutionException(
					"The project's '/target/app/' folder was not found. Did you run the 'prepare-build' goal first?");
		}
		LOGGER.info("Found '/target/app/' folder.");
		builder.directory(workingFolder);

		// assume not found until found
		boolean repoFound = false;
		File[] childFiles = workingFolder.listFiles();
		for (File childFile : childFiles) {
			if ("repo".equals(childFile.getName())) {
				repoFound = true;
				break;
			}
		}
		if (!repoFound) {
			throw new MojoExecutionException(
					"The project's '/target/app/repo' folder was not found. Did you run the 'build-repo' goal first?");
		}
		LOGGER.info("Found '/target/app/repo/' folder.");

		String message = String.format("Executing command in folder: %s", builder.directory());
		LOGGER.info(message);

		Process process;
		try {
			message = String.format("Executing command: %s. This could take a while.", builder.command());
			LOGGER.info(message);
			process = builder.start();
		} catch (IOException e) {
			throw new MojoExecutionException("Error starting process.", e);
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				// output to user
				LOGGER.info(line);
			}
		} catch (IOException e) {
			throw new MojoExecutionException("Error reading response from  process.", e);
		}

		try {
			int exitCode = process.waitFor();
			if (exitCode != 0) {
				throw new MojoExecutionException("Error, command returned non-zero exit code: " + exitCode);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new MojoExecutionException("Error waiting for process.", e);
		}

		// confirm presence
		Path expectedFileLocation = Paths.get(workingPath.toString(),
				project.getGroupId() + "." + project.getArtifactId() + FLATPAK);
		File flatpak = expectedFileLocation.toFile();
		if (!flatpak.exists()) {
			throw new MojoExecutionException("Error, expected built file not found: " + expectedFileLocation);
		}

		message = String.format("%s created. Success!", flatpak.getAbsolutePath());
		LOGGER.info(message);
	}
}
