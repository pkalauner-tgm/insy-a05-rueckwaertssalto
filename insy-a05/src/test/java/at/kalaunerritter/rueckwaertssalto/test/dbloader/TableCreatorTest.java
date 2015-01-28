package at.kalaunerritter.rueckwaertssalto.test.dbloader;

import at.kalaunerritter.rueckwaertssalto.dbloader.Table;
import at.kalaunerritter.rueckwaertssalto.dbloader.TableCreator;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the TableCreator
 *
 * @author Paul Kalauner
 * @version 20141228.1
 */
public class TableCreatorTest {

    /**
     * Tests the loadTables functionality
     *
     * @throws SQLException should not happen
     */
    @Test
    public void testLoadTables() throws SQLException {
        Connection mockedCon = mock(Connection.class);
        DatabaseMetaData mockedMd = mock(DatabaseMetaData.class);
        ResultSet mockedRs = mock(ResultSet.class);
        when(mockedRs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockedRs.getString(3)).thenReturn("table1").thenReturn("table2").thenReturn("table3");
        when(mockedCon.getMetaData()).thenReturn(mockedMd);
        when(mockedMd.getTables(null, null, "%", null)).thenReturn(mockedRs);
        TableCreator tc = new TableCreator(mockedCon);
        tc.loadTables(false);
        List<Table> tables = tc.getTables();
        assertEquals("table1", tables.get(0).getTablename());
        assertEquals("table2", tables.get(1).getTablename());
        assertEquals("table3", tables.get(2).getTablename());
    }
}