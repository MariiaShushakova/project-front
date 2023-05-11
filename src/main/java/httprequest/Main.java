package httprequest;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //отправка и получение запроса
        HttpClient client = HttpClient.newHttpClient();
        //хранит данные запроса (url, version api, GET/POST/etc, headers, body)
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://dummyjson.com/products/add")).build();
        //хранит данные ответа (код, headers, body)
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());


        HttpRequest requestPost = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/products/add"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"title\" : \"BMW Pensil\" }"))
                .header("Content-type", "application/json")
                .version(HttpClient.Version.HTTP_2)
                .timeout(Duration.of(5000, MILLIS))
                .build();
        HttpResponse responsePost = client.send(requestPost, HttpResponse.BodyHandlers.ofString());

        System.out.println(responsePost.body());

        HttpRequest requestPut = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/products/1"))
                .PUT(HttpRequest.BodyPublishers.ofString("{\"title\" : \"iPhone Galaxy +1\" }"))
                .build();
        HttpResponse responsePut = client.send(requestPut, HttpResponse.BodyHandlers.ofString());

        System.out.println(responsePut.body());

        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/products/1"))
                .DELETE()
                .build();
        HttpResponse responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());

        System.out.println(responseDelete.body());
    }
}
