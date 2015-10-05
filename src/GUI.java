import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.awt.event.ActionListener;
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
    private JPanel tablePanel[][];


    private JFrame frame;
    private byte data[];

    private Controller _ctrl;




    public GUI(Controller controller)
    {
        this._ctrl = controller;
        start();


         /*
        slider_L_IR.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                //System.out.print("L_IR");
                System.out.print(slider_L_IR.getValue());
                System.out.print("\n");
                textPane.setText("L_IR : " + Integer.toString(slider_L_IR.getValue()));
            }
        });
        slider_R_IR.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                //System.out.print("R_IR");
                System.out.print(slider_R_IR.getValue());
                System.out.print("\n");
                textPane.setText("R_IR : " + Integer.toString(slider_R_IR.getValue()));
            }
        });
        slider_fusion.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                //System.out.print("fusion");
                System.out.print(slider_fusion.getValue());
                System.out.print("\n");
            }
        });
        slider_motor.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
                //System.out.print("motor");
                System.out.print(slider_motor.getValue());
                System.out.print("\n");
            }
        });
        */
    }

   /**
    * Method     : GUI::start()
    * Purpose    : Configure and show the graphical interface.
    * Parameters : None.
    * Returns    : Nothing.
    * Notes      : None.
    **/
    public void start()
    {
        // [+]Initialisation of the camera's picture:
        tablePanel = new JPanel[IMAGE_LINE][IMAGE_COLUMN];
        //imagePanel = new JPanel();
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
        //frame.setVisible(true);

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
        this.textPane.setText(pre + val);
    }
}
