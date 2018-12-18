
package org.onebeartoe.development.tools.web.content.verification.crawlers;

import java.util.regex.Pattern;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import org.onebeartoe.development.tools.web.content.verification.InternalLink;
import org.onebeartoe.development.tools.web.content.verification.CrawlStat;

/**
 * @author Yasser Ganjisaffar
 */
//TODO: Rename this to BadLinkCrawer
public abstract class StatusHandlerCrawler extends WebCrawler 
{
    private static final Logger logger = LoggerFactory.getLogger(StatusHandlerCrawler.class);

    private static final Pattern FILTERS = Pattern.compile(
        ".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" +
        "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    
    private CrawlStat myCrawlStat;
    
//    private String rootUrl;
    
    public StatusHandlerCrawler()//String rootUrl)
    {
        myCrawlStat = new CrawlStat();
        
//        this.rootUrl = rootUrl;
    }
    
    public abstract String getRootUrl();

    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) 
    {
//        url.get
        String href = url.getURL().toLowerCase();
        
        String rootUrl = getRootUrl();
        
        boolean visit = !FILTERS.matcher(href).matches() 
                            && href.startsWith(rootUrl);
        
        return visit;
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) 
    {
//        logger.info("Visited: {}", page.getWebURL().getURL());
        myCrawlStat.incProcessedPages();

// seeign how much performance increases by skipping the HTML parsing
/*
        if(page.getParseData() instanceof HtmlParseData) 
        {
            HtmlParseData parseData = (HtmlParseData) page.getParseData();
            Set<WebURL> links = parseData.getOutgoingUrls();
            myCrawlStat.incTotalLinks(links.size());
            try {
                myCrawlStat.incTotalTextSize(parseData.getText().getBytes("UTF-8").length);
            } catch (UnsupportedEncodingException ignored) {
                // Do nothing
            }
        }
*/
        // We dump this crawler statistics after processing every 50 pages
        if ((myCrawlStat.getTotalProcessedPages() % 50) == 0) 
        {
            dumpMyData();
        }
    }

    @Override
    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) 
    {
        if (statusCode == HttpStatus.SC_OK) 
        {
            myCrawlStat.increateOkVisitCount();
        }
        
        InternalLink bl = new InternalLink();

        bl.setUrl(webUrl.getURL());
        bl.addParentUrl(webUrl.getParentUrl());
        bl.setStatusCode(statusCode);
        bl.setStatusCodeDescription(statusDescription);

        myCrawlStat.addBadLink(bl);

        logger.warn("Non success status for link: {} status code: {}, parent: {}, description: {}",
                    webUrl.getURL(), statusCode, webUrl.getParentUrl(), statusDescription);        
    }
    
    /**
     * This function is called by controller to get the local data of this crawler when job is
     * finished
     */
    @Override
    public Object getMyLocalData() 
    {
        return myCrawlStat;
    }

    /**
     * This function is called by controller before finishing the job.
     * You can put whatever stuff you need here.
     */
    @Override
    public void onBeforeExit() 
    {
        dumpMyData();
    }

    public void dumpMyData() 
    {
        int id = getMyId();
        // You can configure the log to output to file
        logger.info("Crawler {} > Processed Pages: {}", id, myCrawlStat.getTotalProcessedPages());
        logger.info("Crawler {} > Total Links Found: {}", id, myCrawlStat.getTotalLinks());
        logger.info("Crawler {} > Total Text Size: {}", id, myCrawlStat.getTotalTextSize());
    }    
}
