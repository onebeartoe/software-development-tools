
package org.onebeartoe.development.tools.web.content.verification.crawlers;

/**
 *
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class OnewebNetStatusCrawler extends StatusHandlerCrawler
{
//    public static final String rootUrl = "http://www.onebeartoe.net/";
    
    @Override
    public String getDefaultRootUrl()
    {
        return "http://www.onebeartoe.net/";
//        return rootUrl;
    }
}
