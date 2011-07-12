package au.com.loftinspace.usblight.delcom;

public class UsbLightException extends Exception {
    public UsbLightException(String message) {
        super(message);
    }

    public UsbLightException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
