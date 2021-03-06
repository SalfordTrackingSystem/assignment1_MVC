/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 11/10/15
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 * Take data in the specify infrared queue
 */

import java.util.concurrent.BlockingQueue;

public class ConsumerIR implements Runnable{

    private BlockingQueue<int[]> queue;
    private Controller _ctrl;
    private Boolean stateFrame;
    private int distanceL;
    private int distanceR;

    public ConsumerIR(BlockingQueue<int[]> q, Controller controller){
        this._ctrl = controller;
        this.stateFrame = true;
        this.queue = q;
        distanceL = 0;
        distanceR = 0;
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
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Transform data frame into distance get by infrared sensors.
     * @param data
     */
    private void handleFrame(int[] data){
        String cmd;
        System.out.print("ConsumedIR : ");
        for (int i=0; i<21 ; i++)
            System.out.print(data[i] + " ");
        System.out.println();

        if( data[1] == frame.SENSOR_LIR.ID)         //Stock distanceL and send it to GUI
        {
            distanceL = (int)data[3];
            distanceL <<= 8;
            distanceL |= data[2];
            _ctrl.getModel().applyOnGUI(frame.SENSOR_LIR.NAME, distanceL, data);
        }
        else if (data[1] == frame.SENSOR_RIR.ID)    //Stock distanceR and send it to GUI
        {
            distanceR = (int)data[3];
            distanceR<<= 8;
            distanceR |= data[2];
            _ctrl.getModel().applyOnGUI(frame.SENSOR_RIR.NAME, distanceR, data);
        }
    }
}
