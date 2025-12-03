package flatpak.maven.plugin.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * The project's icon properties.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "icon")
public class Icon {

	private Integer height;
	private String path;
	private Integer scale;
	private String type;
	private Integer width;

	/**
	 * Constructor.
	 */
	public Icon() {
		// empty
	}

	/**
	 * Gets the icon's height.
	 *
	 * @return {@link Integer}
	 */
	@JsonProperty(value = "height")
	@JacksonXmlProperty(isAttribute = true)
	public Integer getHeight() {
		return height;
	}

	/**
	 * Get the icon's path.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlText
	public String getPath() {
		return path;
	}

	/**
	 * Get the icon's scale.
	 *
	 * @return {@link Integer}
	 */
	@JsonProperty(value = "scale")
	@JacksonXmlProperty(isAttribute = true)
	public Integer getScale() {
		return scale;
	}

	/**
	 * Get the icon's type.
	 *
	 * @return {@link String}
	 */
	@JsonProperty(value = "type")
	@JacksonXmlProperty(isAttribute = true)
	public String getType() {
		return type;
	}

	/**
	 * Get the icon's width.
	 *
	 * @return {@link Integer}
	 */
	@JsonProperty(value = "width")
	@JacksonXmlProperty(isAttribute = true)
	public Integer getWidth() {
		return width;
	}

	/**
	 * Set the icon's height.
	 *
	 * @param height {@link Integer}
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * Set the icon's path.
	 *
	 * @param path {@link String}
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Set the icon's scale.
	 *
	 * @param scale {@link Integer}
	 */
	public void setScale(Integer scale) {
		this.scale = scale;
	}

	/**
	 * Set the icon's type property.
	 *
	 * @param type {@link String}
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Set the icon's width.
	 *
	 * @param width {@link Integer}
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}
}
