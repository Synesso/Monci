package au.com.loftinspace.usblight.delcom;

import javax.usb.UsbException;

public interface Light {
    void control(ControlCommands commands, byte lsb) throws UsbException;
}
