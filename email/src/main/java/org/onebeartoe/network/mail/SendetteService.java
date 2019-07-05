
package org.onebeartoe.network.mail;

import org.onebeartoe.application.AppletService;
import org.onebeartoe.application.RunProfile;
import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * @author Roberto Marquez
 */
public class SendetteService extends AppletService
{
    @Override
    public void serviceRequest(RunProfile runProfile) throws AddressException, MessagingException
    {
        // Alas, we have to cast.
        SendetteRunProfile rp = (SendetteRunProfile) runProfile;
        
        String user = rp.getSmtpUser();        
        String pw = rp.getSmtpPassword();
        
        File attachement = rp.getAttachment();
        String to = rp.getTo();
        String subject = rp.getSubject();
        String body = rp.getMessageText();

        boolean checkServerIdentity = false;
        
        AttSender sender = new AttSender(user, pw);        
        
        sender.sendMail(subject, 
                        body, 
                        to, 
                        attachement,
                        checkServerIdentity);
        
        logger.info("The sendette service has sent the email.");
    }
}
