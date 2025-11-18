package flatpak.maven.plugin.models;

public class DesktopEntry {

	private String categories;
	private String comment;
	private String exec;
	private String icon;
	private boolean ignore;
	private String name;
	private String startupWMClass;
	private String type;

	public String getCategories() {
		return categories;
	}

	public final String getComment() {
		return comment;
	}

	public final String getExec() {
		return exec;
	}

	public final String getIcon() {
		return icon;
	}

	public final String getName() {
		return name;
	}

	public String getStartupWMClass() {
		return startupWMClass;
	}

	public final String getType() {
		return type;
	}

	public final boolean isIgnore() {
		return ignore;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public final void setComment(String comment) {
		this.comment = comment;
	}

	public final void setExec(String exec) {
		this.exec = exec;
	}

	public final void setIcon(String icon) {
		this.icon = icon;
	}

	public final void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public void setStartupWMClass(String startupWMClass) {
		this.startupWMClass = startupWMClass;
	}

	public final void setType(String type) {
		this.type = type;
	}
}
