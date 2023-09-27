package me.ruslan.dblb2.forms.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import me.ruslan.dblb2.db.CustomResult;
import me.ruslan.dblb2.db.CustomSqlExecutor;

import java.sql.SQLException;
import java.util.HashMap;

public class SqlFormController {
    @FXML
    public TextArea sqlText;
    @FXML
    public TableView<HashMap<String, String>> resultsTable;

    public void executeSql() {
        CustomResult result;
        try {
            result = CustomSqlExecutor.execute(sqlText.getText());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while executing query");
            alert.setContentText(e.getMessage());
            alert.show();
            return;
        }
        resultsTable.getItems().clear();
        resultsTable.getColumns().clear();

        if(result.affected <= -1) {
            for (String column : result.columns) {
                TableColumn<HashMap<String, String>, String> col = new TableColumn<>(column);
                col.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().get(column)));
                resultsTable.getColumns().add(col);
            }

            resultsTable.getItems().addAll(result.rows);
        } else {
            TableColumn<HashMap<String, String>, String> col = new TableColumn<>("Affected rows count");
            col.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().get("affected")));
            resultsTable.getColumns().add(col);

            resultsTable.getItems().add(new HashMap<>() {{ put("affected", result.affected+""); }});
        }
    }

    public void cleanTF() {
        sqlText.setText("");
    }

    public void exit() {
        ((Stage)sqlText.getScene().getWindow()).close();
    }
}
