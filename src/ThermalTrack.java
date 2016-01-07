import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: v.gaubert
 * Date: 13/10/15
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 * Method related to the tracking with thermal camera
 */
public class ThermalTrack {
    private ArrayList THdata;
    int i = 0; // compteur
    private Controller _ctrl;
    private ArrayList goodFrame;

    public ThermalTrack(Controller controller){
        this._ctrl = controller;
        THdata = new ArrayList();
        goodFrame = new ArrayList();
    }

    /**
     * rightOrLeftTH()
     * Return a side function of thermal data
     * @param THdata
     * @return
     */
    public String rightOrLeftTH(int[] THdata){
        String side = "idle";
        int marge =20;                         //Error of thermal sensor = 0.14° -> 14 in byte
        int cnt=0;
        int moyenneR, moyenneL,sumL=0,sumR=0;
        for(int i=2 ; i<18 ; i++){
            sumR += THdata[i];
            cnt++;
            if(cnt==2){
                i +=2;
                cnt =0;
            }
        }
        moyenneR = sumR/8;
        cnt =0;
        for(int i=4 ; i<18 ; i++){
            sumR += THdata[i];
            cnt++;
            if(cnt==2){
                i +=2;
                cnt =0;
            }
        }
        moyenneL = sumL/8;

        if (moyenneL>moyenneR + marge){
            side = "left";
        }
        else if(moyenneL<moyenneR - marge){
            side = "right";
        }
        return side;
    }

    /**
     * addArrayTH()
     * Get 10 thermal frame
     * @param frame
     */
    public void addArrayTH(int[] frame, int dim){
        for(int k=2; k<18; k++)
            goodFrame.add(frame[k]);
        THdata.add(goodFrame);
        if (THdata.size()>dim){
            _ctrl.getModel().getTracking().sensorFusion(THdata);
            i=0;
            THdata.clear();
            goodFrame.clear();
        }
        i++;
    }
    public ArrayList getArrayTH(){
        return THdata;
    }
}
