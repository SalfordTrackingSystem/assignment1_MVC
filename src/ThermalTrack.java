

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

    public int isSomeoneThere(int[][] data, int nbLigne, int nbColonne)
    {
        double toleranceMin;
        double toleranceMax;
        int cnt = 0;
        double moyenne;
        double ecartType;

        moyenne = getMean(data,nbLigne, nbColonne);
        ecartType = getSDV(data, nbLigne, nbColonne,moyenne);

        toleranceMin = moyenne - ecartType;
        toleranceMax = moyenne + ecartType;

        for(int i=0 ; i<nbLigne   ; i++)
            for(int j=0 ; j<nbColonne ; j++)
                if ((data[i][j] > toleranceMin) && (data[i][j] < toleranceMax))
                    cnt++;

        if (cnt >= (0.9 * nbLigne * nbColonne)) //Si la dispersion est faible..
        {
            if (moyenne > 255/2) // Et si les pixels sont allumés
                return 1;		// Ok, target focused
            else
                return 0; 	//Il n'y a personne
        }
        //!< Si x% de valeurs sont comprise dans mon intervalle moyenne ±  ecart Type
        else
            return 2;	//La dispersion est forte : on cherche quelqu'un
    }

    public String info_tracking(int[] frame)
    {
        int[][] matrice = new int[4][4];
        int val = 0;
        int result;
        int sensitivity = 0;
        double moyenne_gauche = 0;
        double moyenne_droite = 0;
        int[][] matrice_laterale_gauche = new int[4][2];
        int[][] matrice_laterale_droite = new int[4][2];

        for(int i=0 ; i<4   ; i++)
            for(int j=0 ; j<4 ; j++)
            {
                val = (i + j) * frame[i*4+j+2];
                if (val > 255)
                    val=255;

                /*orientation classique*/
                matrice[i][j] = val;
                System.out.print(Integer.toString(val));
            }
        System.out.println();



        //!< Test sur la matrice generale
        result = isSomeoneThere(matrice, 4,4);

        if(result == 1)
        {
            return "tracked";
        }
        else if(result == 2)
        //!< Test sur les bandes laterales
        {
            for(int i=0 ; i<4   ; i++)
                for(int j=0 ; j<2 ; j++)
                    matrice_laterale_gauche[i][j] = matrice[i][j];

            for(int i=0 ; i<4   ; i++)
                for(int j=2 ; j<4 ; j++)
                    matrice_laterale_droite[i][j] = matrice[i][j];

            moyenne_gauche = getMean(matrice_laterale_gauche,4,2) + sensitivity;
            moyenne_droite = getMean(matrice_laterale_droite,4,2) + sensitivity;

            if ( moyenne_gauche < moyenne_droite)
            {
                return "left";
            }

            else if ( moyenne_gauche > moyenne_droite)
            {
                return "right";
            }
            else
            {
                return "tracked";
            }
        }
        else
            return "vide";
    }

    /**
     * getMean()
     * Renvoie la moyenne d'un tableau
     * @param data
     * @return
     */
    public double getMean (int[][] data, int nbLigne, int nbColonne)
    {
        double mean = 0;
        int sum = 0;
        for (int i = 0; i< nbLigne; i++)
            for(int j = 0 ; i < nbColonne ; j++)
                sum += data[i][j];
        mean = sum/(nbLigne * nbColonne);
        return mean;
    }

    /**
     * getSDV()
     * Renvoie l'écart type
     * * @param data
     * @param mean
     * @return
     */
    public double getSDV (int[][] data, int nbLigne, int nbColonne, double mean)
    {
        double SDV;
        double X = 0;
        for(int i = 0; i< nbLigne; i++)
            for (int j = 0 ; j < nbColonne ; j++)
                X += (data[i][j] - mean)*(data[i][j] - mean);
        SDV = Math.sqrt(X /(nbLigne * nbColonne));
        return SDV;
    }
}