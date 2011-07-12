package au.com.loftinspace.usblight.delcom;

import javax.usb.*;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class LightImpl implements Light {
    private UsbDevice device;

    public LightImpl() throws UsbLightException {
        try {
            UsbServices services = UsbHostManager.getUsbServices();
            UsbHub rootHub = services.getRootUsbHub();
            device = locateDevice(rootHub, "Delcom Engineering", "USB Visual Indicator");
            if (device == null) {
                throw new UsbLightException("Unable to locate USB device. Are you running as root? (javax.usb requires it).");
            }
        } catch (UsbException e) {
            throw new UsbLightException("Unable to locate USB device.", e);
        }
    }

    public void control(ControlCommands commands, byte lsb) throws UsbException {
        UsbControlIrp usbControlIrp =
                device.createUsbControlIrp((byte) 0x48, (byte) 0x12, (short) 0x020a, lsb);
        usbControlIrp.setData(new byte[0]);
        usbControlIrp.setAcceptShortPacket(true);
        device.syncSubmit(usbControlIrp);
    }

    private UsbDevice locateDevice(UsbHub hub, String manufacturer, String product) throws UsbException {
        UsbDevice target = null;
        List<UsbDevice> devices = hub.getAttachedUsbDevices();
        for (UsbDevice device : devices) {
            if (device.isUsbHub()) {
                target = locateDevice((UsbHub) device, manufacturer, product);
                if (target != null) break;
            } else {
                try {
                    String deviceProduct = device.getProductString();
                    String deviceManufacturer = device.getManufacturerString();
                    if (manufacturer.equals(deviceManufacturer) && product.equals(deviceProduct)) {
                        System.out.println("Found " + deviceProduct + " by " + deviceManufacturer);
                        target = device;
                        break;
                    }
                } catch (UnsupportedEncodingException e) {
                    // ignore and move on to next device
                }
            }
        }
        return target;
    }

}
