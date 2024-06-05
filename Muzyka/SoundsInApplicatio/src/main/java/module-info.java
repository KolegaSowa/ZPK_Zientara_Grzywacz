module org.example.soundsinapplication {
    requires javafx.media;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.example.soundscomponent;

    opens org.example.soundsinapplication to javafx.fxml;
    exports org.example.soundsinapplication;
}