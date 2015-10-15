/**
 * Created with IntelliJ IDEA.
 * User: v.gaubert
 * Date: 13/10/15
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class IRTrack {

    //Attributes
    private int distanceR,distanceL,marge;

    private Controller _ctrl;

    //Constructor
    public IRTrack (Controller controller){
        this._ctrl = controller;
    }

    /**
     * rightOrLeft
     * Return a string right or left function of the data
     * @param data
     * @return
     */
    public String rightOrLeft(byte[] data){
        String side = "";
        marge = 100;                            //erreur capteur

        if (data[1] == frame.SENSOR_LIR.ID){
            distanceL = (int)data[2];
            distanceL <<= 8;
            distanceL |= data[3];
        }
        else if(data[1] == frame.SENSOR_RIR.ID){
            distanceR = (int)data[2];
            distanceR <<= 8;
            distanceR |= data[3];
        }
        if (distanceL < distanceR - marge){
            side = "left";
        }
        else if(distanceL>distanceR+marge){
            side = "right";
        }
        return side;
    }
}
