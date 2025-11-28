package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Release {

	private String date;
	private String description;
	private String version;

	@JacksonXmlProperty(isAttribute = true)
	public String getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	@JacksonXmlProperty(isAttribute = true)
	public String getVersion() {
		return version;
	}

	@JacksonXmlProperty(isAttribute = true)
	public void setDate(String date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JacksonXmlProperty(isAttribute = true)
	public void setVersion(String version) {
		this.version = version;
	}
}
