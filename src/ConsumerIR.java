/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 11/10/15
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
//package com.journaldev.concurrency;

import java.util.concurrent.BlockingQueue;

public class ConsumerIR implements Runnable{

    private BlockingQueue<byte[]> queue;
    private Controller _ctrl;
    private Boolean stateFrame;
    private String frameStop;

    public ConsumerIR(BlockingQueue<byte[]> q, Controller controller){
        this._ctrl = controller;
        this.stateFrame = false;
        this.frameStop = "";
        this.queue = q;
    }

    @Override
    public synchronized void run() {
        try{
            while(queue.isEmpty() && !stateFrame){
                byte[] frame = queue.take();
                System.out.print("Consumed_IR : ");
                for(int i=0; i<4 ; i++){
                    this.frameStop += (char)frame[i];
                    System.out.print(frame[i]+" ");
                }
                if(this.frameStop.equals("exit")){
                    stateFrame = true;
                }else{
                    // Print all frames in the console
                    this.frameStop = "";
                    for (int i=4; i<21 ; i++){
                        System.out.print(frame[i] + " ");
                    }
                    System.out.println();
                    ////////////////

                }
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
