module org.example.aplikacjatesujacapunkty {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.points.pointscountercomponent;

    opens org.example.aplikacjatesujacapunkty to javafx.fxml;
    exports org.example.aplikacjatesujacapunkty;
}