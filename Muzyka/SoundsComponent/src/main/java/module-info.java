module org.example.soundscomponent {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens org.example.soundscomponent to javafx.fxml;
    exports org.example.soundscomponent;
}