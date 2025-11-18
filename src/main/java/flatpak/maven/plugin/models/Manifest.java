package flatpak.maven.plugin.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "appId", "command", "runtime", "runtimeVersion", "sdk", "sdkExtensions", "modules", "categories",
		"finishArgs" })
public class Manifest {

	private String appId;
	private String categories;
	private String command;
	private List<String> finishArgs = new ArrayList<>();
	private List<Module> modules = new ArrayList<>();
	private String runtime;
	private String runtimeVersion;
	private String sdk;
	private List<String> sdkExtensions = new ArrayList<>();

	@JsonGetter("appId")
	public final String getAppId() {
		return appId;
	}

	@JsonGetter("categories")
	public final String getCategories() {
		return categories;
	}

	@JsonGetter("command")
	public final String getCommand() {
		return command;
	}

	@JsonGetter("finishArgs")
	public final List<String> getFinishArgs() {
		return finishArgs;
	}

	public Module getModule(String name) {
		for (Module module : modules) {
			if (name.equals(module.getName())) {
				return module;
			}
		}
		return null;
	}

	@JsonGetter("modules")
	public final List<Module> getModules() {
		return modules;
	}

	@JsonGetter("runtime")
	public final String getRuntime() {
		return runtime;
	}

	@JsonGetter("runtime-version")
	public final String getRuntimeVersion() {
		return runtimeVersion;
	}

	@JsonGetter("sdk")
	public final String getSdk() {
		return sdk;
	}

	@JsonGetter("sdk-extensions")
	public final List<String> getSdkExtensions() {
		return sdkExtensions;
	}

	@JsonSetter("app-id")
	public final void setAppId(String appId) {
		this.appId = appId;
	}

	@JsonSetter("categories")
	public final void setCategories(String categories) {
		this.categories = categories;
	}

	@JsonSetter("command")
	public final void setCommand(String command) {
		this.command = command;
	}

	@JsonSetter("finish-args")
	public final void setFinishArgs(List<String> finishArgs) {
		this.finishArgs = finishArgs;
	}

	@JsonSetter("modules")
	public final void setModules(List<Module> modules) {
		this.modules = modules;
	}

	@JsonSetter("runtime")
	public final void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	@JsonSetter("runtime-version")
	public final void setRuntimeVersion(String runtimeVersion) {
		this.runtimeVersion = runtimeVersion;
	}

	@JsonSetter("sdk")
	public final void setSdk(String sdk) {
		this.sdk = sdk;
	}

	@JsonSetter("sdk-extensions")
	public final void setSdkExtensions(List<String> sdkExtensions) {
		this.sdkExtensions = sdkExtensions;
	}
}
