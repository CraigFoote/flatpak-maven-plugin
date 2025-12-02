package flatpak.maven.plugin.models;

import java.io.IOException;
import java.util.List;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;

/**
 * Given a {@link MetaInfo}, creates a DOM {@link Document} that can be used to
 * create a flatpak {@link MetaInfo} file.
 */
public class MetaInfoGenerator {

	private static void addSimpleValue(Element parent, String name, String value) {
		Element element = new Element(name);
		element.appendChild(value);
		parent.appendChild(element);
	}

	private static void addXmlValue(Element parent, String name, String value) throws ParsingException, IOException {
		String xml = String.format("<%s>%s</%s>", name, value, name);
		Document doc = new Builder().build(xml, null);
		parent.appendChild(doc.getRootElement().copy());
	}

	public static Document generate(MetaInfo metaInfo) throws ParsingException, IOException {
		Element root = new Element("component");
		root.addAttribute(new Attribute("type", "desktop-application"));

		// branding
		Element branding = new Element("branding");
		List<Color> colors = metaInfo.getBranding().getColors();
		for (Color color : colors) {
			Element colorElem = new Element("color");
			colorElem.addAttribute(new Attribute("type", color.getType()));
			colorElem.addAttribute(new Attribute("scheme_preference", color.getSchemePreference()));
			colorElem.appendChild(color.getValue());
			branding.appendChild(colorElem);
		}
		root.appendChild(branding);

		// description with HTML tags in value
		addXmlValue(root, "description", metaInfo.getDescription());

		// developer
		Developer developer = metaInfo.getDeveloper();
		Element developerElem = new Element("developer");
		developerElem.addAttribute(new Attribute("id", developer.getId()));
		Element developerName = new Element("name");
		developerName.appendChild(developer.getName());
		developerElem.appendChild(developerName);
		root.appendChild(developerElem);

		// id
		addSimpleValue(root, "id", metaInfo.getId());

		// launchable
		Launchable launchable = metaInfo.getLaunchable();
		Element launchableElem = new Element("launchable");
		launchableElem.addAttribute(new Attribute("type", launchable.getType()));
		launchableElem.appendChild(launchable.getValue());
		root.appendChild(launchableElem);

		// name
		addSimpleValue(root, "name", metaInfo.getName());

		// summary
		addSimpleValue(root, "summary", metaInfo.getSummary());

		// url
		Url url = metaInfo.getUrl();
		Element urlElem = new Element("url");
		urlElem.addAttribute(new Attribute("type", url.getType()));
		urlElem.appendChild(url.getValue());
		root.appendChild(urlElem);

		// contentRating
		ContentRating contentRating = metaInfo.getContentRating();
		Element contentRatingElem = new Element("content_rating");
		contentRatingElem.addAttribute(new Attribute("type", contentRating.getType()));
		root.appendChild(contentRatingElem);

		// metadataLicense
		addSimpleValue(root, "metadata_license", metaInfo.getMetadataLicense());

		// projectGroup
		addSimpleValue(root, "project_group", metaInfo.getProjectGroup());

		// projectLicense
		addSimpleValue(root, "project_license", metaInfo.getProjectLicense());

		// releases
		List<Release> releases = metaInfo.getReleases();
		Element releasesElem = new Element("releases");
		for (Release release : releases) {
			Element releaseElem = new Element("release");
			releaseElem.addAttribute(new Attribute("date", release.getDate()));
			releaseElem.addAttribute(new Attribute("version", release.getVersion()));
			addXmlValue(releaseElem, "description", release.getDescription());
			releasesElem.appendChild(releaseElem);
		}
		root.appendChild(releasesElem);

		// screenshots
		List<Screenshot> screenshots = metaInfo.getScreenshots();
		Element screenshotsElem = new Element("screenshots");
		for (Screenshot screenshot : screenshots) {
			Element screenshotElem = new Element("screenshot");
			if (screenshot.getType() != null && !screenshot.getType().isEmpty()) {
				screenshotElem.addAttribute(new Attribute("type", screenshot.getType()));
			}

			Element captionElem = new Element("caption");
			captionElem.appendChild(screenshot.getCaption());

			Image image = screenshot.getImage();
			screenshotElem.appendChild(captionElem);
			Element imageElem = new Element("image");
			imageElem.addAttribute(new Attribute("height", String.valueOf(image.getHeight())));
			imageElem.addAttribute(new Attribute("type", "source"));
			imageElem.addAttribute(new Attribute("width", String.valueOf(image.getWidth())));
			imageElem.appendChild(screenshot.getImage().getValue());
			screenshotElem.appendChild(imageElem);
			screenshotsElem.appendChild(screenshotElem);
		}
		root.appendChild(screenshotsElem);

		return new Document(root);
	}

	/**
	 * Private constructor because of static methods.
	 */
	private MetaInfoGenerator() {
	}
}
