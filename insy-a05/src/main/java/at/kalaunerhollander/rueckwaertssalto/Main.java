package at.kalaunerhollander.rueckwaertssalto;

/**
 * Main class <br>
 * Start of the program
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141207.1
 */
public class Main {
    /**
     * Entry point of the program
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Exporter exporter = new Exporter();
        ArgumentChecker ac = new ArgumentChecker(args, exporter);
        // Invalid arguments --> exit program
        if (!ac.checkArgs()) {
            System.exit(1);
        }
        exporter.export();
    }
}