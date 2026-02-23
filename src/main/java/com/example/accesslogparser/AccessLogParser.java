package com.example.accesslogparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class AccessLogParser {

    public static void main(String[] args) {
        String path = "access.log";
        File file = new File(path);

        if (!file.exists()) {
            System.out.println("Файл не существует");
            return;
        }
        if (!file.isFile()) {
            System.out.println("Указанный путь не является файлом");
            return;
        }

        int totalLines = 0;
        int maxLength = 0;
        int minLength = Integer.MAX_VALUE;

        try (FileReader fileReader = new FileReader(path);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                int length = line.length();

                if (length > 1024) {
                    throw new LineTooLongException("Строка превышает лимит 1024 символа (найдено: " + length + ")");
                }

                totalLines++;
                if (length > maxLength) {
                    maxLength = length;
                }
                if (length < minLength) {
                    minLength = length;
                }
            }

            System.out.println("Общее количество строк в файле: " + totalLines);
            System.out.println("Длина самой длинной строки: " + maxLength);
            System.out.println("Длина самой короткой строки: " + (totalLines == 0 ? 0 : minLength));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}