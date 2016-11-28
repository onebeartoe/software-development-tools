
package org.onebeartoe.development.tools.web.content.verification.crawlers;

/**
 *
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class OnewebOrgStatusCrawler extends StatusHandlerCrawler
{
    public static final String rootUrl = "http://onebeartoe.org/";
    
    @Override
    public String getRootUrl()
    {
        return rootUrl;
    }
}
