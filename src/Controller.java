/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 03/10/15
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */

public class Controller {
    private Model _model;
    private GUI _view;

    public Controller(){
        _view = new GUI(this);
        _model = new Model(this);
        this.start();
    }
    public void start(){
        _view.visible(true);
    }


    // Events Methods
    public void setSliderValue_L_IR(int val){
        getGUI().setValue_L_IR(val);
    }
    public void setSliderValue_R_IR(int val){
        getGUI().setValue_R_IR(val);
    }
    public void setSliderValue_fusion(int val){
        getGUI().setValue_fusion(val);
    }
    public void setSliderValue_motor(int val){
        getGUI().setValue_motor(val);
    }



    //Accessors & Mutators
    public GUI getGUI(){
        return _view;
    }
    public Model getModel(){
        return _model;
    }
    public SerialPort getSerialPort(){
        return _model.getSerialPort();
    }
}
