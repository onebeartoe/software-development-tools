
package org.onebeartoe.network.mail;

import java.util.HashMap;
import java.util.Map;
import static org.onebeartoe.network.mail.Sendlet.MESSAGE_TEXT;

/**
 *
 * @author Roberto Marquez
 */
public class SendletArgumentsBuilder
{
    private Map<String, String> arguments = new HashMap();
    
    public SendletArgumentsBuilder withAttachment(String attachement)
    {
        String argKey = MESSAGE_TEXT + "";
        
        arguments.put(argKey, attachement);
        
        return this;
    }
    
    String [] build()
    {
        
        
        arguments.keySet()
            .forEach((t) ->
                {
                    arguments.get(t);
                    
                    
                }
            );            
        
        String [] args = {};
        
                    
        return args;
    }
            
}
