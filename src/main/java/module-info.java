module me.ruslan.dblb2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens me.ruslan.dblb2 to javafx.fxml;
    opens me.ruslan.dblb2.editForms.controllers to javafx.fxml;
    exports me.ruslan.dblb2;
}