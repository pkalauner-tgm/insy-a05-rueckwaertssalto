package at.kalaunerritter.rueckwaertssalto;

import org.apache.commons.cli.*;

import java.io.Console;
import java.util.Scanner;

/**
 * Checks the given arguments
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141207.1
 */
public class ArgumentChecker {

    private Exporter exporter;
    private String[] args;

    /**
     * Initializes the argument checker
     *
     * @param args     arguments to validate
     * @param exporter the exporter instance
     */
    public ArgumentChecker(String[] args, Exporter exporter) {
        this.args = args;
        this.exporter = exporter;
    }

    /**
     * checks the given arguments
     *
     * @return true if valid
     */
    @SuppressWarnings("AccessStaticViaInstance")
    public boolean checkArgs() {
        Options options = new Options();
        options.addOption(OptionBuilder.hasArg().withArgName("hostname").withDescription("Hostname des DBMS. Standard: localhost").create('h'));
        options.addOption(OptionBuilder.hasArg().withArgName("username").withDescription("Benutzername. Standard: Benutzername des im Betriebssystem angemeldeten Benutzers").create('u'));
        options.addOption(OptionBuilder.hasOptionalArg().withArgName("password").withDescription("Passwort. Falls kein Argument mit dem Passwort folgt, wird ein Passwortprompt angezeigt. Standard: kein Passwort").create('p'));
        options.addOption(OptionBuilder.hasArg().withArgName("database_name").isRequired().withDescription("Name der Datenbank (Erforderlich)").create('d'));
        options.addOption(OptionBuilder.hasArg().withArgName("sorting_fieldname").withDescription("Feld, nach dem sortiert werden soll (nur eines moeglich, Standard: keines)").create('s'));
        options.addOption(OptionBuilder.hasArg().withArgName("sortingdirection").withDescription("Sortierrichtung. Nur ASC und DESC ist moeglich. Standard: ASC").create('r'));
        options.addOption(OptionBuilder.hasArg().withArgName("condition").withDescription("eine Bedingung in SQL-Syntax, die zum Filtern der Tabelle verwendet wird. Standard: keine").create('w'));
        options.addOption(OptionBuilder.hasArg().withArgName("delimiter").withDescription("Trennzeichen, dass fuer die Ausgabe verwendet werden soll. Standard: ; ").create('t'));
        options.addOption(OptionBuilder.hasArg().withArgName("fields").isRequired().withDescription("Kommagetrennte Liste (ohne Leerzeichen) der Felder, die im Ergebnis enthalten sein sollen. * ist auch moeglich. (Erforderlich)").create('f'));
        options.addOption(OptionBuilder.hasArg().withArgName("tablename").isRequired().withDescription("Name der Tabelle (Erforderlich)").create('T'));
        options.addOption(OptionBuilder.hasArg().withArgName("filename").withDescription("Name der Ausgabedatei. Standard: keine -> Ausgabe auf der Konsole").create('o'));
        HelpFormatter hf = new HelpFormatter();
        CommandLineParser parser = new BasicParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            exporter.setDatabase(cmd.getOptionValue('d'));
            exporter.setTablename(cmd.getOptionValue('T'));

            String fields = cmd.getOptionValue('f');

            // if length without spaces does not equal the length with spaces
            if (fields.replace(" ", "").length() != fields.length()) {
                System.out.println("Liste der Felder darf keine Leerzeichen enthalten");
                return false;
            }

            // split string by ,
            exporter.setFields(fields.split(","));

            // hostname
            if (cmd.hasOption('h')) {
                exporter.setHostname(cmd.getOptionValue('h'));
            }

            // username
            if (cmd.hasOption('u')) {
                exporter.setUsername(cmd.getOptionValue('u'));
            }

            // password
            if (cmd.hasOption('p')) {
                String pw = cmd.getOptionValue('p');
                if (pw != null)
                    exporter.setPassword(pw);
                else
                    readPassword();
            }

            // sorting field
            if (cmd.hasOption('s')) {
                exporter.setSortingField(cmd.getOptionValue('s'));
            }

            // sorting direction
            if (cmd.hasOption('r')) {
                if (!cmd.hasOption('s')) {
                    System.out.println("-r ist nur mit -s moeglich");
                    return false;
                }
                String dir = cmd.getOptionValue('r');
                if (!dir.equalsIgnoreCase("ASC") && !dir.equalsIgnoreCase("DESC")) {
                    System.out.println("Ungueltige Sortierrichtung. Nur ASC und DESC ist moeglich.");
                    return false;
                }
                exporter.setSortingDir(dir);
            }

            // SQL condition
            if (cmd.hasOption('w')) {
                exporter.setCondition(cmd.getOptionValue('w'));
            }

            // delimiter
            if (cmd.hasOption('t')) {
                String del = cmd.getOptionValue('t');
                if (del.length() > 1) {
                    System.out.println("Ungueltiges Trennzeichen");
                    return false;
                }
                exporter.setDelimiter(del.charAt(0));
            }

            // output file name
            if (cmd.hasOption('o')) {
                exporter.setOutputFileName(cmd.getOptionValue('o'));
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            hf.printHelp("java -jar A04.jar", options, true);
            return false;
        }
        return true;
    }

    /**
     * Reads the password from the console
     */
    public void readPassword() {
        System.out.print("Geben Sie Ihr Passwort ein: ");
        Console c = System.console(); // Only works in real console (not in an IDE console)
        if (c != null)
            exporter.setPassword(String.valueOf(System.console().readPassword()));
        else
            exporter.setPassword(new Scanner(System.in).nextLine());
    }
}