
package org.onebeartoe.development.tools.html.utility;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.onebeartoe.application.DesktopApplication;
import org.onebeartoe.application.ui.WindowProperties;
import org.onebeartoe.application.ui.swing.SwingApplication;
import org.onebeartoe.application.ui.swing.TabbedPane;
import org.onebeartoe.development.tools.html.utility.panels.JspSeederPanel;

/**
 * 
 * @author Roberto Marquez
 * 
 */
public class HtmlUtility extends JFrame
{
    private TabbedPane tabbedPane;
    
    private static WindowProperties wp;
    
    private SwingApplication guiConfig;

    private final String applicationId;
    
    public HtmlUtility() 
    {
        super( "HtmlUtility by onebeartoe.com" );

        ImageTagPanel itp = new ImageTagPanel();
        IndexPanel ip = new IndexPanel();
        
        JspSeederPanel jsp = new JspSeederPanel();

        tabbedPane = new TabbedPane(jsp, itp, ip);

        String [] titles = {"JSP Seeder", "Image Tagging", "Directory Listing"};
        tabbedPane.setTabTitles(titles);
        
        getContentPane().add( tabbedPane );
        
        applicationId = getClass().getName();
        
        guiConfig = new SwingApplication();
        
        restoreGui();

        addWindowListener( new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent we)
            {
                try 
                {                    
                    wp = guiConfig.currentConfiguration(HtmlUtility.this);
                    wp.id = applicationId;
                    guiConfig.persistWindowProperties(wp);
                } 
                catch (IOException ex) 
                {
                    ex.printStackTrace();
                }
            }
        });
        
        setVisible(true);
    }

    public static void main(String [] args) 
    {
        final HtmlUtility app = new HtmlUtility();
//        app.;
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
    }
    
    public void restoreGui()
    {
        try 
        {
            wp = new WindowProperties();
            wp.id = applicationId;
            wp = guiConfig.loadWindowProperties(wp);
            
            setSize(wp.width, wp.height);
            setLocation(wp.locationX, wp.locationY);
        } 
        catch (IOException | ClassNotFoundException ex  ) 
        {
            Logger.getLogger(HtmlUtility.class.getName()).log(Level.SEVERE, null, ex);

            wp = new WindowProperties();
            wp.id = applicationId;
            
            // resort to default window property values
            setSize(800, 500);
            setLocation(215,100);
        }
    }
}