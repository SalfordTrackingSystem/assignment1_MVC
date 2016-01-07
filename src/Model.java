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
 * All the method we need...
 */


public class Model {
    private Controller _ctrl;
    private SerialPort _sp;
    private Tracking _track;
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

    /**
     * Handle requests
     * @param flag
     */
    public void cmd(String flag){
        if (flag.equals("LIR")) {
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_LIR.SC);
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_LIR.ID);

        } else if (flag.equals("RIR")) {
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_RIR.SC);
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_RIR.ID);

        } else if (flag.equals("THE")) {
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_THERMAL.SC);
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_THERMAL.ID);

        } else if (flag.equals("MOT")) {
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_MOTOR_GET.SC);
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_MOTOR_GET.ID);

        } else if (flag.equals("MOTL")) {
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_MOTOR_LEFT.SC);
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_MOTOR_LEFT.ID);

        } else if (flag.equals("MOTR")) {
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_MOTOR_RIGHT.SC);
            _ctrl.getSerialPort().txByte((byte) protocol.SENSOR_MOTOR_RIGHT.ID);

        } else {
            System.out.println("Invalid CMD");
        }
    }

    /**
     * Send requests
     * @param i
     * @param t
     */
    public void sendCmd(int i, int t){
        if(t==0){
            if (i==1)cmd(frame.SENSOR_THERMAL.NAME);
            if (i==2)cmd(frame.SENSOR_THERMAL.NAME);
            if (i==3)cmd(frame.SENSOR_THERMAL.NAME);
            if (i==4)cmd(frame.SENSOR_THERMAL.NAME);
        }else if(t==5){
            cmd(protocol.SENSOR_MOTOR_LEFT.NAME);
            System.out.println("cmd left send");
            _ctrl.getProducer().setTrack(0);
        }else if(t==6){
            cmd(protocol.SENSOR_MOTOR_RIGHT.NAME);
            System.out.println("cmd right send");
            _ctrl.getProducer().setTrack(0);
        }else{
            System.out.println("cmd pb with sendCmd()");
        }
    }

    /**
     * Use to simulate commands
     * @param i
     * @return
     */
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
        byte i =(byte)(rd.nextInt(254)+1);
        byte j =(byte)(rd.nextInt(4));
        byte[] frameSigned = {(byte)'#',(byte)'A',36, 1, j, i, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 124, 0, 37};

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return frameSigned;
    }
    public byte[] simulation_frame_LIR(){
        Random rd = new Random();
        byte i =(byte)(rd.nextInt(254)+1);
        byte j =(byte)(rd.nextInt(4));
        byte[] frameSigned = {(byte)'#',(byte)'A',36, 2, j, i, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 124, 0, 37};

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return frameSigned;
    }
    public byte[] simulation_frame_THE(){
        Random rd = new Random();
        byte i = (byte) (rd.nextInt(100)+1);
        byte[] frameSigned = {(byte)'#',(byte)'A',36, 3, (byte) 255, i, i, i, i, (byte) 255, (byte) 255, i, i, (byte) 255, (byte) 255, i, i, i, i, i, 124, 0, 37};

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return (frameSigned);
    }
    public byte[] simulation_frame_MOT(){
        Random rd = new Random();
        byte i =(byte)(rd.nextInt(170)+1);
        byte[] frameSigned = {(byte)'#',(byte)'A',36, 4, i, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 124, 0, 37};

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return frameSigned;
    }
    /**
     * Method     : checkCRC()
     * Parameters : byte[] data (frame),  byte crcReceive
     * Returns    : Boolean => to validated the frame or not
     * Notes      : Analyse the CRC and compare it to the CRC calculate by the µC
     * Warning    : We haven't the time to debug the crc, so we always returning True
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
                if ((crc & 0x80) == 0x80)//0b10000000)/* If the uppermost bit is a 1... */
                    crc = (byte) ((crc << 1) ^ POLYNOMIAL);
                else crc = (byte) (crc << 1);
            }
        }
        if (crc != crcReceive){
            return true;  // put false for the real application
        }
        else{
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
                }
                else                                            System.out.println("EB is not valid");
            }
        }
        else                                                    System.out.println("SB is not valid");
    }

    /**
     * Choose the right queue to put the frame in.
     * @param flag
     * @param data
     */
    public void switchQueue(String flag, int[] data){
        if (flag.equals("LIR")) {
            try {
                _ctrl.getQIR().put(data);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        } else if (flag.equals("RIR")) {
            try {
                _ctrl.getQIR().put(data);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        } else if (flag.equals("THE")) {
            try {
                _ctrl.getQTH().put(data);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        } else if (flag.equals("MOT")) {
            try {
                _ctrl.getQMO().put(data);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        } else {
            System.out.println("Invalid NAME");

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
        if (flag.equals("LIR")) {
            _ctrl.getGUI().setValue_L_IR(dist);
        } else if (flag.equals("RIR")) {
            _ctrl.getGUI().setValue_R_IR(dist);
        } else if (flag.equals("THE")) {
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++) {
                    if (data[17 - i * 4 - j] > 255 || data[17 - i * 4 - j] < 0)
                        color = 128;
                    else
                        color = 255 - data[17 - i * 4 - j];
                    this._ctrl.getGUI().get_tablePanel()[i][j].setBackground(new Color(color, 0, 0));
                    this._ctrl.getGUI().get_imagePanel().add(this._ctrl.getGUI().get_tablePanel()[i][j]);
                }

        } else if (flag.equals("MOT")) {//System.out.println("MOT do nothing");
            _ctrl.getGUI().setValue_motor(dist);
        } else {
            System.out.println("Invalid NAME");

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
    }

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
