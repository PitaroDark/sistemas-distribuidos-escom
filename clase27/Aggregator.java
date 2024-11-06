import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Aggregator {
    private WebClient webClient;

    public Aggregator() {
        this.webClient = new WebClient();
    }

    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<String> tasks) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        List<String> results = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            String workerAddress = workersAddresses.get(i % workersAddresses.size());
            String task = tasks.get(i);
            byte[] requestPayload = task.getBytes();
            CompletableFuture<String> future = webClient.sendTask(workerAddress, requestPayload);
            future.thenAccept(results::add);
            System.out.println("Servidor " + workerAddress + " -> Tarea: " + task);
            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join(); // Wait for all tasks to complete

        return results;
    }
}
