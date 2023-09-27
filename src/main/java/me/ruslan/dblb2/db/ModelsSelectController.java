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

    private static ArrayList<Model> getObjects(String tableName, Function<ResultSet, Model> constructor) throws SQLException {
        ArrayList<Model> objects = new ArrayList<>();

        Connection conn = Conn.get();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
        while (resultSet.next())
            objects.add(constructor.apply(resultSet));
        resultSet.close();
        statement.close();
        conn.close();

        return objects;
    }

    public static User getUser(int id) throws SQLException {
        return (User) getObjectById(id, "users", "user_id", User::try_from_result);
    }

    public static ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        for(Model m : getObjects("users", User::try_from_result))
            users.add((User)m);

        return users;
    }

    public static Category getCategory(int id) throws SQLException {
        return (Category) getObjectById(id, "categories", "category_id", Category::try_from_result);
    }

    public static ArrayList<Category> getCategories() throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        for(Model m : getObjects("categories", Category::try_from_result))
            categories.add((Category)m);

        return categories;
    }

    public static Product getProduct(int id) throws SQLException {
        return (Product) getObjectById(id, "products", "product_id", Product::try_from_result);
    }

    public static ArrayList<Product> getProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        for(Model m : getObjects("products", Product::try_from_result))
            products.add((Product)m);

        return products;
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
