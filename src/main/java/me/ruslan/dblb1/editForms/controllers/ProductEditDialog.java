package me.ruslan.dblb1.editForms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.ruslan.dblb1.db.ModelsUpdateController;
import me.ruslan.dblb1.models.Product;
import me.ruslan.dblb1.models.User;

import java.sql.SQLException;
import java.util.Objects;

public class ProductEditDialog {
    @FXML
    public TextField categoryIdText;
    @FXML
    public TextField nameText;
    @FXML
    public TextField descriptionText;
    @FXML
    public TextField imageUrlText;
    @FXML
    public TextField priceText;
    @FXML
    public TextField quantityText;

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
        categoryIdText.setText(product.getCategoryId()+"");
        nameText.setText(product.getName());
        descriptionText.setText(product.getDescription());
        imageUrlText.setText(product.getImageUrl());
        priceText.setText(product.getPrice()+"");
        quantityText.setText(product.getQuantity()+"");
    }

    private void numberError(String fieldName) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid "+fieldName);
        alert.show();
    }

    private int parseInt(String str, String fieldName) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            numberError(fieldName);
            return -1;
        }
    }

    public void close() {
        ((Stage)categoryIdText.getScene().getWindow()).close();
    }

    public void save() {
        int category_id = parseInt(categoryIdText.getText(), "category id");
        String name = nameText.getText();
        String description = descriptionText.getText();
        String imageUrl = imageUrlText.getText();
        int price = parseInt(priceText.getText(), "price");
        int quantity = parseInt(quantityText.getText(), "quantity");

        if(category_id == -1 || price == -1 || quantity == -1) return;

        boolean sameIU = (imageUrl != null && product.getImageUrl() != null) ? imageUrl.equals(product.getImageUrl()) : (imageUrl == null && product.getImageUrl() == null);
        if(Objects.equals(name, product.getName()) &&
                description.equals(product.getDescription()) && sameIU &&
                category_id == product.getCategoryId() &&
                price == product.getPrice() &&
                quantity == product.getQuantity()) {
            close();
            return;
        }

        product.setCategoryId(category_id);
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(imageUrl);
        product.setPrice(price);
        product.setQuantity(quantity);

        try {
            ModelsUpdateController.updateProduct(product);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while updating product object");
            alert.setContentText(e.getMessage());
            alert.show();
            return;
        }

        close();
    }
}
