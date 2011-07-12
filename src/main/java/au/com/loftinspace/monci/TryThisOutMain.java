package au.com.loftinspace.monci;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.Response;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;
import java.util.List;
import java.io.IOException;

public class TryThisOutMain {

    public static void main(String[] args) throws Exception {
        new TryThisOutMain().goAnother();
    }

    private void goAnother() throws IOException {
        Abdera abdera = new Abdera();
        AbderaClient client = new AbderaClient(abdera);
        ClientResponse resp = client.get("http://localhost:8080/rssLatest");
        if (resp.getType() == Response.ResponseType.SUCCESS) {
            Document<Feed> doc = resp.getDocument();
            System.out.println("doc.getEntityTag() = " + doc.getEntityTag());
            System.out.println("doc.getBaseUri() = " + doc.getBaseUri());
            System.out.println("doc.getCharset() = " + doc.getCharset());
            System.out.println("doc.getContentType() = " + doc.getContentType());
            System.out.println("doc.getLanguage() = " + doc.getLanguage());
            System.out.println("doc.getLanguageTag() = " + doc.getLanguageTag());
            System.out.println("doc.getLastModified() = " + doc.getLastModified());
            System.out.println("doc.getMustPreserveWhitespace() = " + doc.getMustPreserveWhitespace());
            System.out.println("doc.getRoot() = " + doc.getRoot());
            System.out.println("doc.getSlug() = " + doc.getSlug());
            System.out.println("doc.getXmlVersion() = " + doc.getXmlVersion());
            Feed feed = doc.getRoot();
            List<Entry> entries = feed.getEntries();
            for (Entry entry : entries) {
                System.out.println("entry = " + entry);
                System.out.println("entry.getTitle() = " + entry.getTitle());
            }
        } else {
            System.out.println("Failure!");
        }
    }

    private void go() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> scheduledFuture = service.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("Hello Thar!");
            }
        }, 0, 1, TimeUnit.SECONDS);
        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
        }
        scheduledFuture.cancel(false);

        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
        }


        scheduledFuture = service.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("Hello Thar!");
            }
        }, 0, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
        }
        service.shutdown();

    }
}
