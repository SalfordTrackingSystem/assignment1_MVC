/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 05/10/15
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public enum frame {
    SENSOR_LIR("LIR", "$", 1, 16, "%"),
    SENSOR_RIR("RIR", "$", 2, 8, "%"),
    SENSOR_FUSION("FUS", "$", 3, 8, "%"),
    SENSOR_MOTOR("MOT", "$", 4, 8, "%");

    public String NAME = "";
    public String SB = "";
    public int ID = 0;
    public int CS = 0;
    public String EB = "";

    frame(String NAME, String SB, int ID, int CS, String EB) {
        this.NAME = NAME;
        this.SB = SB;
        this.ID = ID;
        this.CS = CS;
        this.EB = EB;
    }
}
