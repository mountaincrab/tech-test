package com.crabware.techtest.databasecli;

import com.crabware.techtest.databasecli.util.QueryResult;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class TestDisplayHelper {

    @Test
    public void correctPaddingAddedToString() {
        assertEquals("123  ", DisplayHelper.rightPad("123", 5));
    }

    @Test
    public void queryResultRenderedToString() throws SQLException {
        String[] columnNames = new String[]{"name", "age", "weight"};
        String[][] data = new String[][]{
                new String[]{"John Bon", "34", "78"},
                new String[]{"Marks Getsetgo", "19", "103"},
                new String[]{"Joe King", "27", "192"},
                new String[]{"Phil Lanthropy", null, "96"},
        };

        ResultSet resultSet = ResultSetMock.create(columnNames, data);
        QueryResult queryResult = QueryResult.from(resultSet);

        String rendered = DisplayHelper.render(queryResult, columnNames);
        String[] split = rendered.split(System.lineSeparator());
        assertEquals("name            age  weight  ", split[0]);
        assertEquals("John Bon        34   78      ", split[1]);
        assertEquals("Marks Getsetgo  19   103     ", split[2]);
    }
}
