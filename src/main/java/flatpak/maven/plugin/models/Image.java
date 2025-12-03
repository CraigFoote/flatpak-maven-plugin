package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * An image as used in flatpak manifest.
 */
@JacksonXmlRootElement(localName = "image")
public class Image {

	private int height;
	private String type = "source";
	private String value;
	private int width;

	/**
	 * Constructor.
	 */
	public Image() {
		// empty
	}

	/**
	 * Get the image's height.
	 *
	 * @return int
	 */
	@JacksonXmlProperty(isAttribute = true)
	public int getHeight() {
		return height;
	}

	/**
	 * Get teh image type.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(isAttribute = true)
	public String getType() {
		return type;
	}

	/**
	 * Gets the image's value or pathname.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlText
	public String getValue() {
		return value;
	}

	/**
	 * Get the image's width.
	 *
	 * @return int
	 */
	@JacksonXmlProperty(isAttribute = true)
	public int getWidth() {
		return width;
	}

	/**
	 * Set the image's height.
	 *
	 * @param height int
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Set the image's type.
	 *
	 * @param type {@link String}
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Set the image's value or pathname.
	 *
	 * @param value {@link String}
	 */
	@JacksonXmlProperty
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Set the image's width.
	 *
	 * @param width int
	 */
	public void setWidth(int width) {
		this.width = width;
	}
}
