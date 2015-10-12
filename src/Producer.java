/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 11/10/15
 * Time: 11:59
 * To change this template use File | Settings | File Templates.
 */
//package com.journaldev.concurrency;

import java.util.concurrent.BlockingQueue;


public class Producer implements Runnable {

    private BlockingQueue<byte[]> queue;
    private Controller _ctrl;
    private Boolean stateFrame;

    public Producer(BlockingQueue<byte[]> q, Controller controller){
        this._ctrl = controller;
        this.stateFrame = true;
        this.queue = q;

    }
    @Override
    public synchronized void run() {
        //produce messages
        byte[] frame;
        while(stateFrame){
            frame = _ctrl.getModel().simulation_frame_color();
            //msg = _ctrl.getSerialPort().getData();
            try {
                queue.put(frame);
                //System.out.println("Produced "+msg.getMsg());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void setStateFrame(Boolean b){
        this.stateFrame = b;
        /*if (b == true)
            this.run();
          */
    }

}
