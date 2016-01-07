/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 11/10/15
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 * Take data in the specify thermal queue
 */

import java.util.concurrent.BlockingQueue;

public class ConsumerTH implements Runnable{

    private BlockingQueue<int[]> queue;
    private Controller _ctrl;
    private Boolean stateFrame;

    public ConsumerTH(BlockingQueue<int[]> q, Controller controller){
        this._ctrl = controller;
        this.stateFrame = true;
        this.queue = q;
    }
    @Override
    public synchronized void run() {
        try{
            while(queue.isEmpty() && stateFrame){
                int[] frame = queue.take();
                this.handleFrame(frame);
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void handleFrame(int[] frame){
        String cmdTH;
        System.out.print("ConsumedTH : ");
        for (int i=0; i<21 ; i++)
            System.out.print(frame[i] + " ");
        System.out.println();
        _ctrl.getModel().applyOnGUI(protocol.SENSOR_THERMAL.NAME,0, frame);
        /* Tracking*/
        cmdTH = _ctrl.getModel().get_track().get_thermalTrack().info_tracking(frame);
        System.out.println(cmdTH);

        if (cmdTH=="right"){
            _ctrl.getProducer().setTrack(6);
        }else if (cmdTH=="left"){
            _ctrl.getProducer().setTrack(5);
        }else
            System.out.println("cmdTH pb with consumerTH");
    }
}
