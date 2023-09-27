module me.ruslan.dblb2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens me.ruslan.dblb2 to javafx.fxml;
    exports me.ruslan.dblb2;
    opens me.ruslan.dblb2.forms.controllers to javafx.fxml;
    exports me.ruslan.dblb2.forms;
    opens me.ruslan.dblb2.forms to javafx.fxml;
    exports me.ruslan.dblb2.forms.controllers;
}