/**
 * Created with IntelliJ IDEA.
 * User: v.gaubert
 * Date: 13/10/15
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class IRTrack {

    //Attributes
    private int marge;
    private int[] distanceL = new int[10];
    private int[] distanceR = new int[10];
    private int i =0 ; //Compteur

    private Controller _ctrl;

    //Constructor
    public IRTrack (Controller controller){
        this._ctrl = controller;
    }

    /**
     * rightOrLeftIR
     * Return a string right or left function of the data
     * @param distanceL,distanceR
     * @return
     */
    public String rightOrLeftIR(int distanceR,int distanceL){
        String side = "idle";
        marge = 100;
        if (distanceR > 200 && distanceR< 1500 && distanceL > 200 && distanceL< 1500)           //Sensor data are into range
        {
            if (distanceL < distanceR - marge){
                side = "left";
            }
            else if(distanceL > distanceR + marge){
                side = "right";
            }
        }
        else if ((distanceL < 200 || distanceL > 1500) && distanceR > 200 && distanceR< 1500)   //IRleft out of range
        {
            side = "left";
        }
        else if ((distanceR < 200 || distanceR > 1500) && distanceL > 200 && distanceL< 1500)   //IRright out of range
        {
            side = "right";
        }
        else {
            side ="idle";
            System.out.println("Out of range");
        }

        return side;
    }

    /**
     *  addArrayIR()
     * Permet de stocker 10 mesures du capteur thermique pour pouvoir les traiter
     * @param distanceL
     * @param distanceR
     */
    public void addArrayIR(int distanceL, int distanceR){
        this.distanceL[i] = distanceL;
        this.distanceR[i] = distanceR;
        if (i > 10){

            i =0; // Remise à zéro du compteur quand le tableau est rempli
        }
        i++;
    }

    public int[] getArrayLIR(){
        return distanceL;
    }

    public int[] getArrayRIR(){
        return distanceR;
    }
}
