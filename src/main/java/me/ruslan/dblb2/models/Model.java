package me.ruslan.dblb2.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Model {
    static Model from_result(ResultSet result) throws SQLException {return null;}
    static Model try_from_result(ResultSet result) {return null;}

    void delete() throws SQLException;
}
