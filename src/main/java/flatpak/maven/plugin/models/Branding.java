package flatpak.maven.plugin.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Application branding colors used in GNOME Software.
 */
public class Branding {

	@JacksonXmlProperty(localName = "color")
	private List<Color> colors = new ArrayList<>();

	/**
	 * Constructor.
	 */
	public Branding() {
		// empty
	}

	/**
	 * Gets the branding colors.
	 *
	 * @return {@link List} of {@link Color}
	 */
	@JacksonXmlElementWrapper(useWrapping = false)
	public List<Color> getColors() {
		return colors;
	}

	/**
	 * Sets the branding colors.
	 *
	 * @param colors {@link List} of {@link Color}
	 */
	public void setColors(List<Color> colors) {
		this.colors = colors;
	}
}
