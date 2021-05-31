package com.psy;

import com.psy.dao.IndicationDao;
import com.psy.entity.Indication;
import jssc.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class Application {

    static StringBuilder buffer = new StringBuilder();
    static SerialPort serialPort;

    public static void main(String[] args) {
        Application.connect("COM4", 9600, 8, 1, 0);
        Application.currentDate();
        //Application.counterType();
        //Application.instantValue();
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public static void connect(String portName, int baudRate, int dataBits, int stopBits, int parity) {
        //Передаём в конструктор имя порта
        serialPort = new SerialPort(portName);
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

        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    public static String writeBytes(int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) throws SerialPortException, InterruptedException {
        byte[] bytes = {(byte) i, (byte) i1, (byte) i2, (byte) i3, (byte) i4, (byte) i5, (byte) i6, (byte) i7};
        String str = new String(bytes, StandardCharsets.UTF_8);
        serialPort.writeString(str);
        Thread.sleep(500);
        String result = buffer.toString().trim();
        buffer.setLength(0);

        return result;
    }

    public static void currentDate(){
        try {
            String x = Application.writeBytes(0, 3, 32, 0, 0, 0, 0xDB, 0x4F); //Не работает
           // String[] data = x.split(" ");
           // String[] data1 = Arrays.copyOfRange(data, 4, data.length - 2);
            System.out.println(x);
        } catch (SerialPortException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void counterType() {
        try {
            String x = Application.writeBytes(0, 3, 0, 0, 0, 0, 0x1b, 0x44);
            String[] data = x.split(" ");
            String[] data1 = Arrays.copyOfRange(data, 4, data.length - 2);
            String Type = data1[0];
            String Number = data1[1];
            if (!Type.equals("02")) {
                return;
            }
            if (Number.equals("01")) {
                int result = 101;
                System.out.println(result);
            }

        } catch (SerialPortException | InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void instantValue() {
        try {
            while (true) {
                String x = Application.writeBytes(0, 4, 46, 0, 0, 1, 0x33, 0x39);
                Info info = parse(x);
                System.out.println(info);
                Thread.sleep(1000);
                var indicationDao = IndicationDao.getInstance();
                var indication = new Indication();
                indication.setP(info.P);
                indication.setQ(info.Q);
                indication.setU(info.U);
                indication.setI(info.I);
                indication.setPhi(info.Phi);
                indication.setV(info.V);
                var savedInformation = indicationDao.save(indication);
                System.out.println(savedInformation);
            }

        } catch (SerialPortException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static class Info {
        double P;
        double Q;
        double U;
        double I;
        double Phi;
        double V;

        public Info(double p, double q, double u, double i, double phi, double v) {
            P = p;
            Q = q;
            U = u;
            I = i;
            Phi = phi;
            V = v;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "P=" + P +
                    ", Q=" + Q +
                    ", U=" + U +
                    ", I=" + I +
                    ", Phi=" + Phi +
                    ", V=" + V +
                    '}';
        }
    }

    public static void Type(String s){

    }

    public static Info parse(String s) {
        double[] result = new double[6];
        int k = 0;
        String[] data2 = new String[0];
        String[] data = s.split(" ");
        String[] data1 = Arrays.copyOfRange(data, 4, data.length - 2);
        int c = data1.length;
        int d = 0;
        int e = 4;
        String h;
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
        return new Info(result[0], result[1], result[2], result[3], result[4], result[5]);
    }
}