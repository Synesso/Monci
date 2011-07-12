package au.com.loftinspace.monci.monitor;

import static java.util.concurrent.TimeUnit.SECONDS;
import static com.googlecode.instinct.expect.Expect.expect;
import au.com.loftinspace.monci.server.HudsonServer;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Mock;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Subject;
import org.jmock.Expectations;
import org.junit.runner.RunWith;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.io.IOException;

@RunWith(InstinctRunner.class)
public class AServerMonitor {

    @Subject
    ServerMonitor monitor;
    @Mock
    private ScheduledExecutorService scheduledExecutorService;
    @Mock
    private ScheduledFuture scheduledFuture;
    private HudsonServer server;
    private PollServerCommand command;


    @BeforeSpecification
    void setUp() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        server = new HudsonServer("http://hudson/rssAll");
        monitor = new ServerMonitor(server);
        monitor.setScheduledExecutorService(scheduledExecutorService);
        command = new PollServerCommand(monitor);
    }

    @Specification
    public void monitorsBuildPeriodically() {
        expect.that(new Expectations() {
            {
                one(scheduledExecutorService).scheduleAtFixedRate(command, 0, 10, SECONDS);
                will(returnValue(scheduledFuture));
            }
        });
        monitor.startMonitoring();
    }

    @Specification
    public void monitorsBuildsAtTheSpecifiedInterval() {
        monitor = new ServerMonitor(server, 99);
        monitor.setScheduledExecutorService(scheduledExecutorService);
        command = new PollServerCommand(monitor);
        expect.that(new Expectations() {
            {
                one(scheduledExecutorService).scheduleAtFixedRate(command, 0, 99, SECONDS);
                will(returnValue(scheduledFuture));
            }
        });
        monitor.startMonitoring();
    }

    @Specification
    public void canStopMonitoring() {
        expect.that(new Expectations() {
            {
                one(scheduledExecutorService).scheduleAtFixedRate(command, 0, 10, SECONDS);
                will(returnValue(scheduledFuture));
                one(scheduledFuture).cancel(false);
            }
        });
        monitor.startMonitoring();
        monitor.stopMonitoring();
    }

    @Specification
    public void willDoNothingIfAskedToStopBeforeStarting() {
        expect.that(new Expectations() {
            {
                never(scheduledExecutorService);
                never(scheduledFuture);
            }
        });
        monitor.stopMonitoring();
    }

    @Specification
    public void willDoNothingIfAskedToStartTwice() {
        expect.that(new Expectations() {
            {
                one(scheduledExecutorService).scheduleAtFixedRate(command, 0, 10, SECONDS);
                will(returnValue(scheduledFuture));
            }
        });
        monitor.startMonitoring();
        monitor.startMonitoring();
    }

    @Specification
    public void willDoNothingIfAskedToStopTwice() {
        expect.that(new Expectations() {
            {
                one(scheduledExecutorService).scheduleAtFixedRate(command, 0, 10, SECONDS);
                will(returnValue(scheduledFuture));
                one(scheduledFuture).cancel(false);
            }
        });
        monitor.startMonitoring();
        monitor.stopMonitoring();
        monitor.stopMonitoring();
    }
}
