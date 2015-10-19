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

    private BlockingQueue<int[]> queue;
    private Controller _ctrl;
    private Boolean stateFrame;
    private int interCmd;
    private int interCmdFalse;

    public Producer(BlockingQueue<int[]> q, Controller controller){
        System.out.println("Producer created");
        this._ctrl = controller;
        this.stateFrame = true;
        this.queue = q;
        interCmd = 1;
        interCmdFalse = 1;

    }
    @Override
    public synchronized void run() {
        byte[] frameSigned;
        int[] frameUnsigned;
        try {
            while(stateFrame){
                /*** Real test ***/
                //_ctrl.getModel().sendCmd(interCmd);
                //frameSigned = _ctrl.getSerialPort().rxData();
                /****/
                /*** simulation test ***/
                frameSigned = _ctrl.getModel().getSensors_simulation(interCmd);
                /****/

                frameUnsigned = _ctrl.getModel().signedToUnsignedArray(frameSigned);
                if (handleFrame(frameUnsigned)){
                    frameUnsigned = takeGoodFrame(frameUnsigned);
                    queue.put(frameUnsigned);
                    interCmd+=1;
                    interCmdFalse = 1;
                    if(interCmd>4) interCmd = 1;
                }else{
                    interCmdFalse+=1;
                    if (interCmdFalse>5){
                        System.out.println("Problem cmd");
                        break;
                    }
                }
                /*** Real test ***/
                // _ctrl.getSerialPort().resetRxData();
                /****/
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public Boolean handleFrame(int[] f){  // return ack, nack, frameValidOrNot
        Boolean flag_ack = false;
        Boolean flag_nack = false;
        Boolean flag_return = false;
        Boolean flag_cmd = false;
        int debutFrame = 4000;
        for(int i=0; i<f.length ; i++)
            if(f[i]==protocol.SENSOR_LIR.SC){ // Looking for the start byte => #
                debutFrame = i;
                break;
            }
        if(debutFrame != 4000){
            if(f[debutFrame+1] == protocol.SENSOR_LIR.ACK){
                flag_ack = true;
            }else if(f[debutFrame+1] == protocol.SENSOR_LIR.NACK){
                flag_nack = true;
            }else{
                System.out.println("cmd non valid");
                flag_cmd = true;
            }
        }
        if(flag_cmd || flag_nack){
            return false;
        }else if (flag_ack){
            for(int j = (debutFrame+2) ; j < f.length ; j++){
                if(f[j]==frame.SENSOR_LIR.SB && f[j+20]==frame.SENSOR_LIR.EB){ // Looking for the start frame byte => $
                    flag_return = true;
                    break;
                }
            }
        }
        return flag_return;
    }
    public int[] takeGoodFrame(int[] f){
        int[] goodFrame = new int[21];
        for(int i=0 ; i<f.length ; i++){
            if(f[i] == frame.SENSOR_LIR.SB){
                for(int j=i; j<i+21 ; j++){
                    //System.out.print(j+" ");
                    goodFrame[j-i] = f[j];
                }
                break;
            }
        }
        return goodFrame;
    }
    public Boolean getStateFrame(){
        return this.stateFrame;
    }
    public void setStateFrame(Boolean b){
        this.stateFrame = b;
    }
}
