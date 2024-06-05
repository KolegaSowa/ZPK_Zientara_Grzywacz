module org.example.projektgra {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.example.soundscomponent;
    requires org.points.pointscountercomponent;

    exports org.example.projektgra;
    exports org.example.projektgra.game;
    exports org.example.projektgra.controllers;

    opens org.example.projektgra to javafx.controls, javafx.fxml;
    opens org.example.projektgra.controllers to javafx.controls, javafx.fxml;
    opens org.example.projektgra.game to javafx.fxml;
}