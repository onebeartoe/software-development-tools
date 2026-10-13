package org.onebeartoe.development.tools.lib.search;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LibSearchTest 
{
    @Test
    public void testLibSearchInstantiation() 
    {
        LibSearch app = new LibSearch();
    
        assertThat(app).isNotNull();
    }
}
