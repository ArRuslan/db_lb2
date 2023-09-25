package me.ruslan.dblb1.editForms;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import me.ruslan.dblb1.MainForm;
import me.ruslan.dblb1.editForms.controllers.UserCUDialog;
import me.ruslan.dblb1.models.Product;
import me.ruslan.dblb1.models.User;

import java.io.IOException;

public class UserCUForm {
    private User user;
    private boolean create;

    public UserCUForm(User user, boolean create) {
        this.user = user;
        this.create = create;
    }

    public UserCUForm(User user) {
        this.user = user;
        this.create = false;
    }

    public void show(Window owner) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(MainForm.class.getResource("user_edit_dialog.fxml"));
            stage.setScene(new Scene(loader.load(), 290, 224));
            loader.<UserCUDialog>getController().setUser(user);
            loader.<UserCUDialog>getController().setCreate(create);
            stage.setResizable(false);
            stage.setTitle("Edit User");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
