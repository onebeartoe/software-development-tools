/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onebeartoe.development.tools.web.content.verification;

import java.util.ArrayList;
import java.util.List;

public class CrawlStat 
{    
    private long okVisitCount;
    
    private int totalProcessedPages;
    
    private long totalLinks;
    
    private long totalTextSize;
    
    private List<BadLink> badLinks;

    public CrawlStat()
    {
        okVisitCount = 0;
        
        badLinks = new ArrayList();
    }
    
    public void addBadLink(BadLink badLink)
    {
        badLinks.add(badLink);
    }
    
    public List<BadLink> getBadLinks()
    {
        return badLinks;
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