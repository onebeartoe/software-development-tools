
package org.onebeartoe.development.tools.web.content.verification;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the details of a bad link on a Website.
 * 
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class InternalLink
{
    private String url;
    
    private List<String> parentUrls;
    
    private int statusCode;
    
    private String statusCodeDescription;
    
    public InternalLink()
    {
        parentUrls = new ArrayList();
    }
    
    public List<String> getParentUrls() 
    {
        return parentUrls;
    }

    public String getUrl() {
        return url;
    }

    public void addParentUrl(String parentUrl) 
    {
        parentUrls.add(parentUrl);
    }

    public void setUrl(String url) 
    {
        this.url = url;
    }

    public int getStatusCode() 
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCodeDescription() {
        return statusCodeDescription;
    }

    public void setStatusCodeDescription(String statusCodeDescription) {
        this.statusCodeDescription = statusCodeDescription;
    }
    
    
}
