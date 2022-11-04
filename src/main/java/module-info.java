module edu.season.javacoursedesign {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens edu.season.javacoursedesign to javafx.fxml;
    exports edu.season.javacoursedesign;
}