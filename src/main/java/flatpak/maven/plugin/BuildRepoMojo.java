package flatpak.maven.plugin;

import javax.inject.Inject;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.eclipse.aether.RepositorySystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Goal which builds a flatpak repository.
 */
@Mojo(threadSafe = true, name = "build-repo", defaultPhase = LifecyclePhase.PACKAGE, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME, requiresProject = true)
public class BuildRepoMojo extends AbstractMojo {

	private static final Logger LOGGER = LoggerFactory.getLogger(BuildRepoMojo.class);

	@Parameter(defaultValue = "Hello World!", required = true, readonly = true)
	private String message;

	/**
	 * The entry point to Aether, i.e. the component doing all the work.
	 */
	private RepositorySystem repoSystem;

	/**
	 * Constructor.
	 *
	 * @param repoSystem {@link RepositorySystem}
	 */
	@Inject
	public BuildRepoMojo(RepositorySystem repoSystem) {
		this.repoSystem = repoSystem;
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		LOGGER.info(message);
	}
}