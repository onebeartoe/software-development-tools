/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.onebeartoe.development.tools.sasquatch;

import static org.apache.commons.lang3.text.translate.JavaUnicodeEscaper.above;
import org.testng.annotations.Test;

/**
 *
 * @author roberto
 */
public class StateSightingsServiceSpecification
{
    StateSightingsService implementation = new StateSightingsService();
    
    @Test
    public void sightingsFor_pass(String state)
    {
        SasquatchSighting sighting = implementation.sightingsFor("Texas");
        
        TX	31.968599	-99.901813	Texas
assert the values in line above                
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void sightingsFor_pass(String state)
    {
        SasquatchSighting sighting = implementation.sightingsFor("San Antonio");
        
    }
    
}
