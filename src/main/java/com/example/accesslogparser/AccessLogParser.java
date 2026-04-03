package com.example.accesslogparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AccessLogParser {

    public static void main(String[] args) {
        String path = (args.length > 0) ? args[0] : "access.log";
        File file = new File(path);

        if (!file.exists()) {
            System.err.println("Ошибка: Файл не найден по пути: " + path);
            return;
        }
        if (!file.isFile()) {
            System.err.println("Ошибка: Указанный путь не является файлом: " + path);
            return;
        }

        Statistics statistics = new Statistics();

        try (FileReader fileReader = new FileReader(path);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 1024) {
                    throw new LineTooLongException("Строка превышает лимит 1024 символа (найдено: " + line.length() + ")");
                }

                try {
                    LogEntry entry = new LogEntry(line);
                    statistics.addEntry(entry);
                } catch (IllegalArgumentException e) {
                    System.err.println("Ошибка парсинга строки лога (будет пропущена): " + line);
                }
            }

            System.out.println("--- Статистика по лог-файлу ---");
            System.out.println("Средний объём трафика в час: " + statistics.getTrafficRate() + " байт/час");

            System.out.println("\nСтатистика по ОС:");
            statistics.getOsTypeCounts().forEach((os, count) ->
                    System.out.println("  " + os + ": " + count + " запросов"));

            System.out.println("\nСтатистика по браузерам:");
            statistics.getBrowserTypeCounts().forEach((browser, count) ->
                    System.out.println("  " + browser + ": " + count + " запросов"));

        } catch (LineTooLongException e) {
            System.err.println("Фатальная ошибка обработки файла: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Произошла непредвиденная ошибка: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}