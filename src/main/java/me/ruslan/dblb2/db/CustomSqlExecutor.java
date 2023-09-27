package me.ruslan.dblb2.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomSqlExecutor {
    public static CustomResult execute(String query) throws SQLException {
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<HashMap<String, String>> rows = new ArrayList<>();

        Connection conn = Conn.get();
        Statement statement = conn.createStatement();
        boolean isResultSet = statement.execute(query);
        if(!isResultSet) {
            int affected = statement.getUpdateCount();
            statement.close();
            conn.close();
            return new CustomResult(columns, rows, affected);
        }

        ResultSet resultSet = statement.getResultSet();

        int col_count = resultSet.getMetaData().getColumnCount();
        for(int col = 1; col <= col_count; col++)
            columns.add(resultSet.getMetaData().getColumnName(col));
        while (resultSet.next()) {
            HashMap<String, String> row = new HashMap<>();
            for(int col = 1; col <= col_count; col++)
                row.put(columns.get(col-1), resultSet.getString(col));

            rows.add(row);
        }
        resultSet.close();
        statement.close();
        conn.close();

        return new CustomResult(columns, rows, -1);
    }
}
