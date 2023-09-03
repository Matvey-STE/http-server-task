package org.matveyvs.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class HtmlService {
    private Integer totalIncome;
    private Integer totalTax;
    private Integer totalProfit;

    public HtmlService(Integer totalIncome, Integer totalTax, Integer totalProfit) {
        this.totalIncome = totalIncome;
        this.totalTax = totalTax;
        this.totalProfit = totalProfit;
    }

    public Integer getTotalIncome() {
        return totalIncome;
    }

    public Integer getTotalTax() {
        return totalTax;
    }

    public Integer getTotalProfit() {
        return totalProfit;
    }

    public static void prepareHtmlFile(String path, HtmlService htmlService) {
        try {
            String template = Files.readString(Path.of(path));

            template = template.replace("${total_income}", String.valueOf(htmlService.getTotalIncome()));
            template = template.replace("${total_tax}", String.valueOf(htmlService.getTotalTax()));
            template = template.replace("${total_profit}", String.valueOf(htmlService.getTotalProfit()));

            Files.write(Path.of(path), template.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveHtmlFile(Path pathOfFile, String htmlFile) {
        try {
            if (!Files.exists(pathOfFile)) {
                Files.createFile(pathOfFile);
            }
            Files.write(pathOfFile, htmlFile.getBytes(), StandardOpenOption.WRITE);
            System.out.println("HTML file was saved");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
