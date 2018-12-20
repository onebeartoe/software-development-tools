
package org.onebeartoe.file.types;

import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class FileTypesAppletSpecification
{
   private FileTypesApplet implementation;
   
   public FileTypesAppletSpecification()
   {
       implementation = new FileTypesApplet();
   }
   
   @Test(groups = {"unit"})
   public void execute() throws Exception
   {
       String [] args = {"target/"};
       
       implementation.execute(args);
   }
   
   @Test(groups = {"unit"})
   public void execute_noArgs() throws Exception
   {
       String [] args = {};
       
       implementation.execute(args);
   }
}
