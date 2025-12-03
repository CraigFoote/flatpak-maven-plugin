package flatpak.maven.plugin.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A module used in the building of the application.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Module {

	private List<String> buildCommands = new ArrayList<>();
	private String buildSystem;
	private String name;
	private List<Source> sources = new ArrayList<>();

	/**
	 * Constructor.
	 */
	public Module() {
		// empty
	}

	/**
	 * Get the build commands.
	 *
	 * @return {@link List} of {@link String}
	 */
	@JsonProperty(value = "build-commands", index = 2)
	public List<String> getBuildCommands() {
		return buildCommands;
	}

	/**
	 * Get the build system.
	 *
	 * @return {@link String}
	 */
	@JsonProperty(value = "buildsystem", index = 1)
	public String getBuildSystem() {
		return buildSystem;
	}

	/**
	 * Get the module name.
	 *
	 * @return {@link String}
	 */
	@JsonProperty(value = "name", index = 0)
	public String getName() {
		return name;
	}

	/**
	 * Get the module sources.
	 *
	 * @return {@link List} of {@link Source}
	 */
	@JsonProperty(value = "sources", index = 3)
	public List<Source> getSources() {
		return sources;
	}

	/**
	 * Set the build system.
	 *
	 * @param buildSystem {@link String}
	 */
	public void setBuildSystem(String buildSystem) {
		this.buildSystem = buildSystem;
	}

	/**
	 * Set the module name.
	 *
	 * @param name {@link String}
	 */
	public void setName(String name) {
		this.name = name;
	}
}
