/*
* Sensor enumeration to analyse frame.
* Help us to handle the communication protocol.
*/
public enum frame {
    // 36 => $     37 => %      ID => in byte 1 = 49
    SENSOR_RIR("RIR", 36, 1, 37),     // position sensor right
    SENSOR_LIR("LIR", 36, 2, 37),     // position sensor left
    SENSOR_THERMAL("THE", 36, 3, 37), // thermal sensor
    SENSOR_MOTOR("MOT", 36, 4, 37);   // motor position

    public String NAME = "";
    public int SB = 0;
    public int ID = 0;
    public int EB = 0;

    frame(String NAME, int SB, int ID, int EB) {
        this.NAME = NAME;
        this.SB = SB;
        this.ID = ID;
        this.EB = EB;
    }
}
