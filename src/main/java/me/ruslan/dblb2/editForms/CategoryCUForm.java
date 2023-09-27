package me.ruslan.dblb2.editForms;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import me.ruslan.dblb2.MainForm;
import me.ruslan.dblb2.editForms.controllers.CategoryCUDialog;
import me.ruslan.dblb2.models.Category;

import java.io.IOException;

public class CategoryCUForm {
    private final Category category;
    private final boolean create;

    public CategoryCUForm(Category category, boolean create) {
        this.category = category;
        this.create = create;
    }

    public CategoryCUForm(Category category) {
        this.category = category;
        this.create = false;
    }

    public void show(Window owner) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(MainForm.class.getResource("category_edit_dialog.fxml"));
            stage.setScene(new Scene(loader.load(), 290, 88));
            loader.<CategoryCUDialog>getController().setCategory(category);
            loader.<CategoryCUDialog>getController().setCreate(create);
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
