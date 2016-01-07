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

    /**
     * Consume the queue if the frame is valid.
     */
    @Override
    public synchronized void run() {
        try{
            while(queue.isEmpty() && stateFrame){
                int[] frame = queue.take();
                this.handleFrame(frame);
                _ctrl.getModel().getTracking().get_thermalTrack().addArrayTH(frame,9);
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void handleFrame(int[] frame){
        String cmd;
        System.out.print("Consumed : ");
        for (int i=0; i<21 ; i++)
            System.out.print(frame[i] + " ");
        System.out.println();
        _ctrl.getModel().applyOnGUI("THE",0, frame);
    }
}
