
package org.onebeartoe.develoment.tools.jar.diff;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.onebeartoe.network.mail.AppletService;
import org.onebeartoe.network.mail.CommandLineInterfaceApplet;
import org.onebeartoe.network.mail.RunProfile;

/**
 *
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class JarDiffette extends CommandLineInterfaceApplet
{
    @Override
    public Options buildOptions()
    {
        Option jar1 = Option.builder()
                        .required()
                        .longOpt("jar1")
                        .build();
        
        Option outfile = Option.builder()
                        .required(false)
                        .longOpt("outfile")
                        .hasArg(true)
                        .build();
        
        Option name = Option.builder()
                        .hasArg(true)
                        .required(false)
                        .longOpt("nameA")
                        .argName("nameB")
                        .build();
        
        Options options = new Options();
//        options.addOption(jar1);
//        options.addOption(jar2);
        options.addOption(outfile);

        return options;        
    }
    
    @Override
    protected AppletService getService()
    {
        return new JarDiffService();
    }
    
    public static void main(String [] args) throws ParseException, IOException, Exception
    {
        CommandLineInterfaceApplet app = new JarDiffette();
        app.execute(args);
    }
    
    @Override
    protected RunProfile parseRunProfile(final String[] args, Options options) throws ParseException
    {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        
        List<String> remainingArgs = cmd.getArgList();
        
        System.out.println("remaining args");
        remainingArgs.forEach(a ->
        {
            System.out.println(a);}
        );
        
        if( remainingArgs.size() < 2)
        {
            String message = "Please provide values for two JAR paths.";
                    
            throw new ParseException(message);
        }
        
        String s1 = remainingArgs.get(0);
        File f1 = new File(s1);
        if( !f1.exists() || f1.isDirectory() )
        {
            String message = "Please ensure the first path is a file and exists";
            
            throw new ParseException(message);
        }
        
        String s2 = remainingArgs.get(1);
        File f2 = new File(s2);
        if( !f2.exists() || f1.isDirectory() )
        {
            String message = "Please ensure the second path is a file and exists";
            
            throw new ParseException(message);
        }
                
        JarDiffCliRunProfile runProfile = new JarDiffCliRunProfile();
        
        runProfile.setJarPath1(s1);
        runProfile.setJarPath2(s2);
        
        return runProfile;
    }
    
    public class JarDiffCliRunProfile extends RunProfile
    {
        String jarPath1;
        
        String jarPath2;

        public String getJarPath1() {
            return jarPath1;
        }

        public void setJarPath1(String jarPath1) {
            this.jarPath1 = jarPath1;
        }

        public String getJarPath2() {
            return jarPath2;
        }

        public void setJarPath2(String jarPath2) {
            this.jarPath2 = jarPath2;
        }
        
        
    }
}
