package org.matveyvs;


import org.matveyvs.service.HtmlService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

import static java.net.http.HttpRequest.BodyPublishers.ofFile;

public class HttpClientRunner {
    public static void main(String[] args) throws IOException, InterruptedException {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        Path jsonPath = Path.of("/Users/matvey/MyProjects/http-server-task/src/main/resources/file.json");

        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082"))
                .header("Content-Type", "application/json")
                .POST(ofFile(jsonPath))
                .build();
        var response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        Path htmlClientPath = Path.of("/Users/matvey/MyProjects/http-server-task/src/main/resources/client/client.html");
        //save html to directory client
        HtmlService.saveHtmlFile(htmlClientPath, response.body());
    }
}
