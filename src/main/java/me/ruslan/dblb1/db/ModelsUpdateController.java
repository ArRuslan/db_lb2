package me.ruslan.dblb1.db;

import me.ruslan.dblb1.models.Category;
import me.ruslan.dblb1.models.Product;
import me.ruslan.dblb1.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModelsUpdateController {
    public static void updateUser(User user) throws SQLException {
        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement("UPDATE users SET first_name=?, last_name=?, email=?, password=?, phone_number=? WHERE id = ?");
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setLong(5, user.getPhoneNumber());
        statement.setInt(6, user.getId());
        statement.execute();
        statement.close();
        conn.close();
    }

    public static void updateCategory(Category category) throws SQLException {
        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement("UPDATE categories SET name=? WHERE id = ?");
        statement.setString(1, category.getName());
        statement.setInt(2, category.getId());
        statement.execute();
        statement.close();
        conn.close();
    }

    public static void updateProduct(Product product) throws SQLException {
        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement("UPDATE products SET category_id=?, name=?, description=?, image_url=?, price=?, quantity=? WHERE id = ?");
        statement.setInt(1, product.getCategoryId());
        statement.setString(2, product.getName());
        statement.setString(3, product.getDescription());
        statement.setString(4, product.getImageUrl());
        statement.setInt(5, product.getPrice());
        statement.setInt(6, product.getQuantity());
        statement.setInt(7, product.getId());
        statement.execute();
        statement.close();
        conn.close();
    }
}
