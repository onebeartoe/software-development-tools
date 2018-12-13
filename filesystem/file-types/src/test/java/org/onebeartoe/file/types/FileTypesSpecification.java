
package org.onebeartoe.file.types;

import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class FileTypesSpecification
{
   private FileTypesApplet implementation;
   
   public FileTypesSpecification()
   {
       implementation = new FileTypesApplet();
   }
   
   @Test(groups = {"unit"})
   public void execute() throws Exception
   {
       String [] args = {};
       
       implementation.execute(args);
   }
}
