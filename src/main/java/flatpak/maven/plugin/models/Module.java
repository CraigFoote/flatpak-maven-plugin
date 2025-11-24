package flatpak.maven.plugin.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Module {

	private List<String> buildCommands = new ArrayList<>();
	private String buildSystem;
	private String name;
	private List<Source> sources = new ArrayList<>();

	@JsonProperty(value = "build-commands", index = 2)
	public final List<String> getBuildCommands() {
		return buildCommands;
	}

	@JsonProperty(value = "buildsystem", index = 1)
	public final String getBuildSystem() {
		return buildSystem;
	}

	@JsonProperty(value = "name", index = 0)
	public final String getName() {
		return name;
	}

	@JsonProperty(value = "sources", index = 3)
	public final List<Source> getSources() {
		return sources;
	}

	public final void setBuildSystem(String buildSystem) {
		this.buildSystem = buildSystem;
	}

	public final void setName(String name) {
		this.name = name;
	}
}
