package me.ruslan.dblb2.editForms.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.ruslan.dblb2.db.ModelsCreateController;
import me.ruslan.dblb2.db.ModelsUpdateController;
import me.ruslan.dblb2.models.User;

import java.sql.SQLException;
import java.util.Objects;

public class UserCUDialog {
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
    private boolean create = false;

    public void setUser(User user) {
        this.user = user;
        firstNameText.setText(user.getFirstName());
        lastNameText.setText(user.getLastName());
        emailText.setText(user.getEmail());
        passwordText.setText(user.getPassword());
        phoneText.setText(user.getPhoneNumber()+"");
    }

    public void setCreate(boolean create) {
        this.create = create;
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
                phoneNumber == user.getPhoneNumber() && !create) {
            close();
            return;
        }

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("These fields are required: first name, last name, email, password.");
            alert.show();
            return;
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);

        try {
            if (create) {
                ModelsCreateController.createUser(user);
            } else {
                ModelsUpdateController.updateUser(user);
            }
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
