
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
    public void serviceRequest(RunProfile runProfile) throws AddressException, MessagingException
    {
        // Alas, we have to cast.
        SendetteRunProfile rp = (SendetteRunProfile) runProfile;
        
        String user = rp.smtpUser;        
        String pw = rp.smtpPassword;
        
        File attachement = rp.attachment;
        String to = rp.to;
        String subject = rp.subject;
        String body = rp.messageText;

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