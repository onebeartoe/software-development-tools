
package org.onebeartoe.development.tools.jar.signer;

import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class JarSignerCommandServiceSpecification
{
   private JarSignerCommandService implementation;
   
   public JarSignerCommandServiceSpecification()
   {
       implementation = new JarSignerCommandService();
   }
   
   @Test(groups = {"unit"})
   public void serviceRequest() throws Exception
   {
       JarSignerRunProfile runProfile = new JarSignerRunProfile();
       runProfile.setJarsPath("target/");

       
       
       implementation.serviceRequest(runProfile);
   }
   
}
