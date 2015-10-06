public enum frame {
    // 36 => $     37 => %
    SENSOR_LIR("LIR", 36, 2, 16, 37),
    SENSOR_RIR("RIR", 36, 1, 8, 37),
    SENSOR_FUSION("FUS", 36, 3, 8, 37),
    SENSOR_MOTOR("MOT", 36, 4, 8, 37);

    public String NAME = "";
    public int SB = 0;
    public int ID = 0;
    public int CS = 0;
    public int EB = 0;

    frame(String NAME, int SB, int ID, int CS, int EB) {
        this.NAME = NAME;
        this.SB = SB;
        this.ID = ID;
        this.CS = CS;
        this.EB = EB;
    }
}
