/**
 *
 */
package flatpak.maven.plugin.exceptions;

/**
 * Describes an exception that occurs during metaInfo xml file generation.
 */
public class MetaInfoException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 *
	 * @param message {@link String}
	 */
	public MetaInfoException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param message   {@link String}
	 * @param throwable {@link Throwable}
	 */
	public MetaInfoException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
