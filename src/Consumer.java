/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 11/10/15
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
//package com.journaldev.concurrency;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    private BlockingQueue<byte[]> queue;
    private Controller _ctrl;

    public Consumer(BlockingQueue<byte[]> q, Controller controller){
        this._ctrl = controller;
        this.queue = q;
    }

    @Override
    public void run() {
        try{
            //Message msg;
            //consuming messages until exit message is received
            //while((msg = queue.take()).getMsg() !="exit"){
            System.out.println("1ok");
            while(queue.isEmpty()){
                System.out.println("2ok");
                Thread.sleep(10);
                byte[] res = queue.take();
                System.out.print("Consumed : ");
                for (int i=0; i<21 ; i++){
                    System.out.print(res[i] + " ");
                }
                System.out.println();

            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
