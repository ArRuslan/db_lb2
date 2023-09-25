package me.ruslan.dblb1.models;

import me.ruslan.dblb1.db.ModelsDeleteController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Category implements Model {
    private final int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return String.format(
                "Category(id=%s, name='%s')",
                id, name
        );
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public static Category from_result(ResultSet result) throws SQLException {
        return new Category(
                result.getInt("id"),
                result.getString("name")
        );
    }

    public static Category try_from_result(ResultSet result) {
        try {
            return from_result(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete() throws SQLException {
        ModelsDeleteController.delCategory(this);
    }
}
