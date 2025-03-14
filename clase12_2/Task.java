import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Task implements Runnable {
    private String name;
    private ArrayList<String> aleatorios;
    private int start, end;

    public Task(String name, ArrayList<String> aleatorios, int start, int end) {
        this.name = name;
        this.aleatorios = aleatorios;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
        
            ArrayList<String> localList = new ArrayList<>(aleatorios.subList(start, end));
            localList.sort(String::compareTo);
            Thread.sleep(1000);

            //System.out.println("Ejecución de " + name );
            //System.out.println(name + " ordenó: " + localList);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
