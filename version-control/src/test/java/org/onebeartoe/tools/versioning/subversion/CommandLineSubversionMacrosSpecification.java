
package org.onebeartoe.tools.versioning.subversion;

import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class CommandLineSubversionMacrosSpecification
{
   private CommandLineSubversionMacros implementation;
   
   public CommandLineSubversionMacrosSpecification()
   {
       implementation = new CommandLineSubversionMacros();
   }
   
   @Test(groups = {"unit"})
   public void serviceRequest() throws Exception
   {
       String [] args = {"ploop"};
       
       implementation.serviceRequest(args);
   }
}
