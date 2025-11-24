package flatpak.maven.plugin.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "screenshot")
public class Screenshot {

	private String caption;
	private Image image;
	private String type;

	public String getCaption() {
		return caption;
	}

	public Image getImage() {
		return image;
	}

	@JacksonXmlProperty(isAttribute = true)
	@JsonProperty(required = false)
	public String getType() {
		return type;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@JacksonXmlProperty(localName = "image")
	public void setImage(Image image) {
		this.image = image;
	}

	public void setType(String type) {
		this.type = type;
	}
}
