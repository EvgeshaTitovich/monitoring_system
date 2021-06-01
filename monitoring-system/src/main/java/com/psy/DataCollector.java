package com.psy;

import com.psy.dao.IndicationDao;
import com.psy.entity.Indication;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class DataCollector {
  private final StringBuilder buffer = new StringBuilder();
  private final IndicationDao indicationDao = IndicationDao.getInstance();
  private final ScheduledExecutorService checkExecutor = Executors.newScheduledThreadPool(1);
  private SerialPort serialPort;


  @PostConstruct
  public void init() {
    start("COM4", 9600, 8, 1, 0);
  }

  public void start(String portName, int baudRate, int dataBits, int stopBits, int parity) {
    this.serialPort = new SerialPort(portName);
    try {
      //Открываем порт
      serialPort.openPort();
      //Выставляем параметры
      serialPort.setParams(baudRate, dataBits, stopBits, parity);
      //Включаем аппаратное управление потоком
      serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
          SerialPort.FLOWCONTROL_RTSCTS_OUT);
      //Устанавливаем ивент лисенер и маску
      serialPort.addEventListener(new PortEventListener(serialPort, buffer), SerialPort.MASK_RXCHAR);


      checkExecutor.scheduleWithFixedDelay(
          this::collect,
          3,
          3,
          TimeUnit.SECONDS
      );
    } catch (SerialPortException ex) {
      System.out.println(ex);
    }
  }

  private void collect() {
    try {
        String x = writeBytes(new byte[]{0, 4, 46, 0, 0, 1, 0x33, 0x39});
        Indication indication = parse(x);
        var savedInformation = indicationDao.save(indication);
        System.out.println(savedInformation);
    } catch (SerialPortException | InterruptedException e) {
      e.printStackTrace();
    }

  }

  private String writeBytes(byte[] bytes) throws SerialPortException, InterruptedException {
    String str = new String(bytes, StandardCharsets.UTF_8);
    serialPort.writeString(str);
    Thread.sleep(500);
    String result = buffer.toString().trim();
    buffer.setLength(0);
    return result;
  }

  public static Indication parse(String s) {
    double[] result = new double[6];
    int k = 0;
    String[] data2 = new String[0];
    String[] data = s.split(" ");
    String[] data1 = Arrays.copyOfRange(data, 4, data.length - 2);
    int c = data1.length;
    int d = 0;
    int e = 4;
    Long g;
    Float f;
    String temp;

    while (c > 0) {
      if (data1.length % 4 == 0) {
        data2 = Arrays.copyOfRange(data1, d, e);
      }
      int n = data2.length;
      for (int i = 0; i < n / 2; i++) {
        temp = data2[n - i - 1];
        data2[n - i - 1] = data2[i];
        data2[i] = temp;
      }
      StringBuilder data4 = new StringBuilder();
      for (int i = 0; i < n; i++) {
        data4.append(data2[i]);
      }
      g = Long.parseLong(data4.toString(), 16);
      f = Float.intBitsToFloat(g.intValue());
      result[k++] = f;
      c = c - 4;
      d = d + 4;
      e = e + 4;
    }
    return new Indication(0, result[0], result[1], result[2], result[3], result[4], result[5]);
  }

  static class PortEventListener implements SerialPortEventListener {
    SerialPort serialPort;
    StringBuilder buffer;

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
}
