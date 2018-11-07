
package org.onebeartoe.file.types;

import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class FileTypesSpecification
{
   private FileTypes implementation;
   
   public FileTypesSpecification()
   {
       implementation = new FileTypes();
   }
   
   @Test(groups = {"unit"})
   public void execute() throws Exception
   {
       String [] args = {};
       
       implementation.execute(args);
   }
}
