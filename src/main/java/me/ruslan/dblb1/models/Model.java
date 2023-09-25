package me.ruslan.dblb1.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Model {
    public static Model from_result(ResultSet result) throws SQLException {return null;}
    public static Model try_from_result(ResultSet result) {return null;}

    public void delete() throws SQLException;
}
