package at.kalaunerritter.rueckwaertssalto.dbloader;

import at.kalaunerritter.rueckwaertssalto.attributes.BaseAttribute;
import com.mysql.jdbc.DatabaseMetaData;
import org.junit.Before;
import org.junit.Test;


import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Testen des AttributeLoaders
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141228.1
 */
public class AttributeLoaderTest {

    private Connection con;

    @Before
    public void setUp() throws Exception {

        //Erstellen von Mock-Objekten
        DatabaseMetaData dbmd = mock(com.mysql.jdbc.DatabaseMetaData.class);
        con = mock(Connection.class);
        ResultSet rs = mock(ResultSet.class);
        ResultSet pks = mock(ResultSet.class);
        ResultSet foreignKeys = mock(ResultSet.class);
        ResultSet uniques = mock(ResultSet.class);


        //Connection mocken
        when(con.getCatalog()).thenReturn("Bla");

        //ResultSet mocken
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getString("COLUMN_NAME")).thenReturn("Column");

        //ResultSet der PrimaryKeys mocken
        when(pks.next()).thenReturn(true).thenReturn(false);
        when(pks.getString("COLUMN_NAME")).thenReturn("Column");

        //ResultSet der ForeignKeys mocken
        when(foreignKeys.next()).thenReturn(true).thenReturn(false);
        when(foreignKeys.getString("FKCOLUMN_NAME")).thenReturn("Column");
        when(foreignKeys.getString("PKTABLE_NAME")).thenReturn("ForeignTable");
        when(foreignKeys.getString("PKCOLUMN_NAME")).thenReturn("Column");

        //ResultSet der Unique Attributes mocken
        when(uniques.getString("INDEX_NAME")).thenReturn(null);

        //DB-Metadaten mocken
        when(dbmd.getColumns(null, null, "Table", null)).thenReturn(rs);
        when(dbmd.getPrimaryKeys(con.getCatalog(), null, "Table")).thenReturn(pks);
        when(dbmd.getImportedKeys(con.getCatalog(), null, "Table")).thenReturn(foreignKeys);
        when(dbmd.getIndexInfo(con.getCatalog(), null, "Table", true, true)).thenReturn(uniques);

        //Connection mocken
        when(con.getMetaData()).thenReturn(dbmd);
    }

    /**
     * Testen, ob Attribute korrekt verarbeitet werden
     */
    @Test
    public void testLoadAttributes() {
        AttributeLoader al = new AttributeLoader(con);
        Collection<BaseAttribute> attributes = al.loadAttributes("Table");
        assertEquals("[ForeignTable.Column]", attributes.toString());
    }
}