
package org.onebeartoe.development.tools.html.utility.tasks;

import java.io.File;
import org.onebeartoe.application.ui.swing.ScrollableTextArea;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class IndexTaskSpecification
{
    private IndexTask implementation;
    
    public IndexTaskSpecification()
    {
        File sourceDirectory = new File("target/");
        ScrollableTextArea statusPanel = new ScrollableTextArea("");
        
        implementation = new IndexTask(sourceDirectory, statusPanel);
    }
    
    @Test(groups = {"unit"})
    public void run()
    {
        implementation.run();
    }  
}
