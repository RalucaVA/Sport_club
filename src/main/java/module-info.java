module com.example.fxdemos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;


    opens com.example.fxdemos to javafx.fxml;
    exports com.example.fxdemos;
}