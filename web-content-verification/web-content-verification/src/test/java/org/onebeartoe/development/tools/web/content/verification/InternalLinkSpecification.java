
package org.onebeartoe.development.tools.web.content.verification;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class InternalLinkSpecification
{
    private InternalLink implementation;
    
    @BeforeTest
    public void setup()
    {
        implementation = new InternalLink();
    }
    
    @Test
    public void instanceMembers()
    {
        String url = "some.url.net";        
        implementation.setUrl(url);        
        assert(url.equals( implementation.getUrl() ) );
        
        int statusCode = 200;
        implementation.setStatusCode(statusCode);
        assert( statusCode == implementation.getStatusCode() );
        
        String description = "This is a great description";
        implementation.setStatusCodeDescription(description);
        assert( description.equals(implementation.getStatusCodeDescription() ));
    }
}
