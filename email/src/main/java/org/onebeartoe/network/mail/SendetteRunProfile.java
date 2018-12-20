
package org.onebeartoe.network.mail;

import java.io.File;
import org.onebeartoe.application.RunProfile;
    
class SendetteRunProfile extends RunProfile
{
    private String smtpPropertiesPath;

    private File attachment;
    
    private String messageText;
    
    private String subject;
    
    private String to = "";
    
    private String smtpUser;
    
    private String smtpPassword;

    public String getSmtpPropertiesPath()
    {
        return smtpPropertiesPath;
    }

    public void setSmtpPropertiesPath(String smtpPropertiesPath)
    {
        this.smtpPropertiesPath = smtpPropertiesPath;
    }

    public File getAttachment()
    {
        return attachment;
    }

    public void setAttachment(File attachment)
    {
        this.attachment = attachment;
    }

    public String getMessageText()
    {
        return messageText;
    }

    public void setMessageText(String messageText)
    {
        this.messageText = messageText;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public String getSmtpUser()
    {
        return smtpUser;
    }

    public void setSmtpUser(String smtpUser)
    {
        this.smtpUser = smtpUser;
    }

    public String getSmtpPassword()
    {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword)
    {
        this.smtpPassword = smtpPassword;
    }
    
    
}
