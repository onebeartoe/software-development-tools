
package org.onebeartoe.development.tools.html.utility.panels;

import java.awt.event.ActionEvent;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class IndexPanelSpecification
{
    IndexPanel implementation;
    
    @BeforeTest
    public void setup()
    {
        implementation = new IndexPanel();
    }
    
    @Test
    public void someMethod()
    {
        Object source = new Object();
        
        ActionEvent ae = new ActionEvent(source, 0, "no-command");
        
        implementation.actionPerformed(ae);
    }
}
