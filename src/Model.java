import sun.misc.ASCIICaseInsensitiveComparator;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
    private Tracking _track;
    //private BlockingQueue<byte[]> qIR;
    //private BlockingQueue<byte[]> qTH;
    //private BlockingQueue<byte[]> qMO;


    /**
     * Method     : Constructor of Model class
     * Parameters : Controller
     * Returns    : Nothing
     * Notes      : Initialize SerialPort class
     **/
    public Model(Controller controller){
        this._ctrl = controller;
        this._track = new Tracking(_ctrl);
        _sp = new SerialPort(_ctrl);
        _sp.initialize();
        long time = System.currentTimeMillis();
        long actualTime = 0;
        long timeout = 0;
    }

    public void cmd(String flag){
        switch (flag){
            case "LIR":  // data on the first
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_LIR.SC);
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_LIR.ID);
                break;
            case "RIR":
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_RIR.SC);
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_RIR.ID);
                break;
            case "THE":
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_THERMAL.SC);
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_THERMAL.ID);
                break;
            case "MOT":
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_MOTOR_GET.SC);
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_MOTOR_GET.ID);
                break;
            case "MOTL":
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_MOTOR_LEFT.SC);
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_MOTOR_LEFT.ID);
                break;
            case "MOTR":
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_MOTOR_RIGHT.SC);
                _ctrl.getSerialPort().txByte((byte)protocol.SENSOR_MOTOR_RIGHT.ID);
                break;
            default:
                System.out.println("Invalid CMD");
                break;
        }
    }

    public void sendCmd(int i, int t){
        if(t==0){
            if (i==1)cmd(frame.SENSOR_LIR.NAME);
            if (i==2)cmd(frame.SENSOR_RIR.NAME);
            if (i==3)cmd(frame.SENSOR_THERMAL.NAME);
            if (i==4)cmd(frame.SENSOR_MOTOR.NAME);
        }else if(t==5){
            cmd(protocol.SENSOR_MOTOR_LEFT.NAME);
            _ctrl.getProducer().setTrack(0);
        }else if(t==6){
            cmd(protocol.SENSOR_MOTOR_RIGHT.NAME);
            _ctrl.getProducer().setTrack(0);
        }else{
            System.out.println("cmd pb with sendCmd()");
        }
    }
    public byte[] getSensors_simulation(int i){
        byte[] comand = new byte[21];
        if (i==1){
           comand=simulation_frame_LIR();
        }
        if (i==2){
            comand=simulation_frame_RIR();
        }
        if (i==3){
            comand=simulation_frame_THE();
        }
        if (i==4){
            comand=simulation_frame_MOT();
        }
        return comand;
    }

        // claim data every 50ms
        /* Pilou function
        while (true)
        {
            if(timeout >= 50) //50ms
            {
                _ctrl.getSerialPort().requestData((byte)1);
                _ctrl.getSerialPort().requestData((byte)2);
                _ctrl.getSerialPort().requestData((byte)3);
                _ctrl.getSerialPort().requestData((byte)4);
                timeout = 0;
                time = System.currentTimeMillis();
            }else{
                actualTime = System.currentTimeMillis();
                timeout = actualTime - time;
            }
        }
        */
        //byte data[] = _sp.;
        //while(true){
            // View frame
            /*
            for (int i =0;i<21;i++){
            //String str = new String(_sp.rxData(), "UTF_8");

            //_ctrl.getGUI().setValue_textPanel(str, "DATA : ");
                System.out.print(_sp.rxData()[i]+" ");
            }
            System.out.println();
            //System.out.println();
            */
            // Set image data to the GUI
            /*
            _ctrl.getGUI().setImage(_sp.rxData());
            */
        //}
    //}
    /**
     * Method     : simulation()
     * Parameters : Waiting time in milliseconds
     * Returns    : Random number between 0 and 101
     * Notes      : Use to simulate number each waiting time
     **/
    public int simulation(int time){
        Random rd = new Random();
        int n=rd.nextInt(100)+1;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println(n);
        return n;
    }
    /**
     * Method     : simulation_frame()
     * Returns    : 8 or 16 bits frame with |SB|ID|CRC|CN|EB|
     * Notes      : Used to simulate frame each second
     **/
    public byte[] simulation_frame_RIR(){
        Random rd = new Random();
        //byte i = 1;
        byte i =(byte)(rd.nextInt(254)+1);
        byte j =(byte)(rd.nextInt(4));
        byte[] frameSigned = {(byte)'#',(byte)'A',36, 1, j, i, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 124, 0, 37};
        //byte[] frame = {36, 2, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, -57, 0, 37};      // 16 bits
        //byte[] frame = {36, 2, i, i, 0, 0, 0, 0, 0, 0, 34, 0, 37};                                 // 8 bits

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //System.out.println(frame[0]);
        return frameSigned;
    }
    public byte[] simulation_frame_LIR(){
        Random rd = new Random();
        byte i =(byte)(rd.nextInt(254)+1);
        byte j =(byte)(rd.nextInt(4));
        byte[] frameSigned = {(byte)'#',(byte)'A',36, 2, j, i, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 124, 0, 37};
        //byte[] frame = {36, 2, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, -57, 0, 37};      // 16 bits
        //byte[] frame = {36, 2, i, i, 0, 0, 0, 0, 0, 0, 34, 0, 37};                                 // 8 bits

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //System.out.println(frame[0]);
        return frameSigned;
    }
    public byte[] simulation_frame_THE(){
        Random rd = new Random();
        byte i = (byte) (rd.nextInt(100)+1);
        byte[] frameSigned = {(byte)'#',(byte)'A',36, 3, (byte) 255, i, i, i, i, (byte) 255, (byte) 255, i, i, (byte) 255, (byte) 255, i, i, i, i, i, 124, 0, 37};

        //byte[] frameSigned = {(byte)'#',(byte)'A',36, 3, (byte)255, 0, 0, 0, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, (byte)125, 100, 124, 0, 37};
        //int[] frame = new int[frameSigned.length];

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //System.out.println(frame[0]);
        /*
        for(int j=0;j<frameSigned.length;j++){
            frame[j] = (frameSigned[j] & 0xFF);
        } */
        return (frameSigned);
    }
    public byte[] simulation_frame_MOT(){
        Random rd = new Random();
        //byte i = 1;
        byte i =(byte)(rd.nextInt(170)+1);
        //if(i<1100) i = (byte) 1100;
        byte[] frameSigned = {(byte)'#',(byte)'A',36, 4, i, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 124, 0, 37};
        //byte[] frame = {36, 2, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, -57, 0, 37};      // 16 bits
        //byte[] frame = {36, 2, i, i, 0, 0, 0, 0, 0, 0, 34, 0, 37};                                 // 8 bits

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //System.out.println(frame[0]);
        return frameSigned;
    }
    /**
     * Method     : checkCRC()
     * Parameters : byte[] data (frame),  byte crcReceive
     * Returns    : Boolean => to validated the frame or not
     * Notes      : Analyse the CRC and compare it to the CRC calculate by the µC
     **/
    private boolean checkCRC(int[] data, int crcReceive){
        //CRC
        //P(x)=x^8+x^5+x^4+1 = 100110001
        int POLYNOMIAL = 31;
        byte nbrOfBytes = (byte) (data.length - 5);
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
            //System.out.println(crc);
            return true;  // put false for the real application
        }
        else{
            //System.out.println(crc);
            return true;
        }
    }
    /**
     * Method     : checkData()
     * Parameters : byte[] data (frame)
     * Returns    : Nothing.
     * Notes      : Perform analysis of the frame and validate it or not
     **/
    public void checkData(int[] data){
        String flag = "";
        if (data[0] == frame.SENSOR_THERMAL.SB){ //|| data[0] == frame.SENSOR_LIR.SB || data[0] == frame.SENSOR_RIR.SB || data[0] == frame.SENSOR_MOTOR.SB){
            int[] data_only = new int[16];
            for(int i=2; i < data.length-3; i++){
                data_only[i-2]=data[i];
            }
            if (this.checkCRC(data_only, data[data.length-3])){
                if (data[1] == frame.SENSOR_LIR.ID)             flag = frame.SENSOR_LIR.NAME;
                else if (data[1] == frame.SENSOR_RIR.ID)        flag = frame.SENSOR_RIR.NAME;
                else if (data[1] == frame.SENSOR_THERMAL.ID)    flag = frame.SENSOR_THERMAL.NAME;
                else if (data[1] == frame.SENSOR_MOTOR.ID)      flag = frame.SENSOR_MOTOR.NAME;
                else                                            System.out.println("ID is not valid");
            }
            else                                                System.out.println("CRC is not valid");
            if (!flag.equals("")){
                if (data[data.length-1] == frame.SENSOR_LIR.EB){  // if it's true, frame is validated
                    System.out.println("Frame validated => |SB|ID|CRC|CN|EB|");
                    System.out.println(flag + " => | "+data[0]+" | "+data[1]+" | "+data[data.length-3]+" | "+data[data.length-2]+" | "+data[data.length-1]+" |");
                    switchQueue(flag, data);
                    //this.applyOnGUI(flag, data);
                }
                else                                            System.out.println("EB is not valid");
            }
        }
        else                                                    System.out.println("SB is not valid");
    }

    public void switchQueue(String flag, int[] data){
        switch (flag){
            case "LIR":  // data on the first
                try {
                    _ctrl.getQIR().put(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                break;
            case "RIR":
                try {
                    _ctrl.getQIR().put(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                break;
            case "THE":
                try {
                    _ctrl.getQTH().put(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                break;
            case "MOT":
                try {
                    _ctrl.getQMO().put(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                break;
            default:
                System.out.println("Invalid NAME");
                break;
        }
    }
    /**
     * Method     : applyOnGUI()
     * Parameters : String flag (name of the sensor), byte[] data (frame)
     * Returns    : Nothing
     * Notes      : Used to display information into the GUI
     **/
    public void applyOnGUI(String flag, int dist, int[] data) {

        int color = 0;
        int position = 0;
        switch (flag){
            case "LIR":  // data on the first
                _ctrl.getGUI().setValue_L_IR(dist);
                _ctrl.getGUI().setValue_textPanel(Integer.toString(dist), "> L_IR_setTo : ");
                break;
            case "RIR":
                _ctrl.getGUI().setValue_R_IR(dist);
                _ctrl.getGUI().setValue_textPanel(Integer.toString(dist), "> R_IR_setTo : ");
                break;
            case "THE":
                for(int i=0 ; i<4 ; i++)
                    for(int j=0 ; j<4 ; j++){
                        if (data[17-i*4-j] > 255 || data[17-i*4-j]<0)
                            color=128;
                        else
                            color = 255 - data[17-i*4-j];
                        //System.out.println(color);
                        this._ctrl.getGUI().get_tablePanel()[i][j].setBackground(new Color(color, 0, 0));
                        this._ctrl.getGUI().get_imagePanel().add(this._ctrl.getGUI().get_tablePanel()[i][j]);
                    }
                break;
            case "MOT":
                //System.out.println("MOT do nothing");
                // !! Not really implemented at this time, need more information on the motor returned value
                //position = data[2];
                _ctrl.getGUI().setValue_motor(dist);
                _ctrl.getGUI().setValue_textPanel(Integer.toString(dist), "> MOTOR_setTo : ");
                break;
            default:
                System.out.println("Invalid NAME");
                break;
        }
    }




    public int[] signedToUnsignedArray(byte[] f){
        int[] unsignedArray = new int[f.length];
        for(int j=0;j<f.length;j++){
            unsignedArray[j] = (f[j] & 0xFF);
        }
        return unsignedArray;
    }
    /**
     * Method     : changedColor()
     * Parameters : JPanel[][] tablePanel (image panel), JPanel imagePanel(image), byte[] frame(sensor frame)
     * Returns    : Nothing
     * Notes      : Only used in a first time to set color in the panel image easily (Has no more the vocation to be used)
     **/
    public void changedColor(JPanel[][] tablePanel, JPanel imagePanel, byte[] frame){
        for(int i=0 ; i<4   ; i++)
            for(int j=0 ; j<4 ; j++)
            {
                int val = (i + j) * frame[i*4+j+2];
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
     /*
    public void cmdToSend(String cmd){
        byte cmdMotor = 0;
        if (cmd == "right"){
            cmdMotor = 5;
        }
        else if (cmd == "left"){
            cmdMotor =6;
        }
        _sp.txByte(cmdMotor);
    }  */


 //Acceseurs
    /**
     * Method     : getSerialPort()
     * Returns    : data from the serial port
     * Notes      : Not used at this time
     **/
    public SerialPort getSerialPort(){
        return _sp;
    }

    /**
     * get_track()
     * return _track
     * @return _track
     */
    public Tracking get_track(){
        return _track;

    }

}
