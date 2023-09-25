package me.ruslan.dblb1.models;

import me.ruslan.dblb1.db.ModelsDeleteController;
import me.ruslan.dblb1.db.ModelsSelectController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product implements Model {
    private final int id;
    private int category_id;
    private String name;
    private String description;
    private String image_url;
    private int price;
    private int quantity;

    public Product(int id, int category_id, String name, String description, String image_url, int price, int quantity) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
        this.quantity = quantity;
    }

    public String toString() {
        return String.format(
                "Product(id=%s, category_id='%s', name='%s', description=%s, image_url='%s', price=%s, quantity=%s)",
                id, category_id, name, description, image_url, price, quantity
        );
    }

    public int getId() {
        return this.id;
    }

    public int getCategoryId() {
        return this.category_id;
    }

    public void setCategoryId(int value) {
        this.category_id = value;
    }

    public Category getCategory() {
        if(this.category_id == 0) return null;

        try {
            return ModelsSelectController.getCategory(this.category_id);
        } catch (SQLException e) {
            return null;
        }
    }

    public void setCategory(Category value) {
        this.category_id = value.getId();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getImageUrl() {
        return this.image_url;
    }

    public void setImageUrl(String value) {
        this.image_url = value;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int value) {
        this.price = value;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int value) {
        this.quantity = value;
    }

    public static Product from_result(ResultSet result) throws SQLException {
        return new Product(
                result.getInt("id"),
                result.getInt("category_id"),
                result.getString("name"),
                result.getString("description"),
                result.getString("image_url"),
                result.getInt("price"),
                result.getInt("quantity")
        );
    }

    public static Product try_from_result(ResultSet result) {
        try {
            return from_result(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete() throws SQLException {
        ModelsDeleteController.delProduct(this);
    }
}
