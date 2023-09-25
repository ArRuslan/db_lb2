package me.ruslan.dblb1.db;

import me.ruslan.dblb1.models.Category;
import me.ruslan.dblb1.models.Product;
import me.ruslan.dblb1.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModelsDeleteController {
    private static void delObjectById(int id, String tableName) throws SQLException {
        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
        statement.setInt(1, id);
        statement.execute();
        statement.close();
        conn.close();
    }

    public static void delUser(User user) throws SQLException {
        delObjectById(user.getId(), "users");
    }

    public static void delCategory(Category category) throws SQLException {
        delObjectById(category.getId(), "categories");
    }

    public static void delProduct(Product product) throws SQLException {
        delObjectById(product.getId(), "products");
    }
}
