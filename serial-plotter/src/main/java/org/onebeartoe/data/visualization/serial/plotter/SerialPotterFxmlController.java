package org.onebeartoe.data.visualization.serial.plotter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SerialPotterFxmlController implements Initializable 
{
    
    @FXML
    private Label label;
    
    @FXML
    private AnchorPane anchorPane;

    public AnchorPane getAnchorPane()
    {
        return anchorPane;
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        ;
    }    
}
