
package org.onebeartoe.network.mail;

import org.onebeartoe.application.CommandLineInterfaceApplet;
import org.onebeartoe.application.AppletService;
import org.onebeartoe.application.RunProfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Roberto Marquez
 */
public class Sendlet extends CommandLineInterfaceApplet
{
    private final String ATTACHMENT = "attachment";
    private final String MESSAGE_TEXT = "messageText";
    private final String SMTP_PROPERTIES_PATH = "smtpPropertiesPath";
    private final String SUBJECT = "subject";
    private final String TO = "to";
    
    // these are keys found in the SMTP properties file
    private final String SMTP_USER = "smtp.user";
    private final String SMTP_PASSWORD = "smtp.password";
    
    @Override
    public Options buildOptions()
    {
        Option attachment = Option.builder()
                                .hasArg()
                                .longOpt(ATTACHMENT)
                                .build();
        
        Option messageText = Option.builder()
                                    .hasArg()
                                    .longOpt(MESSAGE_TEXT)
                                    .required()
                                    .build();
        
        Option to = Option.builder()
                        .required()
                        .hasArg()
                        .longOpt(TO)
                        .desc("This is the email address of the recipient.")
                        .build();
        
        Option smtpPropertiesPath = Option.builder()
                                .hasArg()
                                .longOpt(SMTP_PROPERTIES_PATH)
                                .required(true)
                                .build();
        
        Option subject = Option.builder()
                                .hasArg()
                                .longOpt(SUBJECT)
                                .build();
        
        Options options = new Options();
        options.addOption(attachment);
        options.addOption(messageText);
        options.addOption(smtpPropertiesPath);
        options.addOption(to);
        options.addOption(subject);
        
        return options;
    }
    
    @Override
    protected AppletService getService() 
    {
        return new SendetteService();
    }
    
    public static void main(String [] args) throws IOException, Exception
    {
        CommandLineInterfaceApplet app = new Sendlet();
        app.execute(args);
    }

    @Override
    protected RunProfile parseRunProfile(final String[] args, Options options) throws ParseException
    {
        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(options, args);

        String to = cl.getOptionValue(TO);        
        String subject = cl.getOptionValue(SUBJECT, "Wonderful Subject");

        SendetteRunProfile runProfile = new SendetteRunProfile();
        
        String messageText = cl.getOptionValue(MESSAGE_TEXT);
        boolean blankMessageText = messageText == null || messageText.trim().isEmpty();
        if(blankMessageText)
        {
            messageText = "The message text is blank.";
        }
        
        if( cl.hasOption(ATTACHMENT) )
        {
            String path = cl.getOptionValue(ATTACHMENT);
            File attachement = new File(path);
            runProfile.setAttachment(attachement);
        }

        runProfile.setSmtpPropertiesPath( cl.getOptionValue(SMTP_PROPERTIES_PATH) );
        Properties props = new Properties();
        InputStream inStream;
        try
        {
            inStream = new FileInputStream(runProfile.getSmtpPropertiesPath() );
            props.load(inStream);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Sendlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String smtpUser = props.getProperty(SMTP_USER);
        String pw = props.getProperty(SMTP_PASSWORD);
        
        
        // next are all the validation checks for required fields
        if(smtpUser == null || smtpUser.trim().isEmpty() )
        {
            throw new IllegalArgumentException("The smtp user name is not set");
        }
        
        if(pw == null || pw.trim().isEmpty() )
        {
            throw new IllegalArgumentException("The password is blank.  Try setting the " 
                                + SMTP_PASSWORD + " variable.");
        }        
                
        runProfile.setMessageText(messageText);
        runProfile.setTo(to);
        runProfile.setSubject(subject);
        runProfile.setSmtpUser(smtpUser);
        runProfile.setSmtpPassword(pw);

        List<String> remainingArgs = cl.getArgList();

        if(remainingArgs.size() > 0)
        {
            remainingArgs.forEach(System.out::println);
        }        
        
        return runProfile;
    }
}
