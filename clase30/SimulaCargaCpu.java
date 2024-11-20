import java.util.Random;

public class SimulaCargaCpu {

  public static void main(String[] args) {
    System.out.println("SimulaCargaCpu");

    if (args.length != 2) {
      System.out.println("Usage: java SimulaCargaCpu <percentage> <duration>");
      System.exit(1);
    }

    int percentage = Integer.parseInt(args[0]);
    int seconds = Integer.parseInt(args[1]);

    if (percentage < 0 || percentage > 100) {
      System.out.println("Percentage must be between 0 and 100");
      System.exit(1);
    }

    System.out.println("Starting process...");
    long endTime = System.currentTimeMillis() + seconds * 1000;
    Random random = new Random();

    int countBusy = 0;
    int countIdle = 0;

    while (System.currentTimeMillis() < endTime) {
      int randomValue = random.nextInt(100);
      if (randomValue < percentage) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 1) {
          Math.sqrt(random.nextInt(2147483647));
        }
        countBusy++;
      } else {
        try {
          Thread.sleep(1);
          countIdle++;
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

    }
    System.out.println("Busy count: " + countBusy);
    System.out.println("Idle count: " + countIdle);
    System.out.println("Busy percentage: " + (countBusy * 100) / (countBusy + countIdle) + "%");
    System.out.println("Idle percentage: " + (countIdle * 100) / (countBusy + countIdle) + "%");
    System.out.println("Total iterations: " + (countBusy + countIdle));
    System.out.println("Process finished");
  }
}