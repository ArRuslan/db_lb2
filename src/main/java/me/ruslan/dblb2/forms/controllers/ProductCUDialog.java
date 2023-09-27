package me.ruslan.dblb2.forms.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import me.ruslan.dblb2.db.ModelsCreateController;
import me.ruslan.dblb2.db.ModelsSelectController;
import me.ruslan.dblb2.db.ModelsUpdateController;
import me.ruslan.dblb2.models.Category;
import me.ruslan.dblb2.models.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class ProductCUDialog {
    @FXML
    public ChoiceBox<Category> categoryChoice;
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
    private boolean create = false;

    public void setProduct(Product product) {
        this.product = product;
        categoryChoice.setValue(new Category(0, "<NULL>"));
        categoryChoice.setDisable(true);
        nameText.setText(product.getName());
        descriptionText.setText(product.getDescription());
        imageUrlText.setText(product.getImageUrl());
        priceText.setText(product.getPrice()+"");
        quantityText.setText(product.getQuantity()+"");

        new Thread(() -> {
            Category cat = product.getCategory();
            ArrayList<Category> cats;
            try {
                cats = ModelsSelectController.getCategories();
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }

            cats.add(0, new Category(0, "<NULL>"));
            if(cat != null) Platform.runLater(() -> categoryChoice.setValue(cat));
            Platform.runLater(() -> categoryChoice.getItems().setAll(cats));
            Platform.runLater(() -> categoryChoice.setDisable(false));
        }).start();

        categoryChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(Category object) {
                return object.getName();
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        });
    }

    public void setCreate(boolean create) {
        this.create = create;
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
        ((Stage)categoryChoice.getScene().getWindow()).close();
    }

    public void save() {
        int category_id = categoryChoice.getValue().getId();
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
                quantity == product.getQuantity() && !create) {
            close();
            return;
        }

        if(name.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("These fields are required: name, description.");
            alert.show();
            return;
        }

        product.setCategoryId(category_id);
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(imageUrl);
        product.setPrice(price);
        product.setQuantity(quantity);

        try {
            if (create) {
                ModelsCreateController.createProduct(product);
            } else {
                ModelsUpdateController.updateProduct(product);
            }
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
