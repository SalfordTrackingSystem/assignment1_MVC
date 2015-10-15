/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 11/10/15
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
//package com.journaldev.concurrency;

import java.util.concurrent.BlockingQueue;

public class ConsumerMO implements Runnable{

    private BlockingQueue<byte[]> queue;
    private Controller _ctrl;
    private Boolean stateFrame;

    public ConsumerMO(BlockingQueue<byte[]> q, Controller controller){
        this._ctrl = controller;
        this.stateFrame = true;
        this.queue = q;
    }
    @Override
    public synchronized void run() {
        try{
            while(queue.isEmpty() && stateFrame){
                byte[] frame = queue.take();
                this.handleFrame(frame);
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void handleFrame(byte[] frame){
        System.out.print("Consumed : ");
        for (int i=0; i<21 ; i++)
            System.out.print(frame[i] + " ");
        System.out.println();
        ////////////////
        _ctrl.getModel().applyOnGUI("MOT", frame);
    }
}
