package flatpak.maven.plugin.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * An applications descriptor used by flatpak.
 *
 * @see <a href=
 *      "https://www.freedesktop.org/software/appstream/docs/sect-AppStream-YAML.html">AppStream-YAML</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "component")
public class MetaInfo {

	private Branding branding;
	private ContentRating contentRating;
	private String description;
	private Developer developer;
	@JacksonXmlElementWrapper(localName = "icons")
	@JacksonXmlProperty(localName = "icon")
	private List<Icon> icons = new ArrayList<>();
	private String id;
	private Launchable launchable;
	private String metadataLicense;
	private String name;
	private String packageName;
	private String projectGroup;
	private String projectLicense;
	@JacksonXmlElementWrapper(localName = "releases")
	@JacksonXmlProperty(localName = "release")
	private List<Release> releases = new ArrayList<>();
	@JacksonXmlElementWrapper(localName = "screenshots")
	@JacksonXmlProperty(localName = "screenshot")
	private List<Screenshot> screenshots = new ArrayList<>();
	private String sourcePackage;
	private String summary;
	@JacksonXmlProperty(isAttribute = true)
	private String type;
	private Url url;

	/**
	 * Constructor.
	 */
	public MetaInfo() {
		// public
	}

	/**
	 * Get the branding.
	 *
	 * @return {@link Branding}
	 */
	public Branding getBranding() {
		return branding;
	}

	/**
	 * Get the content rating.
	 *
	 * @return {@link ContentRating}
	 */
	@JacksonXmlProperty(localName = "content_rating")
	public ContentRating getContentRating() {
		return contentRating;
	}

	/**
	 * Get the description.
	 *
	 * @return {@link String}
	 */
	@JsonRawValue
	public String getDescription() {
		return description;
	}

	/**
	 * Get the developer.
	 *
	 * @return {@link Developer}
	 */
	public Developer getDeveloper() {
		return developer;
	}

	/**
	 * Get the icons.
	 *
	 * @return {@link List} of {@link Icon}
	 */
	@JacksonXmlElementWrapper(useWrapping = false)
	public List<Icon> getIcons() {
		return icons;
	}

	/**
	 * Get the id.
	 *
	 * @return {@link String}
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the launchable.
	 *
	 * @return {@link Launchable}
	 */
	public Launchable getLaunchable() {
		return launchable;
	}

	/**
	 * Get the metadata license.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(localName = "metadata_license")
	public String getMetadataLicense() {
		return metadataLicense;
	}

	/**
	 * Get the name.
	 *
	 * @return {@link String}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the package name.
	 *
	 * @return {@link String}
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * Get the project group.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(localName = "project_group")
	public String getProjectGroup() {
		return projectGroup;
	}

	/**
	 * Get the project license.
	 *
	 * @return {@link String}
	 */
	@JacksonXmlProperty(localName = "project_license")
	public String getProjectLicense() {
		return projectLicense;
	}

	/**
	 * Get the releases.
	 *
	 * @return {@link List} of {@link Release}
	 */
	public List<Release> getReleases() {
		return releases;
	}

	/**
	 * Get the screenshots.
	 *
	 * @return {@link List} of {@link Screenshot}
	 */
	public List<Screenshot> getScreenshots() {
		return screenshots;
	}

	/**
	 * Get the source package.
	 *
	 * @return {@link String}
	 */
	public String getSourcePackage() {
		return sourcePackage;
	}

	/**
	 * Get the summary.
	 *
	 * @return {@link String}
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Get the type.
	 *
	 * @return {@link String}
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get the URL.
	 *
	 * @return {@link Url}
	 */
	public Url getUrl() {
		return url;
	}

	/**
	 * Set the branding.
	 *
	 * @param branding {@link Branding}
	 */
	public void setBranding(Branding branding) {
		this.branding = branding;
	}

	/**
	 * Set the content rating.
	 *
	 * @param contentRating {@link ContentRating}
	 */
	public void setContentRating(ContentRating contentRating) {
		this.contentRating = contentRating;
	}

	/**
	 * Set the description.
	 *
	 * @param description {@link String}
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Set the developer.
	 *
	 * @param developer {@link Developer}
	 */
	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	/**
	 * Set the id.
	 *
	 * @param id {@link String}
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Set the launchable.
	 *
	 * @param launchable {@link Launchable}
	 */
	public void setLaunchable(Launchable launchable) {
		this.launchable = launchable;
	}

	/**
	 * Set the metadata license.
	 *
	 * @param metaDataLicense {@link String}
	 */
	public void setMetadataLicense(String metaDataLicense) {
		this.metadataLicense = metaDataLicense;
	}

	/**
	 * Set the name.
	 *
	 * @param name {@link String}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the package name.
	 *
	 * @param packageName {@link String}
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * Set the project group.
	 *
	 * @param projectGroup {@link String}
	 */
	public void setProjectGroup(String projectGroup) {
		this.projectGroup = projectGroup;
	}

	/**
	 * Set the project license.
	 *
	 * @param projectLicense {@link String}
	 */
	public void setProjectLicense(String projectLicense) {
		this.projectLicense = projectLicense;
	}

	/**
	 * Set the releases.
	 *
	 * @param releases {@link List} of {@link Release}
	 */
	public void setReleases(List<Release> releases) {
		this.releases = releases;
	}

	/**
	 * Set the source package.
	 *
	 * @param sourcePackage {@link String}
	 */
	public void setSourcePackage(String sourcePackage) {
		this.sourcePackage = sourcePackage;
	}

	/**
	 * Set the summary.
	 *
	 * @param summary {@link String}
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * Set the type.
	 *
	 * @param type {@link String}
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Set the URL.
	 *
	 * @param url {@link Url}
	 */
	public void setUrl(Url url) {
		this.url = url;
	}
}
