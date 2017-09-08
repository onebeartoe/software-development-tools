/*

 */
package org.onebeartoe.development.tools.web.content.verification;

/**
 *
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class InternalLinksTest 
{
    /**
     * This method interprets 'bad' HTTP response codes.
     * 
     * Currently, bad response codes include anything that is not 200 or 302 (OK or Redirect).
     * 
     * @return 
     */
    public boolean isBadHttpResponse(int responseCode)
    {
        boolean isBad = responseCode != 200 
                            || responseCode != 302;
        
        return isBad;
    }
}
