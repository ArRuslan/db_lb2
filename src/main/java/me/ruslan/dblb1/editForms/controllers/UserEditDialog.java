package me.ruslan.dblb1.editForms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.ruslan.dblb1.db.ModelsUpdateController;
import me.ruslan.dblb1.models.User;

import java.sql.SQLException;
import java.util.Objects;

public class UserEditDialog {
    @FXML
    public TextField firstNameText;
    @FXML
    public TextField phoneText;
    @FXML
    public TextField passwordText;
    @FXML
    public TextField emailText;
    @FXML
    public TextField lastNameText;

    private User user;

    public void setUser(User user) {
        this.user = user;
        firstNameText.setText(user.getFirstName());
        lastNameText.setText(user.getLastName());
        emailText.setText(user.getEmail());
        passwordText.setText(user.getPassword());
        phoneText.setText(user.getPhoneNumber()+"");
    }

    public void close() {
        ((Stage)firstNameText.getScene().getWindow()).close();
    }

    public void save() {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String email = emailText.getText();
        String password = passwordText.getText();
        long phoneNumber;

        try {
            phoneNumber = Long.parseLong(phoneText.getText().replace(" ", "").replace("-", ""));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid phone number");
            alert.show();
            return;
        }

        if(Objects.equals(firstName, user.getFirstName()) &&
                lastName.equals(user.getLastName()) &&
                email.equals(user.getEmail()) &&
                password.equals(user.getPassword()) &&
                phoneNumber == user.getPhoneNumber()) {
            close();
            return;
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);

        try {
            ModelsUpdateController.updateUser(user);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while updating user object");
            alert.setContentText(e.getMessage());
            alert.show();
            return;
        }

        close();
    }
}
