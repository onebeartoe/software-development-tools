
package org.onebeartoe.network.mail;

import java.io.File;
import org.onebeartoe.application.RunProfile;
    
class SendetteRunProfile extends RunProfile
{
//    private 
            String smtpPropertiesPath;

    File attachment;
    String messageText;
    String subject;
    String to;
    String smtpUser;
    String smtpPassword;
}