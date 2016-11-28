
package org.onebeartoe.automation.content.verification;

import java.util.List;
import org.onebeartoe.development.tools.web.content.verification.crawlers.OnewebNetStatusCrawler;
import org.onebeartoe.development.tools.web.content.verification.WebContentService;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 * 
 * See this guide for basics behind this tests:
 * 
 *      http://learn-automation.com/data-driven-framework-in-selenium-webdriver/
 */
public class OnewebNetInternalLinksTest 
{
    protected WebContentService webContentService;
    
    public OnewebNetInternalLinksTest() 
    {
        webContentService = new WebContentService();
    }   
        
    @DataProvider(name="oneweb.net")
    public Object[][] testDataFeed() throws Exception
    {        
        Object [][] data = webContentService.loadBadLinks(OnewebNetStatusCrawler.class, OnewebNetStatusCrawler.rootUrl);
        
        return data;
    }
    
    @Test(dataProvider="oneweb.net", groups = {"internal-links"})
    public void testFireFox(String url, int statusCode, List<String> parentUrls)
    {
        assert(statusCode == 200);
    }
}
