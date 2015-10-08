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
        while(true){
            /////// ALL TESTS
            /////
            /* Simulation of a data send to LIR slider and textPanel **/
            //int result = _model.simulation();
            //_view.setValue_L_IR(result);
            //_view.setValue_textPanel(Integer.toString(result), "> L_IR_setTo : ");
            /////

            /* Simulation of a data frame send to sliders and analyse by checkData method**/
            //byte[] result = _model.simulation_frame();
            //_model.checkData(result);
            /////

            /* Simulation of the final implementation => !! Doesn't works**/
            //System.out.println(_model.getSerialPort().getData());
            /////

            /* Simulation of a thermal data frame send to image panel and analyse by checkData method**/
            //byte[] result = _model.simulation_frame_color();
            //_model.changedColor(_view.get_tablePanel(), _view.get_imagePanel(), result);
            /////

            /* Simulation of a thermal data frame and sensor data frame send to sliders and image panel
             * and analyse by checkData method **/
            byte[] result = _model.simulation_frame_color();
            _model.checkData(result);
            byte[] res = _model.simulation_frame();
            _model.checkData(res);
            /////
        }
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
