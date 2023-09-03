package org.matveyvs;

import org.matveyvs.entity.Employee;
import org.matveyvs.service.HtmlService;
import org.matveyvs.service.ServerService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class HttpServer {
    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            var socket = serverSocket.accept();
            processSocket(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSocket(Socket socket) {
        try (socket;
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

            int contentLength = 0;
            String line;
            while (!(line = inputStream.readLine()).isEmpty()) {
                if (line.startsWith("Content-Length:")) {
                    String value = line.substring("Content-Length:".length()).trim();
                    contentLength = Integer.parseInt(value);
                }
            }

            // Read the request body
            byte[] bodyBytes = inputStream.readNBytes(contentLength);
            String jsonPath = "src/main/resources/server/file.json";
            try (FileOutputStream fos = new FileOutputStream(jsonPath)) {
                fos.write(bodyBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Get info from Json and prepare HTML
            String htmlPath = "/Users/matvey/MyProjects/http-server-task/src/main/resources/HttpResponse.html";

            if (ServerService.readFromJson(jsonPath).isPresent()) {
                List<Employee> listOfEmployee = ServerService.readFromJson(jsonPath).get().getEmployeeList();
                int totalProfit = ServerService.getTotalProfit(listOfEmployee);
                int totalIncome = ServerService.getTotalIncome(listOfEmployee);
                int totalTax = ServerService.getTotalTax(listOfEmployee);
                HtmlService htmlService = new HtmlService(totalIncome, totalTax, totalProfit);
                HtmlService.prepareHtmlFile(htmlPath, htmlService);
            }
            //remove json after using
            ServerService.removeFile(jsonPath);

            byte[] body = Files.readAllBytes(Path.of(htmlPath));
            outputStream.write("""
                    HTTP/1.1 200 OK
                    Content-Type: text/html
                    Content-Length: %s
                    """.formatted(body.length).getBytes());

            outputStream.write(System.lineSeparator().getBytes());

            outputStream.write(body);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
