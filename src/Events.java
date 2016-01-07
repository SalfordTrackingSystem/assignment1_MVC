/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 03/10/15
 * Time: 11:06
 * To change this template use File | Settings | File Templates.
 * Class use in the beginning to handle events, but with so bug sometimes.
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

    public Events(Controller controller){
        this._ctrl = controller;
    }

    public synchronized void serialEvent(SerialPortEvent oEvent)
    {
        if(oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                // [+]Acquire bytes from serial port.
                int available = _ctrl.getSerialPort().get_input().available();
            }
            catch(Exception e)
            {
                System.err.println(e.toString());
            }
        }
    }
}
