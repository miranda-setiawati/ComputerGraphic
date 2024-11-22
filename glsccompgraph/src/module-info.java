module glsccompgraph {
	requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens glsccompgraph to javafx.fxml;
    exports glsccompgraph;
}