
package org.onebeartoe.development.tools.html.utility;

import javax.swing.JFrame;

import org.onebeartoe.application.ui.swing.TabbedPane;
import org.onebeartoe.development.tools.html.utility.panels.JspSeederPanel;

/**
 * 
 * @author Roberto Marquez
 * 
 */
public class HtmlUtility extends JFrame
{
    TabbedPane tabbedPane;

    public HtmlUtility() 
    {
        super( "HtmlUtility by onebeartoe.com" );

        ImageTagPanel itp = new ImageTagPanel();
        IndexPanel ip = new IndexPanel();
        
        JspSeederPanel jsp = new JspSeederPanel();

        tabbedPane = new TabbedPane(itp, ip, jsp);

        String [] titles = {"Image Tagging", "Directory Listing", "JEE Web Index"};
        tabbedPane.setTabTitles(titles);
        
        getContentPane().add( tabbedPane );
        setSize(800, 500);
        setLocation(215,100);
        setVisible(true);
    }

    public static void main(String [] args) 
    {
        HtmlUtility app = new HtmlUtility();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
    }
}
