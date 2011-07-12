package au.com.loftinspace.monci.monitor;

import au.com.loftinspace.monci.server.CIServer;
import au.com.loftinspace.monci.monitor.listener.*;
import au.com.loftinspace.monci.monitor.event.UpdateEvent;
import au.com.loftinspace.usblight.delcom.UsbLightException;

import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.*;
import java.util.Set;
import java.util.HashSet;

public class ServerMonitor {
    private static final int DEFAULT_POLL_INTERVAL = 10;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private ExecutorService executorService = Executors.newCachedThreadPool(); 
    private ScheduledFuture<?> scheduledFuture;
    private final CIServer server;
    private final int interval;
    private Set<UpdateEventListener> listeners = new HashSet<UpdateEventListener>();

    public ServerMonitor(CIServer server) {
        this.server = server;
        this.interval = DEFAULT_POLL_INTERVAL;
        listeners.add(new PrintStreamListener(System.out));
        try {
            listeners.add(new DelcomLightListener());
        } catch (UsbLightException e) {
            System.err.println("Cannot attach DelcomLightListener: " + e);
        }
        try {
            listeners.add(new AudioListener());
        } catch (Exception e) {
            System.err.println("Cannot attach AudioLightListener: " + e);
        }

        System.out.println("Listeners attached: " + listeners);
    }

    public ServerMonitor(CIServer server, int interval) {
        this.server = server;
        this.interval = interval;
    }

    public void startMonitoring() {
        if (scheduledFuture == null) {
            PollServerCommand command = new PollServerCommand(this);
            scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(command, 0, interval, SECONDS);
        }
    }

    public void stopMonitoring() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            scheduledFuture = null;
        }
    }

    void setScheduledExecutorService(ScheduledExecutorService service) {
        this.scheduledExecutorService = service;
    }

    public void notify(UpdateEvent updateEvent, Notification notification) {
        for (UpdateEventListener listener: listeners) {
            executorService.submit(new AsyncUpdateEventListenerAdaptor(listener, updateEvent, notification));
        }
    }

    public CIServer getServer() {
        return server;
    }
}
