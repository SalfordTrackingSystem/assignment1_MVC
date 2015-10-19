import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created with IntelliJ IDEA.
 * User: v.gaubert
 * Date: 13/10/15
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class Tracking {

    //Attributes
    //kalman parameter
    private double q; //process noise covariance
    private double r; //measurement noise covariance
    private double x; //value
    private double p; //estimation error covariance
    private double k; //kalman gain

    private Controller _ctrl;
    private MotorTrack _motorTrack;
    private IRTrack _irTrack;
    private ThermalTrack _thermalTrack;

    //Constructor
    public Tracking(Controller controller) {
        this._ctrl = controller;
        _motorTrack = new MotorTrack(_ctrl);
        _irTrack = new IRTrack(_ctrl);
        _thermalTrack = new ThermalTrack(_ctrl);
    }


    /**
     * SensorFusion
     * Decide de la commande moteur en fonction de la fiabilité des capteur
     */
      public void sensorFusion (String sideTH, String sideIR){
        int moyenneLIR,moyenneRIR;
        double SDVLIR,SDVRIR;
        int[][] arrayTH;
        int colTH[] = new int[10];
        int moyTH[] = new int[16];
        double SDVTH[] = new double[16];

          moyenneLIR = _ctrl.getModel().getMean(_irTrack.getArrayLIR());
          moyenneRIR = _ctrl.getModel().getMean(_irTrack.getArrayRIR());
          arrayTH = _thermalTrack.getArrayTH();

          for(int i=0; i<=15;i++) {                        //Calcul la moyenne et SDV de chaque colonne du tableau arrayTH (chaque pixel)
              for (int j=0; i<=9; j++){
                colTH[j] =   arrayTH[i][j];
              }
              moyTH[i] = _ctrl.getModel().getMean(colTH);
              SDVTH[i] = _ctrl.getModel().getSDV(colTH,moyTH[i]);
          }
          SDVLIR = _ctrl.getModel().getSDV(_irTrack.getArrayLIR(),moyenneLIR);
          SDVRIR = _ctrl.getModel().getSDV(_irTrack.getArrayRIR(),moyenneRIR);

         /* if(SDVLIR < SDVTH && SDVRIR < SDVTH){
             _ctrl.getModel().cmdToSend(sideTH);
          }
          else {
              _ctrl.getModel().cmdToSend(sideIR);
          }
            */
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

    //Accusers & Mutters
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
