
package org.onebeartoe.development.tools.html.utility;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.onebeartoe.application.JavaPreferencesService;
import org.onebeartoe.application.PreferencesService;
import org.onebeartoe.application.ui.swing.SwingApplication;
import org.onebeartoe.application.ui.swing.TabbedPane;
import org.onebeartoe.development.tools.html.utility.panels.ImageTagPanel;
import org.onebeartoe.development.tools.html.utility.panels.JspSeederPanel;
import org.onebeartoe.development.tools.html.utility.panels.ListsPanel;

/**
 * 
 * @author Roberto Marquez
 * 
 */
public class HtmlUtility extends JFrame
{
    private transient PreferencesService preferencesService;
    
    private TabbedPane tabbedPane;
    
    private transient SwingApplication guiConfig;

    private final String applicationId;
    
    public HtmlUtility() 
    {
        super( "HtmlUtility by onebeartoe.com" );
        
        preferencesService = new JavaPreferencesService( getClass() );

        ImageTagPanel itp = new ImageTagPanel();
        IndexPanel ip = new IndexPanel();
        
        JspSeederPanel jsp = new JspSeederPanel(preferencesService);
        
        JPanel listsPanel = new ListsPanel();

        tabbedPane = new TabbedPane(jsp, itp, ip, listsPanel);

        String [] titles = {"JSP Seeder", "Image Tagging", "Directory Listing", "Lists"};
        tabbedPane.setTabTitles(titles);
        
        getContentPane().add( tabbedPane );
        
        applicationId = getClass().getName();
        
        guiConfig = loadDefaultGuiConfig();
        
        guiConfig.restoreWindowProperties(this);

        addWindowListener( new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent we)
            {
                try 
                {                    
                    guiConfig.setCurrentConfiguration(HtmlUtility.this);
                    guiConfig.setApplicationId(applicationId);
                    guiConfig.persistWindowProperties();
                } 
                catch (IOException ex) 
                {
                    ex.printStackTrace();
                }
            }
        });
        
        setVisible(true);
    }

    private SwingApplication loadDefaultGuiConfig()
    {
        SwingApplication app = new SwingApplication(applicationId) 
        {
            @Override
            public int defaultX() 
            {
                return 100;
            }
            
            @Override
            public int defaultY() 
            {
                return 200;
            }
            
            @Override
            public int defaultWidth() 
            {
                return 900;
            }
            
            @Override
            public int defaultHeight() 
            {
                return 400;
            }
        };
                
        return app;
    }
    
    public static void main(String [] args) 
    {
        final HtmlUtility app = new HtmlUtility();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
    }
}
