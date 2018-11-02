
package org.onebeartoe.filesystem.populator;

import java.io.File;
import org.onebeartoe.application.RunProfile;

/**
 * @author Roberto Marquez
 */
public class FilesystemPopulatorRunProfile extends RunProfile 
{
    boolean onlyShowNonOpenscadDirs;

    boolean onlyShowOpenScadDirs;

    File populationFile;
}
