/**
 *
 */
package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * Launchable information for flatpak metainfo.xml file generation.
 *
 * @author Footeware.ca
 */
public class Launchable {

	private String type;
	private String value;

	/**
	 * Constructor.
	 *
	 * @param type  {@link String}
	 * @param value {@link String}
	 */
	public Launchable(String type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Get the launchable's type.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(isAttribute = true)
	public String getType() {
		return type;
	}

	/**
	 * Get the launchable's value.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlText
	public String getValue() {
		return value;
	}

	/**
	 * Get the launchable's type.
	 *
	 * @param type {@link String}
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the launchable's value.
	 *
	 * @param value {@link String}
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
