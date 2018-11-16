
package org.onebeartoe.network.mail;

import org.onebeartoe.application.CommandLineInterfaceApplet;
import org.onebeartoe.application.AppletService;
import org.onebeartoe.application.RunProfile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Roberto Marquez
 */
public class Sendette extends CommandLineInterfaceApplet
{
    private final String ATTACHMENT = "attachment";
    private final String MESSAGE_TEXT = "messageText";
    private final String SMTP_FORCE_PASSWORD = "forceSmtpPassword";
    private final String SMTP_USER = "smtpUser";
    private final String SUBJECT = "subject";
    private final String TO = "to";
    
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
        
        Option smtpPassword = Option.builder()
                                .hasArg()
                                .longOpt(SMTP_FORCE_PASSWORD)
                                .build();
        
        Option smtpUser = Option.builder()
                                .hasArg()
                                .longOpt(SMTP_USER)
                                .build();
        
        Option subject = Option.builder()
                                .hasArg()
                                .longOpt(SUBJECT)
                                .build();
        
        Options options = new Options();
        options.addOption(attachment);
        options.addOption(messageText);
        options.addOption(smtpPassword);
        options.addOption(smtpUser);
        options.addOption(to);
        options.addOption(subject);
        
        return options;
    }    
    
//    @Override
//    public CommandLineInterfaceApplet getApplet()
//    {
//        return new Sendette();
//    }
    
    @Override
    protected AppletService getService() 
    {
        return new SendetteService();
    }
    
    public static void main(String [] args) throws IOException, Exception
    {
        CommandLineInterfaceApplet app = new Sendette();
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
        
        String smtpUserKey = "SMTP_USER";
        String smtpUser = System.getenv(smtpUserKey);
        boolean blankUser = smtpUser == null || smtpUser.trim().isEmpty();
        if(blankUser && cl.hasOption(SMTP_USER))
        {
            smtpUser = cl.getOptionValue(SMTP_USER);
        }

        // The password is looked up as an environment variable.
        String key = "SMTP_PASSWORD";
        String pw = System.getProperty(key, "");
        System.out.println("pw: " + pw);
        
        pw = System.getenv(key);
        System.out.println("env pw: " + pw);

        boolean blankPw = pw == null || pw.trim().equals("");
        
        if(blankPw && cl.hasOption(SMTP_FORCE_PASSWORD))
        {
            runProfile.forceSmtpPassword = true;
            pw = cl.getOptionValue(SMTP_FORCE_PASSWORD);
        }
        
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
            runProfile.attachment = attachement;
        }
        
        // next are all the validation checks for required fields
        if(smtpUser == null || smtpUser.trim().isEmpty() )
        {
            throw new IllegalArgumentException("The smtp user name is not set");
        }
        
        if(pw == null || pw.trim().isEmpty() )
        {
            throw new IllegalArgumentException("The password is blank.  Try setting the " 
                                + key + " environment variable.");
        }        
                
        runProfile.messageText = messageText;
        runProfile.to = to;
        runProfile.subject = subject;
        runProfile.smtpUser = smtpUser;
        runProfile.smtpPassword = pw;

        
        List<String> remainingArgs = cl.getArgList();
        if(remainingArgs.size() > 0)
        {
            remainingArgs.forEach(System.out::println);
        }        
        
        return runProfile;
    }
    
    class SendetteRunProfile extends RunProfile
    {
        File attachment;
        String messageText;
        String subject;
        String to;
        boolean forceSmtpPassword;
        String smtpUser;
        String smtpPassword;
    }
}
