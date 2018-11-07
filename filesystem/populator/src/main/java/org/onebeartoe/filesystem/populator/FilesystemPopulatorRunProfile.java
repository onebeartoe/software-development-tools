
package org.onebeartoe.filesystem.populator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.onebeartoe.application.RunProfile;

/**
 * @author Roberto Marquez
 */
public class FilesystemPopulatorRunProfile extends RunProfile 
{
    boolean showNonOpenscadDirs;

    boolean showOpenScadDirs;

    File populationFile;
    
    List<String> openScadDirectories;

    List<String> nonOpenScadDirectories;
    
    public FilesystemPopulatorRunProfile()
    {
        openScadDirectories = new ArrayList();
    
        nonOpenScadDirectories = new ArrayList();        
    }
}
