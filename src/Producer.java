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

    public Producer(BlockingQueue<byte[]> q, Controller controller){
        this._ctrl = controller;
        this.queue=q;
    }
    @Override
    public void run() {
        //produce messages
        byte[] msg;
        for(int i=0; i<100; i++){
            msg = _ctrl.getModel().simulation_frame_color();

            //msg = _ctrl.getSerialPort().getData();

            try {
                //Thread.sleep(i);
                //System.out.println(msg);
                queue.put(msg);
                //System.out.println("Produced "+msg.getMsg());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //adding exit message
        //Message msg = new Message("exit");
        /*
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } */
    }

}
