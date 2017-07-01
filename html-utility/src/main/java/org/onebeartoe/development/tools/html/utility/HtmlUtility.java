
package org.onebeartoe.development.tools.html.utility;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.onebeartoe.application.DesktopApplication;
import org.onebeartoe.application.ui.WindowProperties;
import org.onebeartoe.application.ui.swing.TabbedPane;
import org.onebeartoe.development.tools.html.utility.panels.JspSeederPanel;

/**
 * 
 * @author Roberto Marquez
 * 
 */
public class HtmlUtility extends JFrame implements DesktopApplication
{
    private TabbedPane tabbedPane;
    
    private static WindowProperties wp;

    public HtmlUtility() 
    {
        super( "HtmlUtility by onebeartoe.com" );

        ImageTagPanel itp = new ImageTagPanel();
        IndexPanel ip = new IndexPanel();
        
        JspSeederPanel jsp = new JspSeederPanel();

        tabbedPane = new TabbedPane(itp, ip, jsp);

        String [] titles = {"Image Tagging", "Directory Listing", "JSP Seeder"};
        tabbedPane.setTabTitles(titles);
        
        getContentPane().add( tabbedPane );
        
        String applicationName = getClass().getName();
        try 
        {
            wp = restoreWindowProperties(applicationName);
            
            setSize(wp.width, wp.height);
            setLocation(wp.locationX, wp.locationY);
        } 
        catch (IOException | ClassNotFoundException ex  ) 
        {
            Logger.getLogger(HtmlUtility.class.getName()).log(Level.SEVERE, null, ex);

            wp = new WindowProperties();
            wp.id = applicationName;
            
            // resort to default window property values
            setSize(800, 500);
            setLocation(215,100);
        }
        
        String appName = getClass().getSimpleName();
        wp.applicationName = appName;
        
        setVisible(true);
    }

    public static void main(String [] args) 
    {
        final HtmlUtility app = new HtmlUtility();
        app.addWindowListener( new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent we)
            {
                try 
                {
                    wp.locationX = (int) app.getLocation().getX();
                    wp.locationY = (int) app.getLocation().getY();
                    
                    wp.width = app.getWidth();
                    wp.height = app.getHeight();
                    
                    app.persistWindowProperties(wp);
                } 
                catch (IOException ex) 
                {
                    ex.printStackTrace();
                }
            }
        });
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
    }
}