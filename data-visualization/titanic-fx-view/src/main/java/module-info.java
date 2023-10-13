module org.onebeartoe.development.tools.titanic.fx.view 
{
    requires java.sql;

    requires javafx.controls;
    requires javafx.fxml;
    
    requires org.onebeartoe.development.tools.titanic;
//    requires org.onebeartoe.development.tools.titanic.statistics;

    opens org.onebeartoe.development.tools.titanic.fx.view to javafx.fxml;
    exports org.onebeartoe.development.tools.titanic.fx.view;
}
