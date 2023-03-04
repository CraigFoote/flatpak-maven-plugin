package uk.co.bithatch.maven.flatpak.plugin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Source {

	private String type = "file";
	private String path;
	private String url;
	private String sha256;
	
	public Source() {
	}
	
	Source(String path) {
		this.path = path;
	}

	public final String getType() {
		return type;
	}

	public final void setType(String type) {
		this.type = type;
	}

	@JsonProperty(required = false)
	public final String getPath() {
		return path;
	}

	public final void setPath(String path) {
		this.path = path;
	}

	@JsonProperty(required = false)
	public final String getUrl() {
		return url;
	}

	public final void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty(required = false)
	public final String getSha256() {
		return sha256;
	}

	public final void setSha256(String sha256) {
		this.sha256 = sha256;
	}
}
