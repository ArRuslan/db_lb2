package me.ruslan.dblb2.editForms;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import me.ruslan.dblb2.MainForm;
import me.ruslan.dblb2.editForms.controllers.ProductCUDialog;
import me.ruslan.dblb2.models.Product;

import java.io.IOException;

public class ProductCUForm {
    private final Product product;
    private final boolean create;

    public ProductCUForm(Product product, boolean create) {
        this.product = product;
        this.create = create;
    }

    public ProductCUForm(Product product) {
        this.product = product;
        this.create = false;
    }

    public void show(Window owner) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(MainForm.class.getResource("product_edit_dialog.fxml"));
            stage.setScene(new Scene(loader.load(), 290, 250));
            loader.<ProductCUDialog>getController().setProduct(product);
            loader.<ProductCUDialog>getController().setCreate(create);
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
