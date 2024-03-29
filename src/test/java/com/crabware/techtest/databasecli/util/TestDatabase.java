package com.crabware.techtest.databasecli.util;

import com.crabware.techtest.databasecli.ResultSetMock;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDatabase {
    @Test
    public void getEmployeesReturnsResultQuery() throws SQLException {
        String[] columnNames = new String[]{"name", "age", "weight"};
        String[][] data = new String[][]{
                new String[]{"Jack Top", "34", "78"},
                new String[]{"Milly Sanders", "19", "103"},
                new String[]{"Hugh Jarms", "27", "192"}
        };
        ResultSet resultSet = ResultSetMock.create(columnNames, data);

        Connection connection = mock(Connection.class);
        ConnectionSource connectionSource = mock(ConnectionSource.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        when(connectionSource.getConnection("url", "user", "pass")).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.getResultSet()).thenReturn(resultSet);

        Database db = Database.from("url", "user", "pass", connectionSource);
        db.connect();
        QueryResult queryResult = db.getEmployees("department", "payType", "educationLevel");

        assertEquals(new HashSet<>(Arrays.asList("name", "age", "weight")), queryResult.getColumnNames());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullStringsNowAllowed() throws SQLException {
        Database database = Database.from("url", "usermame", "password", new DriverManagerConnectionSource());
        database.getEmployees("department id", "pay type", null);
    }
}
