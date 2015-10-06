import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
//import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Created by Theo Theodoridis.
 * Class    : GUI
 * Version  : v1.0
 * Date     : 06/05/15
 * User     : ttheod
 * email    : ttheod@gmail.com
 * Comments : Class that creates a Graphical Users Interface.
 **/

public class GUI extends JFrame
{
    public static final int IMAGE_COLUMN = 4;
    public static final int IMAGE_LINE   = 4;

    // [+]Graphical components:
    private JPanel panel;
    private JPanel imagePanel;
    private JSlider slider_L_IR;
    private JSlider slider_R_IR;
    private JSlider slider_fusion;
    private JSlider slider_motor;
    private JTextPane textPane;
    private JScrollPane scrollPane;
    private JPanel tablePanel[][];
    private JFrame frame;
    private byte data[];
    private Controller _ctrl;

    public GUI(Controller controller)
    {
        this._ctrl = controller;
        start();

        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });

    }

    public void start()
    {
        // [+]Initialisation of the camera's picture:
        tablePanel = new JPanel[IMAGE_LINE][IMAGE_COLUMN];
        imagePanel.setLayout(new GridLayout(IMAGE_LINE, IMAGE_COLUMN));
        for(int i=0 ; i<IMAGE_LINE   ; i++)
        for(int j=0 ; j<IMAGE_COLUMN ; j++)
        {
            tablePanel[i][j] = new JPanel();
            tablePanel[i][j].setBackground(new Color((i+j)*18, 0, 0));
            imagePanel.add(tablePanel[i][j]);
        }

        // [+]Configure and show the frame:
        frame = new JFrame("GUI");
        frame.setContentPane(this.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        // Events
        slider_L_IR.addChangeListener(new Events(this._ctrl));
        slider_R_IR.addChangeListener(new Events(this._ctrl));
        slider_fusion.addChangeListener(new Events(this._ctrl));
        slider_motor.addChangeListener(new Events(this._ctrl));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void setData(byte data[]){
        this.data = data;
        System.out.print("setData");
        System.out.print(data+", ");
    }

    public void visible(boolean yesorno){
        frame.setVisible(yesorno);
    }


    //Accessors & mutators
    public JSlider getSlider_L_IR(){
        return slider_L_IR;
    }
    public void setValue_L_IR(int val){
        this.slider_L_IR.setValue(val);
    }
    public JSlider getSlider_R_IR(){
        return slider_R_IR;
    }
    public void setValue_R_IR(int val){
        this.slider_R_IR.setValue(val);
    }
    public JSlider getSlider_fusion(){
        return slider_fusion;
    }
    public void setValue_fusion(int val){
        this.slider_fusion.setValue(val);
    }
    public JSlider getSlider_motor(){
        return slider_motor;
    }
    public void setValue_motor(int val){
        this.slider_motor.setValue(val);
    }
    public void setValue_textPanel(String val, String pre){
        //this.textPane.setText(pre + val);
        //JTextPane textPane = new JTextPane();
        //this.textPane.setText( "original text" );
        StyledDocument doc = this.textPane.getStyledDocument();
        //  Add some text
        try
        {
            doc.insertString(doc.getLength(), pre + val +"\n", null);
        }
        catch(Exception e) { System.out.println(e); }
    }
}
