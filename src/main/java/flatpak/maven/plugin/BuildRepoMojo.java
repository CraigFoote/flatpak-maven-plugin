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
 * Goal that builds a flatpak repository from prepared artifacts.
 */
@Mojo(threadSafe = true, name = "build-repo", defaultPhase = LifecyclePhase.PACKAGE, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresProject = true)
public class BuildRepoMojo extends AbstractMojo {

	private static final Logger LOGGER = LoggerFactory.getLogger(BuildRepoMojo.class);

	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	private MavenProject project;

	/**
	 * Constructor.
	 */
	public BuildRepoMojo() {
		// empty
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		if (isWindows) {
			throw new MojoExecutionException("Windows builds are not supported.");
		}

		ProcessBuilder builder = new ProcessBuilder();
		builder.command("flatpak-builder", "--repo=repo", "--force-clean", "build-dir",
				project.getGroupId() + "." + project.getArtifactId() + ".yml");

		Path workingPath = Paths.get(project.getBasedir().getAbsolutePath(), "target", "app");
		File workingFolder = workingPath.toFile();
		if (!workingFolder.exists()) {
			throw new MojoExecutionException(
					"The project's '/target/app/' folder was not found. Did you run the 'prepare-build' goal first?");
		}
		builder.directory(workingFolder);

		String message = String.format("Executing command in folder: %s", builder.directory());
		LOGGER.info(message);

		Process process;
		try {
			message = String.format("Executing command: %s", builder.command());
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
		Path expectedRepoFolderLocation = Paths.get(workingPath.toString(), "repo");
		File repoFolder = expectedRepoFolderLocation.toFile();
		if (!repoFolder.exists()) {
			throw new MojoExecutionException(
					"Error, expected built repository not found: " + expectedRepoFolderLocation);
		}

		LOGGER.info("Success!");
	}
}