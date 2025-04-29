
import java.io.File;
import java.io.FileFilter;
import java.util.List;
import static java.util.Locale.filter;

public class GitStatus
{
    public static void main(String[] args)
    {
        System.out.println("cd /home/roberto/Versioning/owner/github");
        
        var pwd = new File(".");
        
        System.out.println("echo pwd: " + pwd);
        
        File[] fileArray = pwd.listFiles( (file) -> file.isDirectory() );
        
        List<File> directories = List.of(fileArray);

        for(File directory : directories)
        {
            System.out.println("cd " + directory.getName());
            
            System.out.println("echo " + "pwd: " + directory.getAbsolutePath() );
            
            System.out.println("git status");
            
            System.out.println("cd ../");
            
            System.out.println("echo \n\n\n");
            System.out.println("echo \n\n\n");
        }
    }
}