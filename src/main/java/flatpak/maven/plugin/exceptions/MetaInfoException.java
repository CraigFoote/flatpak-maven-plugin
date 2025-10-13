/**
 * 
 */
package flatpak.maven.plugin.exceptions;

/**
 * Describes an exception that occurs during metaInfo xml file generation.
 * 
 * @author Footeware.ca
 */
public class MetaInfoException extends Exception {

	private static final long serialVersionUID = 1L;

	public MetaInfoException(String message) {
		super(message);
	}

	public MetaInfoException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
