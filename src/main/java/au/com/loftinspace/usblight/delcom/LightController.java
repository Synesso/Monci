package au.com.loftinspace.usblight.delcom;

import javax.usb.UsbException;
import java.util.EnumSet;

public class LightController {
    private Light light;
    private static final byte ALL_COLOURS = (byte) 7;


    public LightController(Light light) {
        this.light = light;
    }

    public void changeColour(EnumSet<LEDColour> colourSet) throws UsbException {
        byte colours = ALL_COLOURS;
        for (LEDColour colour: colourSet) {
            colours &= colour.getDeviceRepresentation();
        }
        light.control(ControlCommands.CHANGE_PORT_1, colours);

    }

    public void changeColour(LEDColour colour) throws UsbException {
        changeColour(EnumSet.of(colour));
    }

    public void turnOffColours() throws UsbException {
        changeColour(EnumSet.noneOf(LEDColour.class));
    }
    
}
