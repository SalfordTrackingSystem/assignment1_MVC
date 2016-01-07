import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: s.dossantos
 * Date: 03/10/15
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 * The controller handle the model class with method and view class GUI. (See MVC architecture)
 */

public class Controller {
    // Initialization of Model and View class
    private Model _model;
    private GUI _view;
    // Initialization of queues, thread, producer, consumers class variable
    private  BlockingQueue<int[]> queue;
    private  BlockingQueue<int[]> qIR;
    private  BlockingQueue<int[]> qTH;
    private  BlockingQueue<int[]> qMO;
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
        // Instanciation of Model and View class
        _view = new GUI(this);
        this.start();
        _model = new Model(this);
    }

    public void start(){
        _view.visible(true);           // show the GUI
        initQueueProducerConsumer();   // Instanciation of Model and View class
    }

    public void initQueueProducerConsumer(){
        // Queues
        this.queue = new ArrayBlockingQueue<>(21);
        this.qIR = new ArrayBlockingQueue<>(21);
        this.qTH = new ArrayBlockingQueue<>(21);
        this.qMO = new ArrayBlockingQueue<>(21);
        // "Receive data" initialization => producer, consumer and thread
        this.producer = new Producer(queue, this);
        this.consumer = new Consumer(queue, producer, this);
        this.p_thread = new Thread(this.producer);
        this.c_thread = new Thread(this.consumer);
        // Consumers and threads sensors
        this.consumerIR = new ConsumerIR(qIR, this);
        this.c_IR_thread = new Thread(this.consumerIR);
        this.consumerTH = new ConsumerTH(qTH, this);
        this.c_TH_thread = new Thread(this.consumerTH);
        this.consumerMO = new ConsumerMO(qMO, this);
        this.c_MO_thread = new Thread(this.consumerMO);
        System.out.println("initQueueProducerConsumer OK");
    }

    /**
     * Start all the different thread.
     * Handle the stop and start acquisition in the GUI.
     */
    public void startThread(){
        System.out.println("startButton() called");
        this.p_thread.start();
        this.c_thread.start();
        this.c_IR_thread.start();
        this.c_TH_thread.start();
        this.c_MO_thread.start();
        System.out.println("Producer and Consumer has been started");
    }

    /**
     *  Handle stop and start data acquisition in the GUI
     */
    public void startButton(){
        if(i == 0){
            startThread();
        }else{
            initQueueProducerConsumer();
            startThread();
        }
    }

    /**
     *  Stop data acquisition.
     */
    public void stopButton(){
        producer.setStateFrame(false);
        consumer.setStateFrame(false);
    }

    /**
     * Clean the queue before restarting the app.
     * @param q : queue
     */
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

    // Events Methods Mutator
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
    public BlockingQueue<int[]> getQueue(){
        return this.queue;
    }
    public BlockingQueue<int[]> getQIR(){
        return this.qIR;
    }
    public BlockingQueue<int[]> getQTH(){
        return this.qTH;
    }
    public BlockingQueue<int[]> getQMO(){
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
