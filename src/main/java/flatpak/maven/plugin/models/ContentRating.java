package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "context_rating")
public class ContentRating {

	private String type;

	@JacksonXmlProperty(isAttribute = true)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
