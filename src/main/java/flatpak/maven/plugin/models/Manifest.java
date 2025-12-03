package flatpak.maven.plugin.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * The flatpak manifest needed to create a repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "app-id", "command", "runtime", "runtimeVersion", "sdk", "sdkExtensions", "modules", "categories",
		"finish-args" })
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

	/**
	 * Constructor.
	 */
	public Manifest() {
		// empty
	}

	/**
	 * Get the application id.
	 *
	 * @return {@link String}
	 */
	@JsonGetter("app-id")
	public String getAppId() {
		return appId;
	}

	/**
	 * Get the application's categories.
	 *
	 * @return {@link String}
	 */
	@JsonGetter("categories")
	public String getCategories() {
		return categories;
	}

	/**
	 * Get the application's command.
	 *
	 * @return {@link String}
	 */
	@JsonGetter("command")
	public String getCommand() {
		return command;
	}

	/**
	 * Get the application's finish args.
	 *
	 * @return {@link List} of {@link String}
	 */
	@JsonGetter("finish-args")
	public List<String> getFinishArgs() {
		return finishArgs;
	}

	/**
	 * Get a module by name.
	 *
	 * @param name {@link String}
	 * @return {@link Module}
	 */
	public Module getModule(String name) {
		for (Module module : modules) {
			if (name.equals(module.getName())) {
				return module;
			}
		}
		return null;
	}

	/**
	 * Get the modules.
	 *
	 * @return {@link List} of {@link Module}
	 */
	@JsonGetter("modules")
	public List<Module> getModules() {
		return modules;
	}

	/**
	 * Get the application's runtime.
	 *
	 * @return {@link String}
	 */
	@JsonGetter("runtime")
	public String getRuntime() {
		return runtime;
	}

	/**
	 * Get the application's runtime version.
	 *
	 * @return {@link String}
	 */
	@JsonGetter("runtime-version")
	public String getRuntimeVersion() {
		return runtimeVersion;
	}

	/**
	 * Get the application's SDK name.
	 *
	 * @return {@link String}
	 */
	@JsonGetter("sdk")
	public String getSdk() {
		return sdk;
	}

	/**
	 * Get the application's SDK extensions.
	 *
	 * @return {@link List} of {@link String}
	 */
	@JsonGetter("sdk-extensions")
	public List<String> getSdkExtensions() {
		return sdkExtensions;
	}

	/**
	 * Set the application's application id.
	 *
	 * @param appId {@link String}
	 */
	@JsonSetter("app-id")
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * Set the application's categories.
	 *
	 * @param categories {@link String}
	 */
	@JsonSetter("categories")
	public void setCategories(String categories) {
		this.categories = categories;
	}

	/**
	 * Set the application's command.
	 *
	 * @param command {@link String}
	 */
	@JsonSetter("command")
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * Set the application's finish args.
	 *
	 * @param finishArgs {@link List} of {@link String}
	 */
	@JsonSetter("finish-args")
	public void setFinishArgs(List<String> finishArgs) {
		this.finishArgs = finishArgs;
	}

	/**
	 * Set the application's modules.
	 *
	 * @param modules {@link List} of {@link Module}
	 */
	@JsonSetter("modules")
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	/**
	 * Set the application's runtime name.
	 *
	 * @param runtime {@link String}
	 */
	@JsonSetter("runtime")
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	/**
	 * Set the application's runtime version.
	 *
	 * @param runtimeVersion {@link String}
	 */
	@JsonSetter("runtime-version")
	public void setRuntimeVersion(String runtimeVersion) {
		this.runtimeVersion = runtimeVersion;
	}

	/**
	 * Set the application's SDK naem.
	 *
	 * @param sdk {@link String}
	 */
	@JsonSetter("sdk")
	public void setSdk(String sdk) {
		this.sdk = sdk;
	}

	/**
	 * Set the application's SDK extension names.
	 *
	 * @param sdkExtensions {@link List} of {@link String}
	 */
	@JsonSetter("sdk-extensions")
	public void setSdkExtensions(List<String> sdkExtensions) {
		this.sdkExtensions = sdkExtensions;
	}
}
