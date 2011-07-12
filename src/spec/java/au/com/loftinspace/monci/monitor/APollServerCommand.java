package au.com.loftinspace.monci.monitor;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import au.com.loftinspace.monci.monitor.event.InitialUpdateEvent;
import au.com.loftinspace.monci.monitor.event.AbortedUpdateEvent;
import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
public class APollServerCommand {

    @Subject
    private PollServerCommand command;
    @Mock
    private ServerMonitor serverMonitor;

    @BeforeSpecification
    public void setUp() {
        command = new PollServerCommand(serverMonitor);
    }


    // TODO: How to get an instance of for expectation?
    // refer to http://www.nabble.com/aNonNull-unexpected-behaviour-td17644154.html
    @Specification
    public void issuesInitialUpdateEventOnFirstPoll() {
        expect.that(new Expectations() {
            {
                one(serverMonitor).notify(withAnInstanceOf(AbortedUpdateEvent.class));
//                never(serverMonitor).notify((UnchangedUpdateEvent) with(instanceOf(UnchangedUpdateEvent.class)));
            }

            private <T> T withAnInstanceOf(Class<T> aClass) {
                return with((Matcher<T>) instanceOf(aClass));
            }
        });
        command.rin();
    }

/*
    @Specification
    public void issuesBuildStatusUpdateEventOnSubsequentPollsWhenStatusIsUnchanged() {
        expect.that(new Expectations() {
            {
//                one(serverMonitor).notify(with(equal(new InitialUpdateEvent())));
//                one(serverMonitor).notify(with(equal(new UnchangedUpdateEvent())));
            }
        });
        command.run();
    }

    @Specification
    public void issuesBuildStatusUpdateEventOnSubsequentPollsWhenStatusHasChanged() {

    }


*/
}

