module org.onebeartoe.development.tools.titanic.fx.view {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.onebeartoe.development.tools.titanic.fx.view to javafx.fxml;
    exports org.onebeartoe.development.tools.titanic.fx.view;
}
