package at.kalaunerritter.rueckwaertssalto;

import at.kalaunerritter.rueckwaertssalto.attributes.BaseAttribute;
import at.kalaunerritter.rueckwaertssalto.attributes.ForeignKey;
import at.kalaunerritter.rueckwaertssalto.dbloader.Table;
import fr.loria.graphviz.DotNotInstalledException;
import fr.loria.graphviz.GraphViz;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Writes to a file
 *
 * @author Paul Kalauner 4AHIT
 * @author Mathias Ritter 4AHIT
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
            writer.println(listToString(cur.getAttributes()) + ")<br>");
        }
        writer.flush();
    }

    /**
     * Converts the content of a List to a String. <br>
     * (We could also use the {@code toString()} method, but we do not want the [])
     *
     * @param list List with BaseAttributes
     * @return String with the content of the given Collection
     */
    private static String listToString(List<BaseAttribute> list) {
        StringBuilder sb = new StringBuilder();
        for (BaseAttribute cur : list)
            sb.append(cur.getHTMLValue()).append(", ");
        return sb.substring(0, sb.lastIndexOf(", "));
    }


    /**
     * writes the ERD to the file
     *
     * @param tables list with the tables
     */
    public static void writeERDToFile(List<Table> tables) {
        int counter = 0;
        Set<String> fkConnections = new HashSet<>();

        StringBuilder sbConnections = new StringBuilder();
        StringBuilder sbRelations = new StringBuilder();

        GraphViz gv = new GraphViz();


        gv.addln(gv.start_graph());

        // Entitaeten generieren
        gv.add("node [shape=box]; ");
        for (Table table : tables) {
            // Falls es sich um eine schwache Entitaet handelt, mit doppeltem Rahmen darstellen (peripheries=2)
            gv.add(table.getTablename() + (table.isWeak() ? " [peripheries=2]" : "") + "; ");
        }
        gv.addln();

        // Attribute generieren
        gv.add("node [shape=ellipse]; ");
        // Fuer jede Tabelle...
        for (Table table : tables) {
            boolean weak = table.isWeak();
            // durch alle Attribute jeder Tabelle iterieren
            for (BaseAttribute attr : table.getAttributes()) {
                if (!(attr instanceof ForeignKey)) {
                    // Falls es sich nicht um einen Foreign Key handelt, stelle das Attribut dar
                    gv.add("{node [label=<" + attr.getHTMLValue() + ">] " + attr.getValue() + counter + ";};");
                    // Tabelle mit Attribut durch einen Strich verbinden
                    sbConnections.append(attr.getValue()).append(counter++).append(" -- ").append(table.getTablename()).append(";\n");
                } else {
                    // Falls es sich schon um einen Foreign Key handelt, erzeuge Relation
                    ForeignKey fk = (ForeignKey) attr;
                    String relationName = "\"" + table.getTablename() + "-" + fk.getForeignTable() + "\"";
                    // Falls es sich um einen Foreign Key handelt, stelle den Rahmen der Beziehung doppelt dar
                    sbRelations.append(relationName).append(weak ? " [peripheries=2]" : "").append(";");
                    // Relation mit Tabellen verbinden. Hierbei wird ein Set verwendet, um doppelte Verbindungen zu vermeiden
                    // Falls es sich um einen Foreign Key handelt stelle die Verbindung mit doppelten Linien dar
                    fkConnections.add(relationName + " -- " + table.getTablename() + " [label=\"1\",len=1.00" + (weak ? ",color=\"black:white:black\"" : "") + "];\n");
                    fkConnections.add(fk.getForeignTable() + " -- " + relationName + " [label=\"n\",len=1.00];\n");
                }
            }


        }
        gv.addln();

        // Relationen (Diamanten) zum Dot-File hinzufuegen
        gv.add("node [shape=diamond]; " + sbRelations.toString() + "\n");

        // Verbindungslinien zum Dot-File hinzufuegen
        gv.add(sbConnections.toString());

        // ForeignKey Verbindungen zum Dot-File hinzufuegen
        fkConnections.forEach(gv::add);

        gv.add(gv.end_graph());


        LOG.debug("Dot-File Content:\n" + gv.getDotSource());
        File out = new File(ERD_FILENAME);
        LOG.info("Writing ERD to " + out.getAbsolutePath());

        try {
            // In Dot File und falls Dot installiert ist, png erzeugen.
            gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), "png"), out);
        } catch (DotNotInstalledException e) {
            LOG.info("Dot ist nicht installiert. Es konnte kein .png aus dem Dot-File generiert werden. Das tut uns leid.");
        }
    }
}