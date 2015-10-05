import java.util.Random;

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

    public int simulation(){
        Random rd = new Random();
        int n=rd.nextInt(100)+1;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //wait(1000);
        System.out.println(n);
        return n;
    }

    public SerialPort getSerialPort(){
        return _sp;
    }
}
