package com.psy;

import jssc.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PortEventListener implements SerialPortEventListener {
    SerialPort serialPort;
    StringBuilder buffer;

    public PortEventListener() {
    }

    public PortEventListener(SerialPort serialPort, StringBuilder buffer) {
        this.buffer = buffer;
        this.serialPort = serialPort;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                String receivedData = serialPort.readHexString(event.getEventValue());

                buffer.append(receivedData).append(" ");
            } catch (SerialPortException ex) {
                System.out.println("Error in receiving string from COM-port: " + ex);
            }
        }

    }

}
