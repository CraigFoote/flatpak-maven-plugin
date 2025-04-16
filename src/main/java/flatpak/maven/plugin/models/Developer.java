/**
 * 
 */
package flatpak.maven.plugin.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Encapsulates developer information for flatpak metainfo.xml file generation.
 * 
 * @author Footeware.ca
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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
	 * @return the id
	 */
	@JsonProperty(required = true)
	@JacksonXmlProperty(isAttribute = true)
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	@JsonProperty(required = true)
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
