package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * The project's developer.
 */
public class Developer {

	private String id;
	private String name;

	/**
	 * Constructor.
	 *
	 * @param id   {@link String}
	 * @param name {@link String}
	 */
	public Developer(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Get the developer's id.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(isAttribute = true)
	public String getId() {
		return id;
	}

	/**
	 * Get the developer's name.
	 *
	 * @return {@link String}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the developer's id.
	 *
	 * @param id {@link String}
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Set the developer's name.
	 *
	 * @param name {@link String}
	 */
	public void setName(String name) {
		this.name = name;
	}
}
