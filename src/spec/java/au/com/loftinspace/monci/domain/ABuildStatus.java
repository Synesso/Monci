package au.com.loftinspace.monci.domain;

import org.junit.runner.RunWith;
import org.apache.abdera.model.Entry;
import org.jmock.Expectations;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Subject;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Mock;
import static com.googlecode.instinct.expect.Expect.expect;

@RunWith(InstinctRunner.class)
public class ABuildStatus {

    @Subject
    private BuildStatus status;
    @Mock
    private Entry entry;

    @Specification
    public void constructsItsFieldValuesFromAValidFeed() {
        expect.that(new Expectations() {{
            one(entry).getTitle();will(returnValue("What now. 500 (SUCCESS) #2 (FAILURE)"));
        }});
        status = new BuildStatus(entry);
        expect.that(status.getBuildName()).isEqualTo("What now. 500 (SUCCESS)");
        expect.that(status.getBuildNumber()).isEqualTo(2);
        expect.that(status.getBuildResult()).isEqualTo(BuildResult.FAILURE);
    }

}
