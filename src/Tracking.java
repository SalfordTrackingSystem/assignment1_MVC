/**
 * Created with IntelliJ IDEA.
 * User: v.gaubert
 * Date: 13/10/15
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class Tracking {

    //Attributs
    private Controller _ctrl;
    private MotorTrack _motorTrack;
    private IRTrack _irTrack;
    private ThermalTrack _thermalTrack;

    //Constructeur
    public Tracking(Controller controller) {
        this._ctrl = controller;
        _motorTrack = new MotorTrack(_ctrl);
        _irTrack = new IRTrack(_ctrl);
        _thermalTrack = new ThermalTrack(_ctrl);
    }

    //Accesseurs & Mutateurs
    public MotorTrack get_motorTrack(){
        return _motorTrack;
    }

    public IRTrack get_irTrack(){
        return _irTrack;
    }

    public ThermalTrack get_thermalTrackck(){
        return _thermalTrack;
    }
}
