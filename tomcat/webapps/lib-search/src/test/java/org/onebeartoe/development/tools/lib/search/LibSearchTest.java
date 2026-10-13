package org.onebeartoe.development.tools.lib.search;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LibSearchTest 
{
    @Test
    public void testLibSearchInstantiation() 
    {
        String webappsPath = "";
        
        String searchTarget = "";
        
        LibSearch app = new LibSearch();
    
        assertThat(app).isNotNull();
        
        List<SearchResults> results = app.search(webappsPath, searchTarget);
        
        
    }
}
