import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class LineTooLongException extends RuntimeException {
    public LineTooLongException(String message) {
        super(message);
    }
}

public class AccessLogParser {

    public static void main(String[] args) {
        String path = "access.log";
        File logFile = new File(path);

        if (!logFile.exists() || !logFile.isFile()) {
            System.err.println("Файл не найден: " + path);
            return;
        }

        int totalRequests = 0;
        int googleBotCount = 0;
        int yandexBotCount = 0;

        try (FileReader fileReader = new FileReader(logFile);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                int length = line.length();

                if (length > 1024) {
                    throw new LineTooLongException("Строка превышает 1024 символа");
                }

                totalRequests++;

                int lastQuoteIndex = line.lastIndexOf("\"");
                int secondLastQuoteIndex = line.lastIndexOf("\"", lastQuoteIndex - 1);

                if (lastQuoteIndex != -1 && secondLastQuoteIndex != -1) {
                    String userAgent = line.substring(secondLastQuoteIndex + 1, lastQuoteIndex);

                    int openBracket = userAgent.indexOf("(");
                    int closeBracket = userAgent.indexOf(")");

                    if (openBracket != -1 && closeBracket > openBracket) {
                        String firstBrackets = userAgent.substring(openBracket + 1, closeBracket);

                        String[] parts = firstBrackets.split(";");
                        if (parts.length >= 2) {
                            String fragment = parts[1].trim();

                            int slashIndex = fragment.indexOf("/");
                            if (slashIndex != -1) {
                                String botName = fragment.substring(0, slashIndex);

                                if (botName.equals("Googlebot")) {
                                    googleBotCount++;
                                } else if (botName.equals("YandexBot")) {
                                    yandexBotCount++;
                                }
                            }
                        }
                    }
                }
            }

            if (totalRequests > 0) {
                double googleRatio = (double) googleBotCount / totalRequests;
                double yandexRatio = (double) yandexBotCount / totalRequests;

                System.out.println("Всего запросов: " + totalRequests);
                System.out.println("Доля Googlebot: " + googleRatio);
                System.out.println("Доля YandexBot: " + yandexRatio);
            }

        } catch (LineTooLongException e) {
            System.err.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}