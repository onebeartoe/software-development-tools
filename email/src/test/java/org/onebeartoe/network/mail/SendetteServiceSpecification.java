
package org.onebeartoe.network.mail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.onebeartoe.application.RunProfile;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class SendetteServiceSpecification
{
    private SendetteService implementation;
 
    @BeforeTest
    public void setup()
    {
        implementation = new SendetteService();
    }
    
    @Test(expectedExceptions = AddressException.class)
    public void serviceRequest_fail_BadRecipientsList() throws MessagingException
    {
        RunProfile rp = new SendetteRunProfile();
        
        implementation.serviceRequest(rp);
    }
}
