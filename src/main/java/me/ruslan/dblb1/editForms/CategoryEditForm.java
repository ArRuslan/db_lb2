package me.ruslan.dblb1.editForms;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import me.ruslan.dblb1.MainForm;
import me.ruslan.dblb1.editForms.controllers.CategoryEditDialog;
import me.ruslan.dblb1.editForms.controllers.UserEditDialog;
import me.ruslan.dblb1.models.Category;
import me.ruslan.dblb1.models.User;

import java.io.IOException;

public class CategoryEditForm {
    private Category category;

    public CategoryEditForm(Category category) {
        this.category = category;
    }

    public void show(Window owner) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(MainForm.class.getResource("category_edit_dialog.fxml"));
            stage.setScene(new Scene(loader.load(), 290, 88));
            loader.<CategoryEditDialog>getController().setCategory(category);
            stage.setResizable(false);
            stage.setTitle("Edit Category");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
