package com.example.accesslogparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class LineTooLongException extends RuntimeException {
    public LineTooLongException(String message) {
        super(message);
    }
}

public class AccessLogParser {

    public static void main(String[] args) {

        File logFile = new File(path);
        if (!logFile.exists()) {
            System.err.println("Ошибка: Файл не найден по пути: " + path);
            return;
        }
        if (!logFile.isFile()) {
            System.err.println("Ошибка: Указанный путь не является файлом: " + path);
            return;
        }

        int totalLines = 0;
        int maxLength = 0;
        int minLength = Integer.MAX_VALUE;

        try (FileReader fileReader = new FileReader(logFile);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                totalLines++;

                int currentLength = line.length();

                if (currentLength > 1024) {
                    throw new LineTooLongException("Обнаружена строка длиной " + currentLength +
                            " символов, что превышает допустимый лимит в 1024 символа. Строка: '" +
                            line.substring(0, Math.min(line.length(), 50)) + "...'");
                }

                if (currentLength > maxLength) {
                    maxLength = currentLength;
                }
                if (currentLength < minLength) {
                    minLength = currentLength;
                }
            }

            if (totalLines == 0) {
                minLength = 0;
            }

            System.out.println("Общее количество строк в файле: " + totalLines);
            System.out.println("Длина самой длинной строки в файле: " + maxLength);
            System.out.println("Длина самой короткой строки в файле: " + minLength);

        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: Файл не найден. " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            e.printStackTrace();
        } catch (LineTooLongException e) {
            System.err.println("Ошибка обработки файла: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Произошла непредвиденная ошибка: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}