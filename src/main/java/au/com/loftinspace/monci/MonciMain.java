package au.com.loftinspace.monci;

import au.com.loftinspace.monci.server.CIServer;
import au.com.loftinspace.monci.server.HudsonServer;
import au.com.loftinspace.monci.monitor.ServerMonitor;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.io.IOException;
import java.io.File;
import java.util.Properties;

public class MonciMain implements ActionListener {

    public static void main(String[] args) throws Exception {
/*
        URL resource = MonciMain.class.getResource("/sound/success.wav");
        System.out.println("resource.toExternalForm() = " + resource.toExternalForm());
        System.out.println("resource.getFile() = " + resource.getFile());
//        Properties properties = System.getProperties();
//        for (Object key:properties.keySet()) {
//            System.out.println(key+"="+properties.get(key));
//        }
        File file = new File(System.getProperty("user.dir") + "/" + System.getProperty("java.class.path") + "!libJavaxUsb.so");
//        file. // trying to copy the file out so it can be loaded
        System.load(System.getProperty("user.dir") + "/" + System.getProperty("java.class.path") + "!libJavaxUsb.so");
*/
        new MonciMain().go();
    }

    private void go() throws AWTException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        Properties props = new Properties();
        props.load(MonciMain.class.getResourceAsStream("/ci_server.properties"));
        String feedUrl = props.getProperty("server.feed.url");
        URL resource = MonciMain.class.getResource("/img/icon.png");
        Image icon = Toolkit.getDefaultToolkit().getImage(resource);
        PopupMenu popup = new PopupMenu();
        MenuItem menuItem = new MenuItem("Nothing in the menu yet");
        popup.add(menuItem);
        menuItem.addActionListener(this);
        TrayIcon trayIcon = new TrayIcon(icon, "Monci", popup);
        SystemTray.getSystemTray().add(trayIcon);

        CIServer server = new HudsonServer(feedUrl);
        ServerMonitor monitor = new ServerMonitor(server);
        monitor.startMonitoring();
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("e = " + e);
    }
}
