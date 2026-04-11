package org.onebeartoe.development.tools.html.utility.panels;

import static org.assertj.core.api.Assertions.assertThat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 */
public class ListsPanelSpecification 
{
    @Test
    public void altText_hasProtocol() throws Exception 
    {
        var expectedHref = "https://w.com/index.html";

        var expectedLabel = "Home";

        var input = expectedLabel + " " + expectedHref;

        ListsPanel implementation = new ListsPanel();

        implementation.anchorTagCheckBox.setSelected(true);

        implementation.textAsLinkCheckBox.setSelected(true);

        implementation.updateInput(input);

        implementation.updateOutput();

        var output = implementation.getOutput();

        Document doc = Jsoup.parse(output);

        Elements links = doc.select("a[href]"); // Selects all <a> with a href attribute

        var link = links.get(0);

        String actualHref = link.attr("href");

        assertThat(expectedHref.equals(actualHref)).isTrue();

        String actualLabel = link.text();

        assertThat(actualLabel.equals(expectedLabel) ).isTrue();
    }
}
