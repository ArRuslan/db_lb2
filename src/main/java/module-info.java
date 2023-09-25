module me.ruslan.dblb1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens me.ruslan.dblb1 to javafx.fxml;
    opens me.ruslan.dblb1.editForms.controllers to javafx.fxml;
    exports me.ruslan.dblb1;
}