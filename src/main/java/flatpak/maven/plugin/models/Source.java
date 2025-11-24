package flatpak.maven.plugin.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Source {

	private String path;
	private String sha256;
	private String type = "file";
	private String url;

	public Source() {
	}

	public Source(String path) {
		this.path = path;
	}

	@JsonProperty(required = false)
	public final String getPath() {
		return path;
	}

	@JsonProperty(required = false)
	public final String getSha256() {
		return sha256;
	}

	public final String getType() {
		return type;
	}

	@JsonProperty(required = false)
	public final String getUrl() {
		return url;
	}

	public final void setPath(String path) {
		this.path = path;
	}

	public final void setSha256(String sha256) {
		this.sha256 = sha256;
	}

	public final void setType(String type) {
		this.type = type;
	}

	public final void setUrl(String url) {
		this.url = url;
	}
}
