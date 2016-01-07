

/**
 * Created with IntelliJ IDEA.
 * User: v.gaubert, a.moufounda, S.dossantos
 * Date: 13/10/15
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 * Method related to the tracking with thermal camera
 */

public class ThermalTrack {
    private Controller _ctrl;
    private int[][] matrix;
    private int result;
    private int sensitivity;
    private int[][] matrix_leftSide;
    private int[][] matrix_rightSide;
    private double diff_meanLeftRight;

    /**
     * Method     : Constructor of Model class
     * Parameters : Controller
     * Returns    : Nothing
     * Notes      : Initialize ThermalTrack class
     **/
    public ThermalTrack(Controller controller){
        this._ctrl = controller;
        matrix = new int[4][4];
        matrix_leftSide = new int[4][2];
        matrix_rightSide = new int[4][2];
        result = 0;
        sensitivity = 50;
        diff_meanLeftRight = 0;
    }

    /**
     * Method     : isSomeoneThere
     * Parameters : None
     * Returns    : A number that indicate ether someone is in front of the camera or not
     * Notes      :
     **/
    public int isSomeoneThere()
    {
        double toleranceMin;
        double toleranceMax;
        double mean;
        double standardDev;
        int cnt;

        mean = getMean(matrix);
        standardDev = getStandardDev(matrix);
        toleranceMin = mean - standardDev;
        toleranceMax =  mean + standardDev;

        cnt = 0;
        for(int i=0 ; i< matrix.length   ; i++)
            for(int j=0 ; j< matrix[i].length; j++)
                if ((matrix[i][j] > toleranceMin) && (matrix[i][j] < toleranceMax))
                    cnt++;

        if (cnt >= (0.9 * (matrix.length * matrix[0].length))) //!< matrix.length * matrix[0].length = nbLine * nbRow
                                //!< If the data dispersion is small
        {
            if (mean > 255/2)   //!< If the whole pixels are red
                return 1;		//!< Ok, target focused
            else
                return 0; 	    //!< Nobody's there
        }
                                //!< If 90% of the values are ranging in mean ± standard deviation
        else
            return 2;	        //!< Strong dispersion : someone's there
    }

    /**
     * Method     : info_tracking
     * Parameters : the frame given by the thermal camera
     * Returns    : An indication of the rotation order
     **/
    public String info_tracking(int[] frame)
    {
        int val = 0;
        double mean_leftSide;
        double mean_rightSide;

        for(int i=0 ; i<4 ; i++)                 //!< Filling the matrix with the different color values of each pixel
        {
            for(int j=0 ; j<4 ; j++)
            {
                if (frame[17-i*4-j] > 255 || frame[17-i*4-j]<0)
                    val=128;
                else
                    val = 255 - frame[17-i*4-j];
                matrix[i][j] = val;
            }
        }

        result = isSomeoneThere();               //!< is someone in front of the camera ?
        if(result == 1)
            return "tracked";
        else if(result == 2)                     //!< is the person to the right or the left ?
        {
                                                 //!<Filling the matrix left and right with the walue of the left side pixels
                                                 //!< and the right side pixels
            for(int i=0 ; i<matrix_leftSide.length   ; i++)
            {
                for(int j=0 ; j<matrix_leftSide[i].length ; j++)
                {
                    matrix_leftSide[i][j] = matrix[i][j];
                    matrix_rightSide[i][j] = matrix[i][j+2];
                }
            }

            mean_leftSide = getMean(matrix_leftSide);
            mean_rightSide = getMean(matrix_rightSide);
            diff_meanLeftRight = Math.abs(mean_leftSide - mean_rightSide); //!< Calculation of the difference between the lef and right mean

            if(diff_meanLeftRight > sensitivity)
            {
                if (mean_leftSide < mean_rightSide)
                    return "left";
                else if (mean_leftSide > mean_rightSide)
                    return "right";
                else
                    return "tracked";
            }
        }
        else
            return "empty";

        return "empty";
    }

    /**
     * getMean()
     * Renvoie la moyenne d'un tableau
     * @param data
     * @return
     */
    public double getMean (int[][] data)
    {
        double mean = 0;
        int sum = 0;

        for (int i = 0; i< data.length; i++)
            for(int j = 0 ; j < data[i].length ; j++)
                sum += data[i][j];
        mean = sum/(data.length * data[0].length);
        return mean;
    }

    /**
     * getSDV()
     * Send back the Standard Deviation
     * * @param data
     * @return
     */
    public double getStandardDev(int[][] data)
    {
        double standardDev;
        double sum = 0;
        double mean = getMean(data);

        for (int i = 0; i< data.length; i++)
            for(int j = 0 ; j < data[i].length ; j++)
                sum += (data[i][j] - mean)*(data[i][j] - mean);
        standardDev = Math.sqrt(sum /(data.length * data[0].length));
        return standardDev;
    }

    private double getDiff_meanLeftRight()
    {
        return this.diff_meanLeftRight;
    }
}