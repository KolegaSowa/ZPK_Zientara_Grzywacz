module org.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.example.soundscomponent;


    opens org.example.test to javafx.fxml;
    exports org.example.test;
}