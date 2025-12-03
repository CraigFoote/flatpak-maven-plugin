package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * The application's content rating.
 */
@JacksonXmlRootElement(localName = "context_rating")
public class ContentRating {

	private String type;

	/**
	 * Constructor.
	 */
	public ContentRating() {
		// empty
	}

	/**
	 * Get the content rating type.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(isAttribute = true)
	public String getType() {
		return type;
	}

	/**
	 * Set the content rating type.
	 *
	 * @param type {@link String}
	 */
	public void setType(String type) {
		this.type = type;
	}
}
