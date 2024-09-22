public class PruebaHilos {
  public static int variable_compartida = 0;
  public static int n;

  public static Thread t1;
  public static Thread t2;

  public static RunnableImpl runnableImpl;

  private static class RunnableImpl implements Runnable {

    private int id;

    @Override
    public void run() {
      for (int i = 0; i < n; i++) {
        modifica();
        // System.out.println(variable_compartida);
      }
    }

    protected int identificador_de_hilo() {
      String name = Thread.currentThread().getName();
      char lastChar = name.charAt(name.length() - 1);
      int id = Integer.parseInt("0" + lastChar);
      return id;
    }

    public synchronized void modifica() {
      this.id = identificador_de_hilo();
      if (this.id == 0)
        variable_compartida++;
      if (this.id == 1)
        variable_compartida--;
    }
  }

  public static void main(String[] args) throws InterruptedException {
    n = Integer.parseInt(args[0]);
    runnableImpl = new RunnableImpl();
    t1 = new Thread(runnableImpl);
    t2 = new Thread(runnableImpl);
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println("Resultado de variable compartida: " + variable_compartida);
  }

}
