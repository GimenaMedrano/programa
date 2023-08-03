module com.example.programa {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens com.example.programa to javafx.fxml;
    exports com.example.programa;
}