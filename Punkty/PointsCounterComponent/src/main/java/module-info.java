module org.points.pointscountercomponent {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.points.pointscountercomponent to javafx.fxml;
    exports org.points.pointscountercomponent;
}