module mixmaven.ui {
    requires mixmaven.core;
    requires mixmaven.json;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    opens ui to javafx.graphics, javafx.fxml;
}
