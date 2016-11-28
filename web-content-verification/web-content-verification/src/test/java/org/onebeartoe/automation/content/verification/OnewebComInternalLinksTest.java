
package org.onebeartoe.automation.content.verification;

import java.util.List;
import org.onebeartoe.development.tools.web.content.verification.WebContentService;
import org.onebeartoe.development.tools.web.content.verification.crawlers.OnewebComStatusCrawler;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 * 
 * See this guide for basics behind this tests:
 * 
 *      http://learn-automation.com/data-driven-framework-in-selenium-webdriver/
 */
public class OnewebComInternalLinksTest 
{
    protected WebContentService webContentService;
    
    public OnewebComInternalLinksTest() 
    {
        webContentService = new WebContentService();
    }   
        
    @DataProvider(name="oneweb.com")
    public Object[][] testDataFeed() throws Exception
    {        
        Object [][] data = webContentService.loadBadLinks(OnewebComStatusCrawler.class, OnewebComStatusCrawler.rootUrl);
        
        return data;
    }
    
    @Test(dataProvider="oneweb.com", groups = {"internal-links"})
    public void testFireFox(String url, int statusCode, List<String> parentUrls)
    {
        assert(statusCode == 200);
    }
}
