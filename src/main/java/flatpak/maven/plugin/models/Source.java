package flatpak.maven.plugin.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A manifest's resource's source.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Source {

	private String path;
	private String sha256;
	private String type = "file";
	private String url;

	/**
	 * No-arg constructor suitable for object mapping.
	 */
	public Source() {
	}

	/**
	 * Constructor.
	 *
	 * @param path {@link String}
	 */
	public Source(String path) {
		this.path = path;
	}

	/**
	 * Get the source path.
	 *
	 * @return {@link String}
	 */
	@JsonProperty(required = false)
	public String getPath() {
		return path;
	}

	/**
	 * Get the source's sha256.
	 *
	 * @return {@link String}
	 */
	@JsonProperty(required = false)
	public String getSha256() {
		return sha256;
	}

	/**
	 * Get the source's type.
	 *
	 * @return {@link String}
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get the source's URL.
	 *
	 * @return {@link String}
	 */
	@JsonProperty(required = false)
	public String getUrl() {
		return url;
	}

	/**
	 * Set the source path.
	 *
	 * @param path {@link String}
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Get the source's sha256.
	 *
	 * @param sha256 {@link String}
	 */
	public void setSha256(String sha256) {
		this.sha256 = sha256;
	}

	/**
	 * Get the source's type.
	 *
	 * @param type {@link String}
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get the source's URL.
	 *
	 * @param url {@link String}
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
