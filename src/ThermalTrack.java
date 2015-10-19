

/**
 * Created with IntelliJ IDEA.
 * User: v.gaubert
 * Date: 13/10/15
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class ThermalTrack {

    //Attributes
    private Controller _ctrl;

    //Constructor
    public ThermalTrack(Controller controller){
        this._ctrl = controller;
    }

    /**
     * rightOrLeftTH()
     * Return a side function of thermal data
     * @param THdata
     * @return
     */
    public String rightOrLeftTH(byte[] THdata){
        String side = "idle";
        int marge =20;                         //Error of thermal sensor = 0.14° -> 14 in byte
        int cnt=0;
        int moyenneR, moyenneL,sumL=0,sumR=0;
        for(int i=0 ; i<16 ; i++){             // Boucle permettant de faire la somme des valeurs des pixels de gauche
            sumL += THdata[i];
            cnt++;
            if(cnt==2){
                i +=2;
                cnt =0;
            }
        }
        moyenneL = sumL/8;
        cnt =0;
        for(int i=2 ; i<16 ; i++){            // Boucle permettant de faire la somme des valeurs des pixels de droite
            sumR += THdata[i];
            cnt++;
            if(cnt==2){
                i +=2;
                cnt =0;
            }
        }
        moyenneR = sumR/8;

        //Compare les deux moyennes et en déduire une direction pour commander le moteur
        if (moyenneL>moyenneR + marge){
            side = "left";
        }
        else if(moyenneL<moyenneR - marge){
            side = "right";
        }
        return side;
    }
}
