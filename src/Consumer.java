/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 11/10/15
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 * Take data put in the main queue by the producer
 */

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    private BlockingQueue<int[]> queue;
    private Controller _ctrl;
    private Boolean stateFrame;

    public Consumer(BlockingQueue<int[]> q, Producer producer,  Controller controller){
        this._ctrl = controller;
        this.stateFrame = true;
        this.queue = q;
    }

    /**
     * Background method witch take data in queue and check the frame.
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

    // Accessors & Mutators
    public void setStateFrame(Boolean b){
        this.stateFrame = b;
    }

    public Boolean getStateFrame(){
        return this.stateFrame;
    }

    /**
     * Display the data frame in the console.
     * @param frame
     */
    private void handleFrame(int[] frame){
        System.out.print("Consumed : ");
        for (int i=0; i<21 ; i++){
                System.out.print(frame[i] + " ");
            }
        System.out.println();
        _ctrl.getModel().checkData(frame);
    }
}

