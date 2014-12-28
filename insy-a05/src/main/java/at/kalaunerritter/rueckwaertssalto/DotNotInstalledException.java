package at.kalaunerritter.rueckwaertssalto;

/**
 * Indicates that Dot is not installed
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141228.1
 */
public class DotNotInstalledException extends Exception {
    /**
     * @param message message
     * @see java.lang.Exception#Exception(String)
     */
    public DotNotInstalledException(String message) {
        super(message);
    }
}
