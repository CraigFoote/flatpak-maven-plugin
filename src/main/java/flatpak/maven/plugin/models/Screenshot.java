package flatpak.maven.plugin.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * A screenshot image of the application used in GNOME Software.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "screenshot")
public class Screenshot {

	private String caption;
	private Image image;
	private String type;

	/**
	 * Constructor.
	 */
	public Screenshot() {
		// empty
	}

	/**
	 * Get the caption.
	 *
	 * @return {@link String}
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * Get the image.
	 *
	 * @return {@link Image}
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Get the type.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(isAttribute = true)
	@JsonProperty(required = false)
	public String getType() {
		return type;
	}

	/**
	 * Set the caption.
	 *
	 * @param caption {@link String}
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * Set the image.
	 *
	 * @param image {@link Image}
	 */
	@JacksonXmlProperty(localName = "image")
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Set the type.
	 *
	 * @param type {@link String}
	 */
	public void setType(String type) {
		this.type = type;
	}
}
