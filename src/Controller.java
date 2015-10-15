import sun.awt.windows.ThemeReader;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 03/10/15
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */

public class Controller {

    private Model _model;
    private GUI _view;
    private  BlockingQueue<byte[]> queue;
    private  BlockingQueue<byte[]> qIR;
    private  BlockingQueue<byte[]> qTH;
    private  BlockingQueue<byte[]> qMO;
    private Producer producer;
    private Consumer consumer;
    private ConsumerIR consumerIR;
    private ConsumerTH consumerTH;
    private ConsumerMO consumerMO;
    private Thread p_thread;
    private Thread c_thread;
    private Thread c_IR_thread;
    private Thread c_TH_thread;
    private Thread c_MO_thread;
    private int i;

    public Controller(){
        _view = new GUI(this);
        this.start();
        _model = new Model(this);
    }
    public void start(){
        _view.visible(true);
        initQueueProducerConsumer();


        //while(true){
            /////// ALL TESTS
            /////
            /* Simulation of a data send to LIR slider and textPanel **/
            //int result = _model.simulation();
            //_view.setValue_L_IR(result);
            //_view.setValue_textPanel(Integer.toString(result), "> L_IR_setTo : ");
            /////

            /* Simulation of a data frame send to sliders and analyse by checkData method**/
            //byte[] result = _model.simulation_frame();
            //_model.checkData(result);
            /////

            /* Simulation of the final implementation => !! Doesn't works**/
            //System.out.println(_model.getSerialPort().getData());
            /////

            /* Simulation of a thermal data frame send to image panel and analyse by checkData method**/
            //byte[] result = _model.simulation_frame_color();
            //_model.changedColor(_view.get_tablePanel(), _view.get_imagePanel(), result);
            /////

            /* Simulation of a thermal data frame and sensor data frame send to sliders and image panel
             * and analyse by checkData method **/
             /*
             byte[] result = _model.simulation_frame_color();
            _model.checkData(result);
            byte[] res = _model.simulation_frame();
            _model.checkData(res);
            */

            /* Simulation serial port */

            /////
        //}
    }
    public void initQueueProducerConsumer(){
        // Queues
        this.queue = new ArrayBlockingQueue<>(21);
        this.qIR = new ArrayBlockingQueue<>(21);
        this.qTH = new ArrayBlockingQueue<>(21);
        this.qMO = new ArrayBlockingQueue<>(21);
        //receive init
        this.producer = new Producer(queue, this);
        this.consumer = new Consumer(queue, producer, this);
        this.p_thread = new Thread(this.producer);
        this.c_thread = new Thread(this.consumer);
        //thread sensors
        this.consumerIR = new ConsumerIR(qIR, this);
        this.c_IR_thread = new Thread(this.consumerIR);
        this.consumerTH = new ConsumerTH(qTH, this);
        this.c_TH_thread = new Thread(this.consumerTH);
        this.consumerMO = new ConsumerMO(qMO, this);
        this.c_MO_thread = new Thread(this.consumerMO);
        System.out.println("initQueueProducerConsumer OK");

    }
    public void startThread(){
        System.out.println("startButton() called");
        this.p_thread.start();
        this.c_thread.start();
        this.c_IR_thread.start();
        this.c_TH_thread.start();
        this.c_MO_thread.start();
        System.out.println("Producer and Consumer has been started");

    }
    public void startButton(){
        System.out.println(i+" :i");
        if(i == 0){
            startThread();
        }else{
            initQueueProducerConsumer();
            startThread();
        }
        i++;
    }
    public void stopButton(){
        producer.setStateFrame(false);
        consumer.setStateFrame(false);
    }
    public void cleanQueue(BlockingQueue<byte[]> q){
        while(q.isEmpty()){
            try {
                q.take();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        System.out.println("Queue is clean");
    }

    // Events Methods
    public void setSliderValue_L_IR(int val){
        getGUI().setValue_L_IR(val);
    }
    public void setSliderValue_R_IR(int val){
        getGUI().setValue_R_IR(val);
    }
    public void setSliderValue_fusion(int val){
        getGUI().setValue_fusion(val);
    }
    public void setSliderValue_motor(int val){
        getGUI().setValue_motor(val);
    }



    //Accessors & Mutators
    public GUI getGUI(){
        return _view;
    }
    public Model getModel(){
        return _model;
    }
    public SerialPort getSerialPort(){
        return _model.getSerialPort();
    }
    public BlockingQueue<byte[]> getQueue(){
        return this.queue;
    }
    public BlockingQueue<byte[]> getQIR(){
        return this.qIR;
    }
    public BlockingQueue<byte[]> getQTH(){
        return this.qTH;
    }
    public BlockingQueue<byte[]> getQMO(){
        return this.qMO;
    }
    public Thread getP_thread(){
        return p_thread;
    }
    public Thread getC_thread(){
        return c_thread;
    }
    public Producer getProducer(){
        return producer;
    }
}
