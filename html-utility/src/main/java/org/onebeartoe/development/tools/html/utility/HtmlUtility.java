
package org.onebeartoe.development.tools.html.utility;

import javax.swing.JFrame;

import org.onebeartoe.application.ui.swing.TabbedPane;

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

        tabbedPane = new TabbedPane(itp, ip);

        String [] titles = {"Image Mode", "Index Mode"};
        tabbedPane.setTabTitles(titles);
        getContentPane().add( tabbedPane );
        setSize(600, 500);
        setLocation(215,100);
        setVisible(true);
    }

    public static void main(String [] args) 
    {
        HtmlUtility app = new HtmlUtility();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
    }
}
