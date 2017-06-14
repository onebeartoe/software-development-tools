
package org.onebeartoe.jar.diff;

import java.io.File;
import java.io.IOException;
import org.onebeartoe.develoment.tools.jar.diff.JarDiff;
import org.onebeartoe.develoment.tools.jar.diff.JarDiffReport;
//import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * These tests use the sample data found under src/test/resources/
 * 
 * a.zip contains a.text
 * 
 * b.zip contains a.text, b.text
 * 
 * c.zip contains a.text, b.text, c.text
 * 
 * d.zip contains d/a.text, d/b.text, d/c.text, d/d.text
 * 
 * e.zip contains e.text
 * 
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class JarDiffTest 
{    
    private static JarDiffDataSet dataset;
    
    public JarDiffTest() throws Exception
    {
        // the tests woulld NPE if this was not called from the contructor; static initilization was not working :(
        setUpClass();
    }
    
    @Test(groups = {"jar-diff-dataset"})
    public void sampleDataAB() throws IOException 
    {        
        JarDiff diff = new JarDiff();
        JarDiffReport report = diff.diff(dataset.jarA, dataset.jarB);
        
        int aSize = report.uniqueToJar1.size();
        assert(aSize == 0);
        
        int bSize = report.uniqueToJar2.size();
        // should be just b.text
        assert(bSize == 1);
        
        int commonSize = report.commonToBoth.size();
        // should be just a.text
        assert(commonSize == 1);
    }
    
    @Test(groups = {"jar-diff-dataset"})
    public void sampleDataBC() throws IOException
    {
        JarDiff diff = new JarDiff();
        JarDiffReport report = diff.diff(dataset.jarB, dataset.jarC);
        
        int bSize = report.uniqueToJar1.size();
        assert(bSize == 0);
        
        int cSize = report.uniqueToJar2.size();
        assert(cSize == 1);
        
        int commonSize = report.commonToBoth.size();
        assert(commonSize == 2);
    }

    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception 
    {
        File pwd = new File(".");
        System.out.println(" pwd: " + pwd);
        System.out.println("apwd: " + pwd.getAbsolutePath() );

        dataset = new JarDiffDataSet();
    }

    @org.testng.annotations.AfterClass
    public static void tearDownClass() throws Exception 
    {
    }

    @org.testng.annotations.BeforeMethod
    public void setUpMethod() throws Exception 
    {
    }

    @org.testng.annotations.AfterMethod
    public void tearDownMethod() throws Exception 
    {
    }
    
    private static class JarDiffDataSet
    {
        public String jarA;
        public String jarB;
        public String jarC;
        public String jarD;
        public String jarE;
        
        public JarDiffDataSet()
        {
            String path = "src/test/resources/";
            
            jarA = path + "a.zip";
            jarB = path + "b.zip";
            jarC = path + "c.zip";
            jarD = path + "d.zip";
            jarE = path + "e.zip";
        }
    }
}