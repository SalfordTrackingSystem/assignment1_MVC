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
     * @param distanceL,distanceR
     * @return
     */
    public String rightOrLeftIR(int distanceR,int distanceL){
        String side = "idle";
        marge = 100;
        if (distanceR > 200 && distanceR< 1500 && distanceL > 200 && distanceL< 1500){            //Test if sensor data are out of range

            if (distanceL < distanceR - marge){
                side = "left";
            }
            else if(distanceL > distanceR + marge){
                side = "right";
            }
        }
        else {
            System.out.println("Out of range");
        }
        return side;
    }
}
