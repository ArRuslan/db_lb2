package me.ruslan.dblb2.forms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.ruslan.dblb2.db.ModelsCreateController;
import me.ruslan.dblb2.db.ModelsUpdateController;
import me.ruslan.dblb2.models.Category;

import java.sql.SQLException;
import java.util.Objects;

public class CategoryCUDialog {
    @FXML
    public TextField nameText;

    private Category category;
    private boolean create = false;

    public void setCategory(Category category) {
        this.category = category;
        nameText.setText(category.getName());
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public void close() {
        ((Stage)nameText.getScene().getWindow()).close();
    }

    public void save() {
        String name = nameText.getText();

        if(Objects.equals(name, category.getName()) && !create) {
            close();
            return;
        }

        if(name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("These fields are required: name.");
            alert.show();
            return;
        }

        category.setName(name);

        try {
            if (create) {
                ModelsCreateController.createCategory(category);
            } else {
                ModelsUpdateController.updateCategory(category);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while updating category object");
            alert.setContentText(e.getMessage());
            alert.show();
            return;
        }
        close();
    }
}
