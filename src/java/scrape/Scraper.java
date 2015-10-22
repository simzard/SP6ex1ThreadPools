/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import entities.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author simon
 */
public class Scraper {

    public List<Group> groups = new ArrayList();

    public List<String> urls = new ArrayList<String>() {
        {
            //Class A
            add("http://cphbusinessjb.cloudapp.net/CA2/");
            add("http://ca2-ebski.rhcloud.com/CA2New/");
            add("http://ca2-chrislind.rhcloud.com/CA2Final/");
            add("http://ca2-pernille.rhcloud.com/NYCA2/");
            add("https://ca2-jonasrafn.rhcloud.com:8443/company.jsp");
            add("http://ca2javathehutt-smcphbusiness.rhcloud.com/ca2/index.jsp");

            //Class B
            add("https://ca2-ssteinaa.rhcloud.com/CA2/");
            add("http://tomcat-nharbo.rhcloud.com/CA2/");
            add("https://ca2-cphol24.rhcloud.com/3.semCa.2/");
            add("https://ca2-ksw.rhcloud.com/DeGuleSider/");
            add("http://ca2-ab207.rhcloud.com/CA2/index.html");
            add("http://ca2-sindt.rhcloud.com/CA2/index.jsp");
            add("http://ca2gruppe8-tocvfan.rhcloud.com/");
            add("https://ca-ichti.rhcloud.com/CA2/");

            //Class COS
            add("https://ca2-9fitteen.rhcloud.com:8443/CA2/");
            add("https://cagroup04-coolnerds.rhcloud.com/CA_v1/index.html");
            add("http://catwo-2ndsemester.rhcloud.com/CA2/");
        }
    };

    private class ScraperWorkerUnit implements Callable<Group> {

        private String url;

        public ScraperWorkerUnit(String url) {
            this.url = url;
        }

        @Override
        public Group call() throws Exception {
            // get authors
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("#authors");
            String authors = (elements.text());

            // get class
            elements = doc.select("#class");
            String classDescr = (elements.text());

            // get group
            elements = doc.select("#group");
            String groupNo = (elements.text());

            return new Group(authors, classDescr, groupNo);
        }

    }

    public static List<Group> beginScrape() throws InterruptedException, ExecutionException {
        
        
        Scraper s = new Scraper();

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        List<Future<Group>> futures = new ArrayList();

        for (String url : s.urls) {
            Future fut = threadPool.submit(s.new ScraperWorkerUnit(url));
            futures.add(fut);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.DAYS);

        List<Group> groups = new ArrayList();
        
        for (Future<Group> fut : futures) {
            groups.add(fut.get());
        }
    
        return groups;
    
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Scraper s = new Scraper();

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        List<Future<Group>> futures = new ArrayList();

        for (String url : s.urls) {
            Future fut = threadPool.submit(s.new ScraperWorkerUnit(url));
            futures.add(fut);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.DAYS);

        // initially just print the values to the console
        for (Future<Group> fut : futures) {
            System.out.println("authors: " + fut.get().getAuthors());
            System.out.println("class: " + fut.get().getClassDescr());
            System.out.println("groupNo: " + fut.get().getGroupNo());
            System.out.println("");
        }
    }
}
