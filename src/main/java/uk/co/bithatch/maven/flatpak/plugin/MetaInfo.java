package uk.co.bithatch.maven.flatpak.plugin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
	private String metaDataLicense;
	private String description;
	private String developerName;
	private String projectGroup;
	private Map<String, String> url = new LinkedHashMap<>();
	private List<Icon> icons = new ArrayList<>();

	@JsonProperty(value = "type")
    @JacksonXmlProperty(isAttribute = true)
	public final String getType() {
		return type;
	}

	public final void setType(String type) {
		this.type = type;
	}

	@JsonProperty(value = "id", index = 0)
	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	@JsonProperty(value = "merge", index = 3)
	public final String getMerge() {
		return merge;
	}

	public final void setMerge(String merge) {
		this.merge = merge;
	}

	@JsonProperty(value = "pkgname", index = 4)
	public final String getPackageName() {
		return packageName;
	}

	public final void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@JsonProperty(value = "source_pkgname", index = 5)
	public final String getSourcePackage() {
		return sourcePackage;
	}

	public final void setSourcePackage(String sourcePackage) {
		this.sourcePackage = sourcePackage;
	}

	@JsonProperty(value = "name", index = 6)
	public final String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty(value = "summary", index = 7)
	public final String getSummary() {
		return summary;
	}

	@JsonProperty(value = "summary", index = 7)
	public final void setSummary(String summary) {
		this.summary = summary;
	}

	@JsonProperty(value = "project_license", index = 8)
	public final String getProjectLicense() {
		return projectLicense;
	}

	public final void setProjectLicense(String projectLicense) {
		this.projectLicense = projectLicense;
	}

	@JsonProperty(value = "metadata_license", index = 9)
	public final String getMetaDataLicense() {
		return metaDataLicense;
	}

	public final void setMetaDataLicense(String metaDataLicense) {
		this.metaDataLicense = metaDataLicense;
	}

	@JsonProperty(value = "description", index = 10)
	@JsonRawValue
	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty(value = "url", index = 11)
	public final Map<String, String> getUrl() {
		return url;
	}

	@JsonProperty(value = "project_group", index = 12)
	public final String getProjectGroup() {
		return projectGroup;
	}

	public final void setProjectGroup(String projectGroup) {
		this.projectGroup = projectGroup;
	}

	@JsonProperty(value = "developer_name", index = 13)
	public final String getDeveloperName() {
		return developerName;
	}

	public final void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

    @JacksonXmlElementWrapper(useWrapping = false)
	@JsonProperty(value = "icon", index = 14)
	public final List<Icon> getIcons() {
		return icons;
	}
}
