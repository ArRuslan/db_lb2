package me.ruslan.dblb1.editForms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.ruslan.dblb1.db.ModelsUpdateController;
import me.ruslan.dblb1.models.Category;
import me.ruslan.dblb1.models.User;

import java.sql.SQLException;
import java.util.Objects;

public class CategoryEditDialog {
    @FXML
    public TextField nameText;

    private Category category;

    public void setCategory(Category category) {
        this.category = category;
        nameText.setText(category.getName());
    }

    public void close() {
        ((Stage)nameText.getScene().getWindow()).close();
    }

    public void save() {
        String name = nameText.getText();

        if(Objects.equals(name, category.getName())) {
            close();
            return;
        }

        category.setName(name);

        try {
            ModelsUpdateController.updateCategory(category);
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
