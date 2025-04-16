/**
 * 
 */
package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * Encapsulates an Url `type` and `value`.
 * 
 * @author Footeware.ca
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
	 * @return the type
	 */
	@JacksonXmlProperty(isAttribute = true)
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the value
	 */
	@JacksonXmlText
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
