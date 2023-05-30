module com.example.momuney {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    exports com.example.momuney;
    opens com.example.momuney to javafx.fxml;
    exports com.example.momuney.models;
    opens com.example.momuney.models to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.example.momuney.controllers;
    opens com.example.momuney.controllers to javafx.fxml, com.fasterxml.jackson.databind;
}