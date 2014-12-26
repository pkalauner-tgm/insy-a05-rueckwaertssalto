package at.kalaunerritter;

import at.kalaunerritter.attributes.BaseAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Created by Paul on 26.12.14.
 */
public class MyFileWriter {
    private static final Logger LOG = LogManager.getLogger(MyFileWriter.class);
    private static final String RM_FILENAME = "rm.html";

    public static void writeRmToFile(Collection<Table> tables) throws IOException {
        PrintWriter writer;
        File f = new File(RM_FILENAME);
        // if file exists already, delete it
        if (f.exists()) {
            //noinspection ResultOfMethodCallIgnored
            f.delete();
        }
        writer = new PrintWriter(new FileWriter(f));
        LOG.info("Writing to " + f.getAbsolutePath());


        for (Table cur : tables) {
            writer.print(cur.getTablename() + ": ");
            writer.println(collectionToString(cur.getAttributes()) + "<br>");
        }
        writer.flush();
    }

    private static String collectionToString(Collection<BaseAttribute> col) {
        StringBuilder sb = new StringBuilder();
        for (BaseAttribute cur : col)
            sb.append(cur.getHTMLValue()).append(", ");
        return sb.substring(0, sb.lastIndexOf(", "));
    }
}
