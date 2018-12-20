
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
        // assume the link is bad
        boolean isBad = true;
        
        if(responseCode == 200)
        {
            isBad = false;
        }
        
        if(responseCode == 302)
        {
            isBad = false;
        }

        System.out.println("response code: " + responseCode);
        
        return isBad;
    }
}
