package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JacksonXmlRootElement(localName = "image")
public class Image {

	private int height;
	private String type = "source";
	private String value;
	private int width;

	@JacksonXmlProperty(isAttribute = true)
	public int getHeight() {
		return height;
	}

	@JacksonXmlProperty(isAttribute = true)
	public String getType() {
		return type;
	}

	@JacksonXmlText
	public String getValue() {
		return value;
	}

	@JacksonXmlProperty(isAttribute = true)
	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JacksonXmlProperty
	public void setValue(String value) {
		this.value = value;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
