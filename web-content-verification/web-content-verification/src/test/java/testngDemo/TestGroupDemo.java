
package testngDemo;

import org.testng.annotations.Test;

public class TestGroupDemo 
{

    @Test(groups = {"Smoke"})
    public void login() {

        System.out.println("Login done");
        System.out.println("Smoke Scenario passed");
    }

    @Test(groups = {"Regression"})
    public void register() {
        System.out.println("Registration done");
    }

}
