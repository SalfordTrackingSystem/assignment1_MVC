public enum frame {
    // 36 => $     37 => %

    SENSOR_LIR("LIR", 36, 2, 37),    // position sensor left
    SENSOR_RIR("RIR", 36, 1, 37),    // position sensor right
    SENSOR_THERMAL("THE", 36, 3, 37), // thermal sensor
    SENSOR_MOTOR("MOT", 36, 4, 37);  // position sensor left

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
