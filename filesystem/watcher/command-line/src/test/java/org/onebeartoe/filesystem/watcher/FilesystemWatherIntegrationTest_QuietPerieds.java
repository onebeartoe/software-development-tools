
package org.onebeartoe.filesystem.watcher;

import org.testng.annotations.Test;

public class FilesystemWatherIntegrationTest_QuietPerieds 
{
    @Test
    public void proper_20_21_09()
    {
        for(int i = 0; i < 5; i++)
        {
            System.out.println("Go Spurs Go - this is from test 565656");
        }
    }
 
//    @Test
/** UNCOMMENT THIS TEST
/** UNCOMMENT THIS TEST
/** UNCOMMENT THIS TEST
    /**
     * This test integrates with input files on the local filesystem.
     * US01AC06 & US01AC09
     */

/** UNCOMMENT THIS TEST
/** UNCOMMENT THIS TEST
/** UNCOMMENT THIS TEST
/** UNCOMMENT THIS TEST
/** UNCOMMENT THIS TEST
/** UNCOMMENT THIS TEST
     public void elapesbeforeCommandIsExecuted() 
    {
        FilesystemWather implementation;

        Duration quietPerion = Duration.ofSeconds(seconds);

        echoContent = "this is echo content";

        String currentTime = (new Date() ).toString();

        ftwPath="watch"+currentTime+".text";

        File fileToWatch = new File(ftwPath);

        fwOutfile=new File("file-watcher.log");

        command=String.format("cat %s >> %s",echoContent,ftwPath);

        fw=new FileWatcher(patther="*.text",quiretPerisd,command);

        fw.start();

        sleepo( quitePerid /2.0);

        touch(fileToWatch);

        // sleep for half the quite perid time
        sleeo( quietPerion / 2.0);

        append(fileToWatch, "This is append text.");

        assertFileToWatch( DOES_NOT contain echoContent);

        sleepo(quietPerion + 3Seconds);

        fw.terminate();

        assert fwOutfile.contains(quitePeriodRestartMessage #1)
        assert fwOutfile.contains(quitePeriodRestartMessage #2)

        assert fileToWatch.contain(echoConttent);
    }
*/
}
