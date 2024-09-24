import java.util.Random;

class Stack {
  private int tope;
  private int size;
  private int actual_tope;
  private char[] pila;
  private boolean isChanged;
  private String lastOperation;

  Stack(int size) {
    this.tope = 0;
    this.actual_tope = 0;
    this.size = size;
    this.pila = new char[size];
    this.isChanged = false;
    this.lastOperation = "";
  }

  public void push(char dato) {
    if (this.isFull())
      return;
    this.pila[tope] = dato;
    this.actual_tope = this.tope;
    this.tope++;
    this.isChanged = true;
    this.lastOperation = "push";
  }

  public char pop() {
    if (this.isEmpty())
      return '\0';
    char dato = this.pila[tope];
    this.pila[tope] = '\0';
    this.actual_tope = this.tope;
    this.tope--;
    this.isChanged = true;
    this.lastOperation = "pop";
    return dato;
  }

  public boolean isEmpty() {
    return this.tope == 0;
  }

  public boolean isFull() {
    return this.tope == this.size;
  }

  public void imprime() {
    System.out.print("Pila -> ");
    for (int i = 0; i < this.tope; i++)
      System.out.print(this.pila[i]);
    System.out.println();
    System.out.println("Tope: " + this.tope);
    System.out.println("Actual tope: " + this.actual_tope);
    System.out.println("Last operation: " + this.lastOperation);
  }

  public void setIsChanged(boolean isChanged) {
    this.isChanged = isChanged;
  }

  public boolean getIsChanged() {
    return this.isChanged;
  }
}

public class PruebaPila implements Runnable {
  static Stack stack;
  static PruebaPila pruebaPila;
  static int timesCleaned = 0;

  // Only works with > 10 threads
  static int identificador_de_hilo() {
    String name = Thread.currentThread().getName();
    char lastChar = name.charAt(name.length() - 1);
    int id = Integer.parseInt("0" + lastChar);
    return id;
  }

  static void clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    timesCleaned++;
  }

  static synchronized void modifica(int id) {
    if (id == 0)
      stack.push('X');
    if (id == 1) 
      stack.pop();
  }

  @Override
  public void run() {
    int id = identificador_de_hilo();
    if (id == 0 || id == 1) {
      while (true) {
        int time = new Random().nextInt(5) + 1; // Random number between 1 and 10
        modifica(id);
        try {
          Thread.sleep(time * 1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    if (id == 2) {
      while (true) {
        try {
          if (stack.getIsChanged()) {
            clear();
            stack.setIsChanged(false);
            stack.imprime();
            System.out.println("Times cleaned: " + timesCleaned);
          }
          Thread.sleep(100);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void main(String[] args) {
    stack = new Stack(10);
    pruebaPila = new PruebaPila();
    Thread t1 = new Thread(pruebaPila); // Productor
    Thread t2 = new Thread(pruebaPila); // Consumidor
    Thread t3 = new Thread(pruebaPila); // Watcher
    t1.start();
    t2.start();
    t3.start();
  }
}