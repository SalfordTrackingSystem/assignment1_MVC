/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 03/10/15
 * Time: 11:06
 * To change this template use File | Settings | File Templates.
 */
import gnu.io.SerialPortEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import gnu.io.SerialPortEventListener;

public class Events implements SerialPortEventListener {//ChangeListener,
    private Controller _ctrl;
    //private byte data[] = null;

    public Events(Controller controller){
        this._ctrl = controller;
    }
    /*
    public void stateChanged(ChangeEvent e){
        System.out.println("Clicked.");
        JSlider slider = (JSlider)e.getSource();

        //System.out.println(slider);

        //System.out.println(_ctrl.getGUI().getSlider_R_IR());

        // If value is for the slider ... not implemented, have to change next line code
        // This implementation only move the L_IR slider throughout R_IR slider
        if(slider == _ctrl.getGUI().getSlider_R_IR()){
            _ctrl.setSliderValue_L_IR(_ctrl.getGUI().getSlider_R_IR().getValue());
            System.out.println("L_IR");
        }
        else{
            System.out.println("Error Events class");
        }
    }
    */
    /*
    //scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
    public void adjustmentValueChanged(AdjustmentEvent e) {
        e.getAdjustable().setValue(e.getAdjustable().getMaximum());
    }
      */

    public synchronized void serialEvent(SerialPortEvent oEvent)
    {
        if(oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                //input = _ctrl.getSerialPort().get_input();
                // [+]Acquire bytes from serial port.
                int available = _ctrl.getSerialPort().get_input().available();
                //_ctrl.getSerialPort().getData() = new byte[available];
                //_ctrl.getSerialPort().get_input().read(data, 0, available);
                //System.out.println("brow");
                // [+]Read incoming bytes:
                //data[i] ...

                //System.out.println(data.length);
                /*
                for(int i=0; i<data.length; i++){
                    if(data[i] == '$')

                        System.out.println("OK");
                    //System.out.print(data[i]+" , ");
                }    */

               // System.out.println();
            }
            catch(Exception e)
            {
                System.err.println(e.toString());
            }
        }
    }
}
