import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Dos Santos Sébastien
 **/
   //TEST
public class Run
{
   /**
    * Method     : Run::main()
    * Purpose    : Default main method used for launch the interface.
    * Parameters : - args : Initialization parameters.
    * Returns    : Nothing.
    * Notes      : Implement the Controller
    **/

    public static void main(String[] args)
    {

        Controller intcont = new Controller();
        //Creating BlockingQueue of size 10
        BlockingQueue<byte[]> queue = new ArrayBlockingQueue<>(10);
        Producer producer = new Producer(queue, intcont);
        Consumer consumer = new Consumer(queue, intcont);
        //starting producer to produce messages in queue
        new Thread(producer).start();
        //starting consumer to consume messages from queue
        new Thread(consumer).start();
        System.out.println("Producer and Consumer has been started");
    }
}
