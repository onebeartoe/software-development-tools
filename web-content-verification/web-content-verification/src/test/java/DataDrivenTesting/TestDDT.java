
package DataDrivenTesting;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * http://learn-automation.com/data-driven-framework-in-selenium-webdriver/
 * 
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class TestDDT
{
    // this will take data from dataprovider which we created
    @Test(dataProvider="testdata", groups = {"Smoke"})
    public void testFireFox(String uname, String password)
    {
        assert(uname != null);
    }

    // this is DataProvider which actually feed data to our test cases here I have taken 2 D 
    // array with 2 rows and 2 column it means. It will run our test case two times because we 
    // have taken 2 rows. While first iteration this will pass username and password to test 
    // case and in second iteration perform the same for second rows
    @DataProvider(name="testdata")
    public Object[][] testDataFeed()
    {
        // Create object array with 2 rows and 2 column- first parameter is row and second is //column
        Object [][] facebookdata=new Object[2][2];



        // Enter data to row 0 column 0
        facebookdata[0][0]="Selenium1@gmail.com";



        // Enter data to row 0 column 1
        facebookdata[0][1]="Password1";



        // Enter data to row 1 column 0
        facebookdata[1][0]="Selenium2@gmail.com";

        // Enter data to row 1 column 0
        facebookdata[1][1]="Password2";

        // return arrayobject to testscript
        return facebookdata;
    }
}
