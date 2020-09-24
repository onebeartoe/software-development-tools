
package org.onebeartoe.development.tools.web.content.verification;

import java.util.ArrayList;
import java.util.List;

//TODO: rename this clas to CrawlStatistics
public class CrawlStatistics
{    
    private long okVisitCount;
    
    private int totalProcessedPages;
    
    private long totalLinks;
    
    private long totalTextSize;
    
    private final List<InternalLink> links;

    public CrawlStatistics()
    {
        okVisitCount = 0;
        
        links = new ArrayList();
    }
    
    public void addLink(InternalLink link)
    {
        links.add(link);
    }
    
    public List<InternalLink> getLinks()
    {
        return links;
    }

    public long getOkVisitCount()
    {
        return okVisitCount;
    }
    public long getTotalLinks() 
    {
        return totalLinks;
    }
    
    public int getTotalProcessedPages() 
    {
        return totalProcessedPages;
    }

    public long getTotalTextSize() {
        return totalTextSize;
    }

    public void incProcessedPages() {
        this.totalProcessedPages++;
    }    
    
    public void increateOkVisitCount()
    {
        okVisitCount++;
    }
    
    public void incTotalLinks(int count) {
        this.totalLinks += count;
    }

    public void incTotalTextSize(int count) {
        this.totalTextSize += count;
    }    
    
    public void setTotalProcessedPages(int totalProcessedPages) {
        this.totalProcessedPages = totalProcessedPages;
    }

    public void setTotalLinks(long totalLinks) {
        this.totalLinks = totalLinks;
    }

    public void setTotalTextSize(long totalTextSize) {
        this.totalTextSize = totalTextSize;
    }
}