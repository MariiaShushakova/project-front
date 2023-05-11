package httprequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        HttpClient clientAsync = HttpClient.newBuilder().executor(executorService).build();

        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/products/1"))
                .DELETE()
                .build();

        List<CompletableFuture<HttpResponse<String>>> responses = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            responses.add(clientAsync.sendAsync(requestDelete, HttpResponse.BodyHandlers.ofString()));
            //clientAsync.send(requestDelete, HttpResponse.BodyHandlers.ofString());
        }

        for (CompletableFuture<HttpResponse<String>> result:responses) {
            System.out.println(result.get().body());
        }
    }
}
