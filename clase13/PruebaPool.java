import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class PruebaPool {
  public static void main(String[] args) {
    int MAX_T = (args.length > 0) ? Integer.parseInt(args[0]) : 1;
    
    ExecutorService pool = Executors.newFixedThreadPool(MAX_T);

    Runnable task1 = new Task(0);
    Runnable task2 = new Task(1);
    Runnable task3 = new Task(2);
    Runnable task4 = new Task(3);
    Runnable task5 = new Task(4);

    pool.execute(task1);
    pool.execute(task2);
    pool.execute(task3);
    pool.execute(task4);
    pool.execute(task5);

    pool.shutdown();
  }

}