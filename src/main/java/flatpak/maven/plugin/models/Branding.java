package flatpak.maven.plugin.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Branding {

	@JacksonXmlProperty(localName = "color")
	private List<Color> colors = new ArrayList<>();

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}

	@JacksonXmlElementWrapper(useWrapping = false)
	public List<Color> getColors() {
		return colors;
	}
}
