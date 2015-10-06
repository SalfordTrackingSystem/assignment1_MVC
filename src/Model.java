/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 03/10/15
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */


public class Model {
    private Controller _ctrl;
    private SerialPort _sp;



        public Model(Controller controller){
        this._ctrl = controller;
        _sp = new SerialPort(_ctrl);
        _sp.initialize();
    }

    //21 octects have to be analyse
    //////
    // Check SB byte (The same for all frame=> $)
    // Check ID byte (Different for all sensors)
    // If ID is valid then take the number of data byte "nbByte" (!!not implemented) and compare it to the CS sensor
    // If CS is valid, variable flag take the name of the sensor frame
    // Same thing for all sensors..
    //////
    /*
    public void checkData(Byte[] data){
        String flag = "";
        if (data[0].equals(frame.SENSOR_FUSION.SB)){
            if (data[1] == frame.SENSOR_LIR.ID){
                if (nbByte == frame.SENSOR_LIR.CS);
                    flag = frame.SENSOR_LIR.NAME;
            }
            else if (data[1] == frame.SENSOR_RIR.ID){
                if (nbByte = frame.SENSOR_RIR.CS);
                    flag = frame.SENSOR_RIR.NAME;
            }
            else if (data[1] == frame.SENSOR_FUSION.ID){
                if (nbByte = frame.SENSOR_FUSION.CS);
                    flag = frame.SENSOR_FUSION.NAME;
            }
            else if (data[1] == frame.SENSOR_MOTOR.ID){
                if (nbByte = frame.SENSOR_MOTOR.CS);
                    flag = frame.SENSOR_MOTOR.NAME;
            }
            else{
                System.out.println("ID not valid");
            }
        }
        else{
            System.out.println("No data");
        }
        ///////
        // If the flag have been changed, check the EB
        // Print the CN of the sensor frame and valid the frame
        // Same thing for all sensors
        ///////
        if (flag != ""){
            if (data[data.length].toString() == frame.SENSOR_LIR.EB){
                System.out.println(flag + " | CN : " + data[data.length-1]);
            }
        }
    }  */

    public SerialPort getSerialPort(){
        return _sp;
    }
}
