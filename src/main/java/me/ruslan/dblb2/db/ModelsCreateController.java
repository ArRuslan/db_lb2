package me.ruslan.dblb2.db;

import me.ruslan.dblb2.models.Category;
import me.ruslan.dblb2.models.Product;
import me.ruslan.dblb2.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModelsCreateController {
    public static void createUser(User user) throws SQLException {
        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement("INSERT INTO users(first_name, last_name, email, password, phone_number) VALUES (?, ?, ?, ?, ?)");
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setLong(5, user.getPhoneNumber());
        statement.execute();
        statement.close();
        conn.close();
    }

    public static void createCategory(Category category) throws SQLException {
        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement("INSERT INTO categories(name) VALUES (?)");
        statement.setString(1, category.getName());
        statement.execute();
        statement.close();
        conn.close();
    }

    public static void createProduct(Product product) throws SQLException {
        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement("INSERT INTO products(category_id, name, description, image_url, price, quantity) VALUES(?, ?, ?, ?, ?, ?)");
        if(product.getCategoryId() <= 0)
            statement.setNull(1, java.sql.Types.INTEGER);
        else
            statement.setInt(1, product.getCategoryId());

        statement.setString(2, product.getName());
        statement.setString(3, product.getDescription());
        statement.setString(4, product.getImageUrl());
        statement.setInt(5, product.getPrice());
        statement.setInt(6, product.getQuantity());
        statement.execute();
        statement.close();
        conn.close();
    }
}
