
package org.onebeartoe.development.tools.web.content.verification;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 */
public class WebContentService 
{
    private Logger logger;
    
//    public static final String baseUrl = "http://electronics.onebeartoe.org/";
    
    public WebContentService()
    {
        logger = LoggerFactory.getLogger( getClass().getName() );    
    }
    
    private List<InternalLink> getBadLinks(Class crawlerClass, String rootUrl) throws Exception 
    {
    /*
     * crawlStorageFolder is a folder where intermediate crawl data is
     * stored.
     */
        String crawlStorageFolder;

    /*
     * numberOfCrawlers shows the number of concurrent threads that should
     * be initiated for crawling.
     */
//        int numberOfCrawlers = Integer.parseInt(args[1]);        
            
        crawlStorageFolder = "target/crawler4j";   // this driectory will contain intermediate crawl data
        int numberOfCrawlers = 5;           // number of concurrent threads

        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder(crawlStorageFolder);

    /*
     * Be polite: Make sure that we don't send more than 1 request per
     * second (1000 milliseconds between requests).
     */
        config.setPolitenessDelay(1000);

    /*
     * You can set the maximum crawl depth here. The default value is -1 for
     * unlimited depth
     */
        config.setMaxDepthOfCrawling(200);

    /*
     * You can set the maximum number of pages to crawl. The default value
     * is -1 for unlimited number of pages
     */
        config.setMaxPagesToFetch(10_000);

    /*
     * Do you need to set a proxy? If so, you can use:
     * config.setProxyHost("proxyserver.example.com");
     * config.setProxyPort(8080);
     *
     * If your proxy also needs authentication:
     * config.setProxyUsername(username); config.getProxyPassword(password);
     */

    /*
     * This config parameter can be used to set your crawl to be resumable
     * (meaning that you can resume the crawl from a previously
     * interrupted/crashed crawl). Note: if you enable resuming feature and
     * want to start a fresh crawl, you need to delete the contents of
     * rootFolder manually.
     */
        config.setResumableCrawling(false);

    /*
     * Instantiate the controller for this crawl.
     */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

    /*
     * For each crawl, you need to add some seed urls. These are the first
     * URLs that are fetched and then the crawler starts following links
     * which are found in these pages
     */
//TODO: Let's see if we need this?
//      Yep, it is needed a seed added to start the crawl.
        controller.addSeed(rootUrl);

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(crawlerClass, numberOfCrawlers);
//TODO: Look into the WebCrawlerFactory version of the start() method.        
//        controller.start(crawlerFactory, numberOfCrawlers);
        
        List<Object> crawlersLocalData = controller.getCrawlersLocalData();
        long totalLinks = 0;
        long totalTextSize = 0;
        int totalProcessedPages = 0;
        long totalOkLinks = 0;
        List<InternalLink> allBadLinks = new ArrayList();
        
        for (Object localData : crawlersLocalData) 
        {
            CrawlStat stat = (CrawlStat) localData;

            List<InternalLink> badLinks = stat.getLinks();
            allBadLinks.addAll(badLinks);
            
            totalLinks += stat.getTotalLinks();
            totalTextSize += stat.getTotalTextSize();
            totalOkLinks += stat.getOkVisitCount();
            totalProcessedPages += stat.getTotalProcessedPages();
        }

        logger.info("Aggregated Statistics:");
        logger.info("\tProcessed Pages: {}", totalProcessedPages);
        logger.info("\tTotal OK links: {}", totalOkLinks);
        logger.info("\tTotal Links found: {}", totalLinks);
        logger.info("\tTotal Text Size: {}", totalTextSize);
                
        allBadLinks.sort(
                (l1, l2) -> l1.getUrl().compareTo(l2.getUrl())
        );
        
        logger.info("Bad links:");
        allBadLinks.forEach( l ->
        {
            logger.info("{} - {} - {}", l.getUrl(), l.getStatusCode(), l.getParentUrls() );
        });
        
        return allBadLinks;
    }
    
    public Object[][] loadBadLinks(Class crawlerClass, String rootUrl) throws Exception
    {
        List<InternalLink> badLinks = getBadLinks(crawlerClass, rootUrl);

        int paramCount = 3;

        List<Object []> rows = badLinks.stream()
                .map( l -> 
                {
                    Object [] array = new Object[paramCount];
                    
                    array[0] = l.getUrl();
                    array[1] = l.getStatusCode();
                    array[2] = l.getParentUrls();
                    
                    return array;
                }).collect(Collectors.toList());
        
        Object[][] data = new Object[rows.size()][paramCount];

        int r = 0;
        
        for(Object [] row : rows)
        {
            data[r] = row;
            
            r++;
        }
        
        return data;
    }    
}
