package flatpak.maven.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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

		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
			LOGGER.info("Determined OS to be Windows. Executing 'cmd.exe'.");
			builder.command("cmd.exe", "/c", "flatpak-builder", "--repo=repo", "--force-clean", "build-dir",
					project.getGroupId() + "." + project.getArtifactId() + ".yml");
		} else {
			LOGGER.info("Determined OS to be something other than Windows. Executing shell command.");
			builder.command("flatpak-builder", "--repo=repo", "--force-clean", "build-dir",
					project.getGroupId() + "." + project.getArtifactId() + ".yml");
		}

		String workingDir = project.getBasedir() + File.separator + "target" + File.separator + "app" + File.separator;
		builder.directory(new File(workingDir));

		String message = String.format("Executing command in folder: %s", builder.directory());
		LOGGER.info(message);

		Process process;
		try {
			message = String.format("Executing command: %s", builder.toString());
			LOGGER.info(message);
			process = builder.start();
		} catch (IOException e) {
			throw new MojoExecutionException("Error starting process.", e);
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
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
	}
}