package me.ruslan.dblb2.db;

import me.ruslan.dblb2.models.Category;
import me.ruslan.dblb2.models.Product;
import me.ruslan.dblb2.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModelsDeleteController {
    private static void delObjectById(int id, String tableName, String pk_name) throws SQLException {
        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement(String.format("DELETE FROM %s WHERE %s = ?", tableName, pk_name));
        statement.setInt(1, id);
        statement.execute();
        statement.close();
        conn.close();
    }

    public static void delUser(User user) throws SQLException {
        delObjectById(user.getId(), "users", "user_id");
    }

    public static void delCategory(Category category) throws SQLException {
        delObjectById(category.getId(), "categories", "category_id");
    }

    public static void delProduct(Product product) throws SQLException {
        delObjectById(product.getId(), "products", "product_id");
    }
}
