/**
 *
 */
package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * Encapsulates an Url `type` and `value`.
 */
public class Url {

	private String type;
	private String value;

	/**
	 * Constructor.
	 *
	 * @param type  {@link String}
	 * @param value {@link String}
	 */
	public Url(String type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Get the type.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(isAttribute = true)
	public String getType() {
		return type;
	}

	/**
	 * Get the value.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlText
	public String getValue() {
		return value;
	}

	/**
	 * Set the type.
	 *
	 * @param type {@link String}
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Set the value.
	 *
	 * @param value {@link String}
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
