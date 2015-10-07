import sun.misc.ASCIICaseInsensitiveComparator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 03/10/15
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */


public class Model {
    private Controller _ctrl;
    private SerialPort _sp;

    public Model(Controller controller){
        this._ctrl = controller;
        _sp = new SerialPort(_ctrl);
        _sp.initialize();
    }

    public int simulation(){
        Random rd = new Random();
        int n=rd.nextInt(100)+1;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println(n);
        return n;
    }

    public byte[] simulation_frame(){
        //Random rd = new Random();
        byte i = 1;
        //byte[] frame = {36, 2, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, -57, 0, 37};      // 16 bits
        byte[] frame = {36, 2, i, i, i, i, i, i, i, i, 64, 0, 37};                                 // 8 bits
        //n=rd.nextInt(100)+1;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //System.out.println(frame[0]);
        return frame;
    }

    public boolean checkCRC(byte[] data){
        //CRC
        //P(x)=x^8+x^5+x^4+1 = 100110001
        int POLYNOMIAL = 31;
        byte nbrOfBytes = (byte) (data.length - 5);
        byte crcReceive = data[data.length - 3];
        byte crc = 0;
        byte byteCtr;
        byte bit;
        //calculates 8-Bit checksum with given polynomial
        for (byteCtr = 0; byteCtr < nbrOfBytes; ++byteCtr){
            /* Initially, the dividend is the remainder */
            crc ^= (data[byteCtr]);
            /*  For each bit position in the message */
            for (bit = 8; bit > 0; --bit){
                if ((crc & 0x80) == 0b10000000)/* If the uppermost bit is a 1... */
                    crc = (byte) ((crc << 1) ^ POLYNOMIAL);
                else crc = (byte) (crc << 1);
            }
        }
        if (crc != crcReceive){
            System.out.println(crc);
            return false;
        }
        else{
            System.out.println(crc);
            return true;
        }
    }

    public void checkData(byte[] data){
        String flag = "";
        if (data[0] == frame.SENSOR_FUSION.SB){ //|| data[0] == frame.SENSOR_LIR.SB || data[0] == frame.SENSOR_RIR.SB || data[0] == frame.SENSOR_MOTOR.SB){
            if (this.checkCRC(data)){
                if (data[1] == frame.SENSOR_LIR.ID)             flag = frame.SENSOR_LIR.NAME;
                else if (data[1] == frame.SENSOR_RIR.ID)        flag = frame.SENSOR_RIR.NAME;
                else if (data[1] == frame.SENSOR_FUSION.ID)     flag = frame.SENSOR_FUSION.NAME;
                else if (data[1] == frame.SENSOR_MOTOR.ID)      flag = frame.SENSOR_MOTOR.NAME;
                else                                            System.out.println("ID is not valid");
            }
            else                                                System.out.println("CS is not valid");
            if (flag != ""){
                if (data[data.length-1] == frame.SENSOR_LIR.EB) System.out.println(flag + " => | "+data[0]+" | "+data[1]+" | "+data[data.length-3]+" | "+data[data.length-2]+" | "+data[data.length-1]+" |");
                else                                            System.out.println("EB is not valid");
            }
        }
        else System.out.println("SB is not valid");
    }
    public byte[] simulation_frame_color(){
        Random rd = new Random();
        byte i = (byte) (rd.nextInt(100)+1);
        byte[] frame = {36, 2, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, 124, 0, 37};
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //System.out.println(frame[0]);
        return frame;
    }

    public void changedColor(JPanel[][] tablePanel, JPanel imagePanel, byte[] frame){
        //Random rd = new Random();
        //int n = rd.nextInt(50)+1;

        //System.out.println(n);
        for(int i=0 ; i<4   ; i++)
            for(int j=0 ; j<4 ; j++)
            {
                //tablePanel[i][j] = new JPanel();
                int val = (i + j) * frame[i*4+j+2];
                //int val = frame[i*4+j+2];
                if (val > 255)
                    val=255;
                this._ctrl.getGUI().get_tablePanel()[i][j].setBackground(new Color(val, 0, 0));
                this._ctrl.getGUI().get_imagePanel().add(this._ctrl.getGUI().get_tablePanel()[i][j]);
            }
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } */
    }

    public SerialPort getSerialPort(){
        return _sp;
    }
}
