/**
 *
 */
package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * Encapsulates launchable information for flatpak metainfo.xml file generation.
 *
 * @author Footeware.ca
 */
public class Launchable {

	private String type;
	private String value;

	/**
	 * Constructor.
	 *
	 * @param type {@link String}
	 * @param value {@link String}
	 */
	public Launchable(String type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * @return the type
	 */
	@JacksonXmlProperty(isAttribute = true)
	public String getType() {
		return type;
	}

	/**
	 * @return the value
	 */
	@JacksonXmlText
	public String getValue() {
		return value;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
