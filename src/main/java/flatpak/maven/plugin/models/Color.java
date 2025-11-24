package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JacksonXmlRootElement(localName = "color")
public class Color {

	private String schemePreference;
	private String type = "primary";
	private String value;

	@JacksonXmlProperty(localName = "scheme_preference", isAttribute = true)
	public String getSchemePreference() {
		return schemePreference;
	}

	@JacksonXmlProperty(isAttribute = true)
	public String getType() {
		return type;
	}

	@JacksonXmlText
	public String getValue() {
		return value;
	}

	public void setSchemePreference(String schemePreference) {
		this.schemePreference = schemePreference;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
