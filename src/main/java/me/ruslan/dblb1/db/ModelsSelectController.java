package me.ruslan.dblb1.db;

import me.ruslan.dblb1.models.Category;
import me.ruslan.dblb1.models.Model;
import me.ruslan.dblb1.models.Product;
import me.ruslan.dblb1.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.function.Function;

public class ModelsSelectController {
    private static Model getObjectById(int id, String tableName, Function<ResultSet, Model> constructor) throws SQLException {
        Model object = null;

        Connection conn = Conn.get();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
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
        return (User) getObjectById(id, "users", User::try_from_result);
    }

    public static ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        for(Model m : getObjects("users", User::try_from_result))
            users.add((User)m);

        return users;
    }

    public static Category getCategory(int id) throws SQLException {
        return (Category) getObjectById(id, "categories", Category::try_from_result);
    }

    public static ArrayList<Category> getCategories() throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        for(Model m : getObjects("categories", Category::try_from_result))
            categories.add((Category)m);

        return categories;
    }

    public static Product getProduct(int id) throws SQLException {
        return (Product) getObjectById(id, "products", Product::try_from_result);
    }

    public static ArrayList<Product> getProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        for(Model m : getObjects("products", Product::try_from_result))
            products.add((Product)m);

        return products;
    }
}
