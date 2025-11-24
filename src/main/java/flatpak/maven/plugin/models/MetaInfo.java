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

	private Branding branding;
	private String description;
	private Developer developer;
	@JacksonXmlElementWrapper(localName = "icons")
	@JacksonXmlProperty(localName = "icon")
	private List<Icon> icons = new ArrayList<>();
	private String id;
	private Launchable launchable;
	private String merge;
	private String metadataLicense;
	private String name;
	private String packageName;
	private String projectGroup;
	private String projectLicense;
	@JacksonXmlElementWrapper(localName = "screenshots")
	@JacksonXmlProperty(localName = "screenshot")
	private List<Screenshot> screenshots = new ArrayList<>();
	private String sourcePackage;
	private String summary;
	private String type;
	private Url url;

	public Branding getBranding() {
		return branding;
	}

	@JsonRawValue
	public final String getDescription() {
		return description;
	}

	public Developer getDeveloper() {
		return developer;
	}

	@JacksonXmlElementWrapper(useWrapping = false)
	public final List<Icon> getIcons() {
		return icons;
	}

	public final String getId() {
		return id;
	}

	public Launchable getLaunchable() {
		return launchable;
	}

	public final String getMerge() {
		return merge;
	}

	@JacksonXmlProperty(localName = "metadata_license")
	public final String getMetadataLicense() {
		return metadataLicense;
	}

	public final String getName() {
		return name;
	}

	public final String getPackageName() {
		return packageName;
	}

	public final String getProjectGroup() {
		return projectGroup;
	}

	@JacksonXmlProperty(localName = "project_license")
	public final String getProjectLicense() {
		return projectLicense;
	}

	@JacksonXmlElementWrapper(useWrapping = true, localName = "screenshots")
	public List<Screenshot> getScreenshots() {
		return screenshots;
	}

	public final String getSourcePackage() {
		return sourcePackage;
	}

	public final String getSummary() {
		return summary;
	}

	@JacksonXmlProperty(isAttribute = true)
	public final String getType() {
		return type;
	}

	public Url getUrl() {
		return url;
	}

	public void setBranding(Branding branding) {
		this.branding = branding;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public void setLaunchable(Launchable launchable) {
		this.launchable = launchable;
	}

	public final void setMerge(String merge) {
		this.merge = merge;
	}

	public final void setMetadataLicense(String metaDataLicense) {
		this.metadataLicense = metaDataLicense;
	}

	public void setName(String name) {
		this.name = name;
	}

	public final void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public final void setProjectGroup(String projectGroup) {
		this.projectGroup = projectGroup;
	}

	public final void setProjectLicense(String projectLicense) {
		this.projectLicense = projectLicense;
	}

	public final void setSourcePackage(String sourcePackage) {
		this.sourcePackage = sourcePackage;
	}

	public final void setSummary(String summary) {
		this.summary = summary;
	}

	public final void setType(String type) {
		this.type = type;
	}

	public void setUrl(Url url) {
		this.url = url;
	}
}
