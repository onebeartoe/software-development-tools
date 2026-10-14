package org.onebeartoe.development.tools.lib.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LibSearchTest 
{
    @Test
    public void testLibSearchInstantiation() 
    {
        String webappsPath = "src/test/resources/webapps";
        
        String searchTarget = "log4j";
        
        LibSearch app = new LibSearch();
    
        assertThat(app).isNotNull();
        
        Map<String, List<String>> resultsMap = app.search(webappsPath, searchTarget);
        
        assertThat( resultsMap.size() ).isEqualTo(4);
        
        String webapp = "another-single";
        List<String> matches = resultsMap.get(webapp);
        List<String> expectedTargets = new ArrayList( Arrays.asList("log4j-3.2.jar"));
        assertOne(matches, expectedTargets);
        
        webapp = "multiple";
        matches = resultsMap.get(webapp);
        expectedTargets = new ArrayList( Arrays.asList("log4j-1.0.jar", "log4j-helper-1.0.jar"));
        assertOne(matches, expectedTargets);
        
        webapp = "none";
        matches = resultsMap.get(webapp);
        assertThat(matches).isNull();
        
        webapp = "single";
        matches = resultsMap.get(webapp);
        expectedTargets = new ArrayList( Arrays.asList("log4j-1.0.jar"));
        assertOne(matches, expectedTargets);
        
        webapp = "uppercase";
        matches = resultsMap.get(webapp);
        expectedTargets = new ArrayList( Arrays.asList("lOG4j-1.0.jar"));
        assertOne(matches, expectedTargets);        
    }
    
    private void assertOne(List<String> matches, List<String> expectedTargets)
    {
        int expectedSize = expectedTargets.size();
                
        assertThat(expectedSize).isEqualTo(matches.size());
        
        assertThat(matches).containsAll(expectedTargets);
    }
}
