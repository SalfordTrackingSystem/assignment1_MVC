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
        System.out.println("Producer created");
        this._ctrl = controller;
        this.stateFrame = true;
        this.queue = q;
    }
    @Override
    public synchronized void run() {
        byte[] frame;
        try {
            while(stateFrame){
                //frame = _ctrl.getModel().simulation_frame_color();
                frame = _ctrl.getSerialPort().rxData();
                queue.put(frame);
                _ctrl.getSerialPort().resetRxData();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Boolean getStateFrame(){
        return this.stateFrame;
    }
    public void setStateFrame(Boolean b){
        this.stateFrame = b;
    }
}
