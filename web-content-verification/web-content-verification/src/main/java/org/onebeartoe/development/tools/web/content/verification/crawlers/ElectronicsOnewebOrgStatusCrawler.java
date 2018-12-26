
package org.onebeartoe.development.tools.web.content.verification.crawlers;

/**
 *
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class ElectronicsOnewebOrgStatusCrawler extends StatusHandlerCrawler
{
//    public static final String rootUrl = "http://electronics.onebeartoe.org/";
    
    /**
     *
     * @return
     */
    @Override
    public String getDefaultRootUrl()
    {
        return "http://electronics.onebeartoe.org/";
//        return rootUrl;
    }
}
