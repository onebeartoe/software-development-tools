
package org.onebeartoe.develoment.tools.jar.diff;

import java.io.IOException;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class JarDiffCli 
{
    public static void main(String [] args) throws ParseException, IOException
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
        
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        
        List<String> remainingArgs = cmd.getArgList();
        
        System.out.println("remaining args");
        remainingArgs.forEach(a ->
        {
            System.out.println(a);}
        );
        
        try
        {
            String s1 = remainingArgs.get(0);
            String s2 = remainingArgs.get(1);

            JarDiff diff = new JarDiff();
            JarDiffReport report = diff.diff(s1, s2);
            
            report.uniqueToJar1.stream()
                    .forEach( System.out::println );
        }
        catch(IndexOutOfBoundsException ioobe)
        {
            String message = "At leat two arguments are required: jarPath1 jarPath2";
            System.err.println(message);
            ioobe.printStackTrace();
        }
    }
}
