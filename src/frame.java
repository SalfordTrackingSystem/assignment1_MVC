public enum frame {
    // 36 => $     37 => %

    SENSOR_LIR("LIR", 36, 2, 37),
    SENSOR_RIR("RIR", 36, 1, 37),
    SENSOR_FUSION("FUS", 36, 3, 37),
    SENSOR_MOTOR("MOT", 36, 4, 37);

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
