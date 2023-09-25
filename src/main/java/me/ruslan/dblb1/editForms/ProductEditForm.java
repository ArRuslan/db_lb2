package me.ruslan.dblb1.editForms;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import me.ruslan.dblb1.MainForm;
import me.ruslan.dblb1.editForms.controllers.ProductEditDialog;
import me.ruslan.dblb1.editForms.controllers.UserEditDialog;
import me.ruslan.dblb1.models.Product;
import me.ruslan.dblb1.models.User;

import java.io.IOException;

public class ProductEditForm {
    private Product product;

    public ProductEditForm(Product product) {
        this.product = product;
    }

    public void show(Window owner) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(MainForm.class.getResource("product_edit_dialog.fxml"));
            stage.setScene(new Scene(loader.load(), 290, 250));
            loader.<ProductEditDialog>getController().setProduct(product);
            stage.setResizable(false);
            stage.setTitle("Edit Product");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
