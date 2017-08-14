
package org.onebeartoe.development.tools.keyboard.shortcuts;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Section;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * This application uses the gauge class from the Medusa 
 * project (https://github.com/HanSolo/Medusa/wiki/Gauge-Skins).
 * 
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class FXMLController implements Initializable 
{
    @FXML
    Gauge gauge;
            
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        gauge.setSkinType(Gauge.SkinType.SECTION);
        Section s1 = new Section(0, 20, "fart", Color.CORAL);
        List<Section> sections = new ArrayList();
        sections.add(s1);
        gauge.setSections(sections);
        gauge.prefHeight(50);
        gauge.prefWidth(50);
        gauge.setScaleX(0.5);
        gauge.setScaleY(0.5);
    }    
}
