/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 16/10/15
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public enum protocol {
    SENSOR_RIR("RIR", '#', '1', 'A', 'N'),
    SENSOR_LIR("LIR",  '#', '2', 'A', 'N'),
    SENSOR_THERMAL("THE",  '#', '3', 'A', 'N'),
    SENSOR_MOTOR_GET("MOTG",  '#', '4', 'A', 'N'),
    SENSOR_MOTOR_SET("MOTS",  '#', '5', 'A', 'N');

    public String NAME = "";
    public char SC;
    public char ID;
    public char ACK;
    public char NACK;

    protocol(String NAME, char SC, char ID, char ACK, char NACK) {
        this.NAME = NAME;
        this.SC = SC;
        this.ID = ID;
        this.ACK = ACK;
        this.NACK = NACK;
    }
}
