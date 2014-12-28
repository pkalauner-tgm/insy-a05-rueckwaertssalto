package at.kalaunerritter.rueckwaertssalto;

import at.kalaunerritter.rueckwaertssalto.attributes.BaseAttribute;
import at.kalaunerritter.rueckwaertssalto.attributes.ForeignKey;
import at.kalaunerritter.rueckwaertssalto.attributes.PrimaryKey;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Writes to a file
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141226.1
 */
public class MyFileWriter {
    private static final Logger LOG = LogManager.getLogger(MyFileWriter.class);
    private static final String RM_FILENAME = "rm.html";
    private static final String ERD_FILENAME = "erd.png";

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

        LOG.info("Writing RM to " + f.getAbsolutePath());


        for (Table cur : tables) {
            writer.print(cur.getTablename() + " (");
            writer.println(collectionToString(cur.getAttributes()) + ")<br>");
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


    /**
     * writes the ERD to the file
     *
     * @param tables the collection with the tables
     */
    public static void writeERDToFile(Collection<Table> tables) {
        int counter = 0;
        Set<String> fkConnections = new HashSet<>();

        StringBuilder sbConnections = new StringBuilder();
        StringBuilder sbRelations = new StringBuilder();

        GraphViz gv = new GraphViz();


        gv.addln(gv.start_graph());

        gv.add("node [shape=box]; ");
        for (Table table : tables) {
            gv.add(table.getTablename() + (table.isWeak() ? " [peripheries=2]" : "") + "; ");
        }
        gv.addln();

        gv.add("node [shape=ellipse]; ");
        for (Table table : tables) {
            for (BaseAttribute attr : table.getAttributes()) {
                if (!(attr instanceof ForeignKey)) {
                    gv.add("{node [label=\"" + attr.getValue() + ((attr instanceof PrimaryKey) ? " <PK>" : "") + "\"] " + attr.getValue() + counter + ";};");
                    sbConnections.append(attr.getValue()).append(counter++).append(" -- ").append(table.getTablename()).append(";\n");
                } else {
                    ForeignKey fk = (ForeignKey) attr;
                    String relationName = "\"" + table.getTablename() + "-" + fk.getForeignTable() + "\"";
                    sbRelations.append(relationName).append(";");
                    fkConnections.add(relationName + " -- " + table.getTablename() + " [label=\"1\",len=1.00];\n");
                    fkConnections.add(fk.getForeignTable() + " -- " + relationName + " [label=\"n\",len=1.00];\n");
                }
            }


        }
        gv.addln();

        gv.add("node [shape=diamond,style=filled,color=lightgrey]; " + sbRelations.toString() + "\n");

        gv.add(sbConnections.toString());

        fkConnections.forEach(gv::add);

        gv.add(gv.end_graph());


        LOG.debug("Dot-File Content:\n" + gv.getDotSource());
        File out = new File(ERD_FILENAME);
        LOG.info("Writing ERD to " + out.getAbsolutePath());

        try {
            gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), "png"), out);
        } catch (DotNotInstalledException e) {
            LOG.info("Dot ist nicht installiert. Es konnte kein .png aus dem Dot-File generiert werden. Das tut uns leid.");
        }
    }
}