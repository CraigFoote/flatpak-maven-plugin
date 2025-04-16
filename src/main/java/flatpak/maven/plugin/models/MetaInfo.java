package flatpak.maven.plugin.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * https://www.freedesktop.org/software/appstream/docs/sect-AppStream-YAML.html
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "component")
public class MetaInfo {

	private String id;
	private String type;
	private String merge;
	private String packageName;
	private String sourcePackage;
	private String name;
	private String summary;
	private String projectLicense;
	private String metadataLicense;
	private String description;
	private String projectGroup;
	private Url url;
	private List<Icon> icons = new ArrayList<>();
	private Developer developer;
	private Launchable launchable;

	@JacksonXmlProperty(isAttribute = true)
	public final String getType() {
		return type;
	}

	public final void setType(String type) {
		this.type = type;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getMerge() {
		return merge;
	}

	public final void setMerge(String merge) {
		this.merge = merge;
	}

	public final String getPackageName() {
		return packageName;
	}

	public final void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public final String getSourcePackage() {
		return sourcePackage;
	}

	public final void setSourcePackage(String sourcePackage) {
		this.sourcePackage = sourcePackage;
	}

	public final String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public final String getSummary() {
		return summary;
	}

	public final void setSummary(String summary) {
		this.summary = summary;
	}

	@JacksonXmlProperty(localName = "project_license")
	public final String getProjectLicense() {
		return projectLicense;
	}

	public final void setProjectLicense(String projectLicense) {
		this.projectLicense = projectLicense;
	}

	@JacksonXmlProperty(localName = "metadata_license")
	public final String getMetadataLicense() {
		return metadataLicense;
	}

	public final void setMetadataLicense(String metaDataLicense) {
		this.metadataLicense = metaDataLicense;
	}

	@JsonRawValue
	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the url
	 */
	public Url getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(Url url) {
		this.url = url;
	}

	public final String getProjectGroup() {
		return projectGroup;
	}

	public final void setProjectGroup(String projectGroup) {
		this.projectGroup = projectGroup;
	}

	@JacksonXmlElementWrapper(useWrapping = false)
	public final List<Icon> getIcons() {
		return icons;
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	/**
	 * @return the launchable
	 */
	public Launchable getLaunchable() {
		return launchable;
	}

	/**
	 * @param launchable the launchable to set
	 */
	public void setLaunchable(Launchable launchable) {
		this.launchable = launchable;
	}
}
