import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: v.gaubert
 * Date: 13/10/15
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 * Sensors fusion combine infrared and thermal sensors.
 */
public class Tracking {
    // Kalman parameters
    private double q; //process noise covariance
    private double r; //measurement noise covariance
    private double x; //value
    private double p; //estimation error covariance
    private double k; //kalman gain
    private int tableSize;

    private Controller _ctrl;
    private MotorTrack _motorTrack;
    private IRTrack _irTrack;
    private ThermalTrack _thermalTrack;

    public Tracking(Controller controller) {
        this._ctrl = controller;
        _motorTrack = new MotorTrack(_ctrl);
        _irTrack = new IRTrack(_ctrl);
        _thermalTrack = new ThermalTrack(_ctrl);
        tableSize = 10;
    }

    public void sensorFusion (ArrayList<ArrayList> THdata){
        int moyenneLIR, moyenneRIR;
        double SDVLIR, SDVRIR, SDVTH;
        ArrayList arrayTH;
        int colTH[] = new int[tableSize];
        int moyTH[] = new int[16];
        double sdvTH[] = new double[16];
        //IR
        moyenneLIR = _ctrl.getModel().getMean(_irTrack.getArrayLIR())*100/1500;
        moyenneRIR = _ctrl.getModel().getMean(_irTrack.getArrayRIR())*100/1500;
        SDVLIR = _ctrl.getModel().getSDV(_irTrack.getArrayLIR(),moyenneLIR);
        SDVRIR = _ctrl.getModel().getSDV(_irTrack.getArrayRIR(),moyenneRIR);

        //TH
        for(int i = 0; i < THdata.size(); i++) {
            ArrayList<Integer> res = THdata.get(i);
            for(int j = 0; j < res.size(); j++)
                colTH[j] = res.get(j);
            moyTH[i] = _ctrl.getModel().getMean(colTH)*100/255;
            sdvTH[i] = _ctrl.getModel().getSDV(colTH, moyTH[i]);
        }
        SDVTH = _ctrl.getModel().getMean(sdvTH);
     }

    /**
     * kalman_init
     * Initialize the parameter for a kalman filter of one dimension
     * @param q   //process noise covariance
     * @param r   //measurement noise covariance
     * @param p   //estimation error covariance
     * @param initial_value
     */
    public void kalman_init(double q, double r, double p, double initial_value){
        this.q = q;
        this.r = r;
        this.p = p;
        x = initial_value;
    }

    /**
     * kalman_update
     * Update the value of the measurment vector
     * @param measurement
     */
    public void kalman_update(double measurement)
    {
        //prediction update
        //omit x = x
        p += q;

        //measurement update
        k = p / (p + r);
        x = x + k * (measurement - x);
        p = (1 - k) * p;
    }

    //Accessors & Mutators
    public MotorTrack get_motorTrack(){
        return _motorTrack;
    }

    public IRTrack get_irTrack(){
        return _irTrack;
    }

    public ThermalTrack get_thermalTrack(){
        return _thermalTrack;
    }
}
