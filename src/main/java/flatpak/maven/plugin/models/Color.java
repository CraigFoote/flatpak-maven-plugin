package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * A branding color with type and value.
 */
@JacksonXmlRootElement(localName = "color")
public class Color {

	private String schemePreference;
	private String type = "primary";
	private String value;

	/**
	 * Constructor.
	 */
	public Color() {
		// empty
	}

	/**
	 * Get the color's scheme preference.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(localName = "scheme_preference", isAttribute = true)
	public String getSchemePreference() {
		return schemePreference;
	}

	/**
	 * Get the color's type.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(isAttribute = true)
	public String getType() {
		return type;
	}

	/**
	 * Get the color value.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlText
	public String getValue() {
		return value;
	}

	/**
	 * Set the color's scheme preference.
	 *
	 * @param schemePreference {@link String}
	 */
	public void setSchemePreference(String schemePreference) {
		this.schemePreference = schemePreference;
	}

	/**
	 * Set the color's type.
	 *
	 * @param type {@link String}
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Set the color's value.
	 *
	 * @param value {@link String}
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
