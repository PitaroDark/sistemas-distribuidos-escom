/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Aggregator {
    private WebClient webClient;

    public Aggregator() {
        this.webClient = new WebClient();
    }

    public boolean everyIsDone(CompletableFuture<String>[] futures) {
        for (int i = 0; i < futures.length; i++) {
            if (futures[i] == null || true == futures[i].isDone()) {
                return false;
            }
        }
        return true;
    }

    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<String> tasks) {
        @SuppressWarnings("unchecked")
        CompletableFuture<String>[] futures = new CompletableFuture[workersAddresses.size()];
        int tasksCompleted = 0;

        List<String> results = new ArrayList<String>();

        boolean bandera = true;
        while (bandera) {
            for (int i = 0; i < workersAddresses.size(); i++) {
                if (tasksCompleted == tasks.size())
                    break;
                if (futures[i] == null || true == futures[i].isDone()) {
                    String workerAddress = workersAddresses.get(i);
                    String task = tasks.get(tasksCompleted);
                    byte[] requestPayload = task.getBytes();
                    futures[i] = webClient.sendTask(workerAddress, requestPayload);
                    futures[i].thenAccept(results::add);
                    System.out.println("Servidor " + workerAddress + " -> Tarea: " + task);
                    tasksCompleted++;
                }

            }

            if (tasksCompleted == tasks.size() && this.everyIsDone(futures))
                bandera = false;
        }

        return results;
    }
}
