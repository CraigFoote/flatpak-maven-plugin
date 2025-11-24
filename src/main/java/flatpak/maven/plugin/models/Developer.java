/**
 *
 */
package flatpak.maven.plugin.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Developer {

	private String id;
	private String name;
	// TODO email, etc

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
	 * @return the id
	 */
	@JacksonXmlProperty(isAttribute = true)
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
