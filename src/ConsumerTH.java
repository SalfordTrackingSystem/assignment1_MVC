/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 11/10/15
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
//package com.journaldev.concurrency;

import sun.org.mozilla.javascript.internal.ast.ReturnStatement;

import java.util.concurrent.BlockingQueue;


public class ConsumerTH implements Runnable{

    private BlockingQueue<byte[]> queue;
    private Controller _ctrl;
    private Boolean stateFrame;

    public static final char OUT_OF_RANGE = 0;
    public static final char SMALL_ROTATION_LEFT = 1;
    public static final char SMALL_ROTATION_RIGHT = 2;
    public static final char LARGE_ROTATION_LEFT = 3;
    public static final char LARGE_ROTATION_RIGHT = 4;
    public static final char TARGET_FOCUSED = 5;

    public ConsumerTH(BlockingQueue<byte[]> q, Controller controller){
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
                //fromFrameToMatrice(frame);
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void handleFrame(byte[] frame){
        String cmdTH;
        System.out.print("Consumed : ");
        for (int i=0; i<21 ; i++)
            System.out.print(frame[i] + " ");
        System.out.println();
        /* Tracking */
        cmdTH = _ctrl.getModel().get_track().get_thermalTrack().info_tracking(frame);
        _ctrl.getModel().applyOnGUI("THE",0, frame);
        if (cmdTH=="right"||cmdTH=="left"){
            _ctrl.getModel().cmdToSend(cmdTH);
        }
    }
}
