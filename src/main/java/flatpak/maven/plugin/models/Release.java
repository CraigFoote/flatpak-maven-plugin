package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Describes a release of the application.
 */
@JacksonXmlRootElement(localName = "release")
public class Release {

	private String date;
	private String description;
	private String version;

	/**
	 * Constructor.
	 */
	public Release() {
		// empty
	}

	/**
	 * Get the date.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(isAttribute = true)
	public String getDate() {
		return date;
	}

	/**
	 * Get the description.
	 *
	 * @return {@link String}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the version.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(isAttribute = true)
	public String getVersion() {
		return version;
	}

	/**
	 * GSet the date.
	 *
	 * @param date {@link String}
	 */
	@JacksonXmlProperty(isAttribute = true)
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Set the description.
	 *
	 * @param description {@link String}
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Set the vesion.
	 *
	 * @param version {@link String}
	 */
	@JacksonXmlProperty(isAttribute = true)
	public void setVersion(String version) {
		this.version = version;
	}
}
