package flatpak.maven.plugin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Module {

	private String name;
	private String buildSystem;
	private List<String> buildCommands = new ArrayList<>();
	private List<Source> sources = new ArrayList<>();

	@JsonProperty(value = "name", index = 0)
	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	@JsonProperty(value = "buildsystem", index = 1)
	public final String getBuildSystem() {
		return buildSystem;
	}

	public final void setBuildSystem(String buildSystem) {
		this.buildSystem = buildSystem;
	}

	@JsonProperty(value = "build-commands", index = 2)
	public final List<String> getBuildCommands() {
		return buildCommands;
	}

	@JsonProperty(value = "sources", index = 3)
	public final List<Source> getSources() {
		return sources;
	}
}
