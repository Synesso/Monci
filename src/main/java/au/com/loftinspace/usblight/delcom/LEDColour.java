package au.com.loftinspace.usblight.delcom;

public enum LEDColour {
    BLUE((byte)3), RED((byte)5), GREEN((byte)6);
    private byte deviceRepresentation;

    private LEDColour(byte deviceRepresentation) {
        this.deviceRepresentation = deviceRepresentation;
    }

    public byte getDeviceRepresentation() {
        return deviceRepresentation;
    }
}
