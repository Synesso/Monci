package au.com.loftinspace.monci.domain;

import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.Response;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.io.IOException;

public class BuildStatusFactory {
    public static Set<BuildStatus> convert(ClientResponse response) {
        Set<BuildStatus> statuses = new HashSet<BuildStatus>();
        Document<Feed> document = response.getDocument();
        Feed feed = document.getRoot();

//
//        try {
//            feed.writeTo(System.out);
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }


        List<Entry> entries = feed.getEntries();
        for (Entry entry : entries) {
            statuses.add(new BuildStatus(entry));
        }
        return statuses;
    }
}
