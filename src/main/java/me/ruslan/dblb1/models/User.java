package me.ruslan.dblb1.models;

import me.ruslan.dblb1.db.ModelsDeleteController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements Model {
    private final int id;
    private String first_name;
    private String last_name;
    private String password;
    private String email;
    private long phone_number;

    public User(int id, String first_name, String last_name, String password, String email, long phone_number) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String toString() {
        return String.format(
                "User(id=%s, first_name='%s', last_name='%s', password=..., email='%s', phone_number=%s)",
                id, first_name, last_name, email, phone_number
        );
    }

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.first_name;
    }

    public void setFirstName(String value) {
        this.first_name = value;
    }

    public String getLastName() {
        return this.last_name;
    }

    public void setLastName(String value) {
        this.last_name = value;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public long getPhoneNumber() {
        return this.phone_number;
    }

    public void setPhoneNumber(long value) {
        this.phone_number = value;
    }

    public static User from_result(ResultSet result) throws SQLException {
        return new User(
                result.getInt("id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("password"),
                result.getString("email"),
                result.getLong("phone_number")
        );
    }

    public static User try_from_result(ResultSet result) {
        try {
            return from_result(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete() throws SQLException {
        ModelsDeleteController.delUser(this);
    }
}
