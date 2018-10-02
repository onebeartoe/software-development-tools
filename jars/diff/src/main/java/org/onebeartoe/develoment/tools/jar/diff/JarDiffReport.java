
package org.onebeartoe.develoment.tools.jar.diff;

import java.util.List;

/**
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class JarDiffReport 
{
    String infile1;
    String infile2;
    
    public List<String> uniqueToJar1;
    public List<String> uniqueToJar2;
    public List<String> commonToBoth;
    
    public JarDiffReport(String infile1, String infile2)
    {
        this.infile1 = infile1;
        this.infile2 = infile2;
    }
}
