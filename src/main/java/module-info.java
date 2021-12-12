module com.example.kursovayagui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.kursovayaGui to javafx.fxml;
    exports com.example.kursovayaGui;
}