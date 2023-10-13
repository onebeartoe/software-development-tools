module org.onebeartoe.development.tools.titanic.fx.view 
{
    requires javafx.controls;
    requires javafx.fxml;
    
    requires org.onebeartoe.development.tools.titanic;

    opens org.onebeartoe.development.tools.titanic.fx.view to javafx.fxml;
    exports org.onebeartoe.development.tools.titanic.fx.view;
}
