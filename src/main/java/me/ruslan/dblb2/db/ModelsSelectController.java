package me.ruslan.dblb2.db;

import me.ruslan.dblb2.models.Category;
import me.ruslan.dblb2.models.Model;
import me.ruslan.dblb2.models.Product;
import me.ruslan.dblb2.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.function.Function;

public class ModelsSelectController {
    private static Model getObjectById(int id, String tableName, String pk_name, Function<ResultSet, Model> constructor) throws SQLException {
        Model object = null;

        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement(String.format("SELECT * FROM %s WHERE %s = ?", tableName, pk_name));
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next())
            object = constructor.apply(resultSet);
        resultSet.close();
        statement.close();
        conn.close();

        return object;
    }

    private static <T> ArrayList<T> getObjects(String tableName, Function<ResultSet, T> constructor) throws SQLException {
        ArrayList<T> objects = new ArrayList<>();

        Connection conn = Conn.get();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s", tableName));
        while (resultSet.next())
            objects.add(constructor.apply(resultSet));
        resultSet.close();
        statement.close();
        conn.close();

        return objects;
    }

    private static <T> LimitedResult<T> getObjectsLimited(String tableName, Function<ResultSet, T> constructor, int limit, int offset) throws SQLException {
        ArrayList<T> objects = new ArrayList<>();
        int count = 0;

        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement(String.format("SELECT *, COUNT(*) OVER () as %s_count FROM %s LIMIT ? OFFSET ?", tableName, tableName));
        statement.setInt(1, limit);
        statement.setInt(2, offset);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            if (count == 0) count = resultSet.getInt(String.format("%s_count", tableName));
            objects.add(constructor.apply(resultSet));
        }
        resultSet.close();
        statement.close();
        conn.close();

        return new LimitedResult<>(objects, count);
    }


    public static User getUser(int id) throws SQLException {
        return (User) getObjectById(id, "users", "user_id", User::try_from_result);
    }

    public static ArrayList<User> getUsers() throws SQLException {
        return getObjects("users", User::try_from_result);
    }

    public static LimitedResult<User> getUsersLim(int limit, int offset) throws SQLException {
        return getObjectsLimited("users", User::try_from_result, limit, offset);
    }


    public static Category getCategory(int id) throws SQLException {
        return (Category) getObjectById(id, "categories", "category_id", Category::try_from_result);
    }

    public static ArrayList<Category> getCategories() throws SQLException {
        return getObjects("categories", Category::try_from_result);
    }

    public static LimitedResult<Category> getCategoriesLim(int limit, int offset) throws SQLException {
        return getObjectsLimited("categories", Category::try_from_result, limit, offset);
    }


    public static Product getProduct(int id) throws SQLException {
        return (Product) getObjectById(id, "products", "product_id", Product::try_from_result);
    }

    public static ArrayList<Product> getProducts() throws SQLException {
        return getObjects("products", Product::try_from_result);
    }

    public static LimitedResult<Product> getProductsLim(int limit, int offset) throws SQLException {
        return getObjectsLimited("products", Product::try_from_result, limit, offset);
    }


    public static ArrayList<Product> getProductsByCategory(Category category) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();

        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM products WHERE category_id=?");
        statement.setInt(1, category.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
            products.add(Product.try_from_result(resultSet));
        resultSet.close();
        statement.close();
        conn.close();

        return products;
    }
}
