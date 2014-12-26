package at.kalaunerritter.rueckwaertssalto;

import at.kalaunerritter.rueckwaertssalto.attributes.BaseAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Writes to a file
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141226.1
 */
public class MyFileWriter {
    private static final Logger LOG = LogManager.getLogger(MyFileWriter.class);
    private static final String RM_FILENAME = "rm.html";

    /**
     * Writes the RM to the file
     *
     * @param tables the collection with the tables
     */
    public static void writeRmToFile(Collection<Table> tables) {
        PrintWriter writer;
        File f = new File(RM_FILENAME);
        // if file exists already, delete it
        if (f.exists()) {
            //noinspection ResultOfMethodCallIgnored
            f.delete();
        }
        try {
            writer = new PrintWriter(new FileWriter(f));
        } catch (IOException e) {
            LOG.error("Could not open file", e);
            return;
        }

        LOG.info("Writing to " + f.getAbsolutePath());


        for (Table cur : tables) {
            writer.print(cur.getTablename() + ": ");
            writer.println(collectionToString(cur.getAttributes()) + "<br>");
        }
        writer.flush();
    }

    /**
     * Converts the content of a Collection to a String. <br>
     * (We could also use the {@code toString()} method, but we do not want the [])
     *
     * @param col Collection with BaseAttributes
     * @return String with the content of the given Collection
     */
    private static String collectionToString(Collection<BaseAttribute> col) {
        StringBuilder sb = new StringBuilder();
        for (BaseAttribute cur : col)
            sb.append(cur.getHTMLValue()).append(", ");
        return sb.substring(0, sb.lastIndexOf(", "));
    }
}
