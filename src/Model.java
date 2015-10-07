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
        byte[] frame = {36, 2, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, 16, 0, 37};
        //n=rd.nextInt(100)+1;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //System.out.println(frame[0]);
        return frame;
    }

    public void checkData(byte[] data){
        String flag = "";
        if (data[0] == frame.SENSOR_FUSION.SB){
            int nbByte = data.length - 5;
            if (data[1] == frame.SENSOR_LIR.ID){
                if (nbByte == frame.SENSOR_LIR.CS)
                    flag = frame.SENSOR_LIR.NAME;
            }
            else if (data[1] == frame.SENSOR_RIR.ID){
                if (nbByte == frame.SENSOR_RIR.CS)
                    flag = frame.SENSOR_RIR.NAME;
            }
            else if (data[1] == frame.SENSOR_FUSION.ID){
                if (nbByte == frame.SENSOR_FUSION.CS)
                    flag = frame.SENSOR_FUSION.NAME;
            }
            else if (data[1] == frame.SENSOR_MOTOR.ID){
                if (nbByte == frame.SENSOR_MOTOR.CS)
                    flag = frame.SENSOR_MOTOR.NAME;
            }
            else{
                System.out.println("ID not valid");
            }
        }
        else{
            System.out.println("No data");
        }
        ///////
        // If the flag have been changed, check the EB
        // Print the CN of the sensor frame and valid the frame
        // Same thing for all sensors
        ///////
        if (flag != ""){
            if (data[data.length-1] == frame.SENSOR_LIR.EB){
                System.out.println(flag + " | CN : " + data[data.length-2]);
            }
        }
    }

    public void changedColor(JPanel[][] tablePanel, JPanel imagePanel){
        Random rd = new Random();
        int n = rd.nextInt(50)+1;

        System.out.println(n);
        for(int i=0 ; i<4   ; i++)
            for(int j=0 ; j<4 ; j++)
            {
                //tablePanel[i][j] = new JPanel();
                int val = (i + j) * n;
                if (val > 255)
                    val=255;
                this._ctrl.getGUI().get_tablePanel()[i][j].setBackground(new Color(val, 0, 0));
                this._ctrl.getGUI().get_imagePanel().add(this._ctrl.getGUI().get_tablePanel()[i][j]);
            }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public SerialPort getSerialPort(){
        return _sp;
    }
}
