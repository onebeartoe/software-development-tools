
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
    public void serviceRequest(RunProfile runProfilee) throws AddressException, MessagingException
    {
        // Alas, we have to cast.
        SendetteRunProfile rp = (SendetteRunProfile) runProfilee;
        
        String user = rp.smtpUser;        
        String pw = rp.smtpPassword;
        
        File attachement = rp.attachment;
        String to = rp.to;
        String subject = rp.subject;
        String body = rp.messageText;
        
        AttSender sender = new AttSender(user, pw);        
        sender.sendMail(subject, body, to, attachement);
        
        System.out.println("The sendette service is great!");
    }
}
