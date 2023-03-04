package uk.co.bithatch.maven.flatpak.plugin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Manifest {

	private String appId;
	private String runtime;
	private String runtimeVersion;
	private String sdk;
	private List<String> sdkExtensions = new ArrayList<>();
	private String command;
	private List<Module> modules = new ArrayList<>();
	private List<String> finishArgs = new ArrayList<>();

	@JsonProperty(value = "app-id", index = 0)
	public final String getAppId() {
		return appId;
	}

	public final void setAppId(String appId) {
		this.appId = appId;
	}

	@JsonProperty(value = "runtime", index= 1)
	public final String getRuntime() {
		return runtime;
	}

	public final void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	@JsonProperty(value = "runtime-version", index= 2)
	public final String getRuntimeVersion() {
		return runtimeVersion;
	}

	public final void setRuntimeVersion(String runtimeVersion) {
		this.runtimeVersion = runtimeVersion;
	}

	@JsonProperty(value = "sdk", index= 3)
	public final String getSdk() {
		return sdk;
	}

	public final void setSdk(String sdk) {
		this.sdk = sdk;
	}

	@JsonProperty(value = "command", index = 5)
	public final String getCommand() {
		return command;
	}

	public final void setCommand(String command) {
		this.command = command;
	}

	@JsonProperty(value = "sdk-extensions", index = 4)
	public final List<String> getSdkExtensions() {
		return sdkExtensions;
	}

	@JsonProperty(value = "modules", index = 6)
	public final List<Module> getModules() {
		return modules;
	}

	public final List<String> getFinishArgs() {
		return finishArgs;
	}

	@JsonProperty(value = "finish-args", index = 999)
	public final void setFinishArgs(List<String> finishArgs) {
		this.finishArgs = finishArgs;
	}
	
	Module getModule(String name) {
		for(Module module : modules) {
			if(name.equals(module.getName())) {
				return module;
			}
		}
		return null;
	}
}
