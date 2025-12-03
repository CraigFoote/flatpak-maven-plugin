package flatpak.maven.plugin.models;

/**
 * Values required to create a .desktop file.
 */
public class DesktopEntry {

	private String categories;
	private String comment;
	private String exec;
	private String icon;
	private boolean ignore;
	private String name;
	private String startupWMClass;
	private String type;

	/**
	 * Constructor.
	 */
	public DesktopEntry() {
		// empty
	}

	/**
	 * Get the categories.
	 *
	 * @return {@link String}
	 */
	public String getCategories() {
		return categories;
	}

	/**
	 * Get the comment.
	 *
	 * @return {@link String}
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Get the exec line.
	 *
	 * @return {@link String}
	 */
	public String getExec() {
		return exec;
	}

	/**
	 * Gets the icon name.
	 *
	 * @return {@link String}
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Get the application's name.
	 *
	 * @return {@link String}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the application's startupWMClass.
	 *
	 * @return {@link String}
	 */
	public String getStartupWMClass() {
		return startupWMClass;
	}

	/**
	 * Get the application type.
	 *
	 * @return {@link String}
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get whether or not to ignore creating the .desktop file.
	 *
	 * @return boolean true if .desktop creation is ignored
	 */
	public boolean isIgnore() {
		return ignore;
	}

	/**
	 * Set the application's categories.
	 *
	 * @param categories {@link String} in the form "cat1;cat2"
	 */
	public void setCategories(String categories) {
		this.categories = categories;
	}

	/**
	 * Set the .desktop file's comment property.
	 *
	 * @param comment {@link String}
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Set the .desktop file's exec property.
	 *
	 * @param exec {@link String}
	 */
	public void setExec(String exec) {
		this.exec = exec;
	}

	/**
	 * Set the .desktop file's icon property.
	 *
	 * @param icon {@link String}
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Set whether or not to ignore the creation of the .desktop file.
	 *
	 * @param ignore boolean
	 */
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	/**
	 * Set the .desktop file's name property.
	 *
	 * @param name {@link String}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the .desktop file's statupWMClass property.
	 *
	 * @param startupWMClass {@link String}
	 */
	public void setStartupWMClass(String startupWMClass) {
		this.startupWMClass = startupWMClass;
	}

	/**
	 * Set the .desktop file's type property.
	 *
	 * @param type {@link String}
	 */
	public void setType(String type) {
		this.type = type;
	}
}
