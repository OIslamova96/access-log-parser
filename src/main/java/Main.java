import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int validFileCount = 0;
        while (true) {
            System.out.println("Введите путь к файлу для парсинга:");
            String filePath = scanner.nextLine();
            File file = new File(filePath);
            boolean fileExists = file.exists();
            boolean isActualFile = file.isFile();
            if (!fileExists) {
                System.out.println("Файл или директория по указанному пути не существует. Попробуйте еще раз.");
                continue;
            }
            if (!isActualFile) {
                System.out.println("Указанный путь ведет к папке, а не к файлу. Попробуйте еще раз.");
                continue;
            }
            System.out.println("Путь указан верно");
            validFileCount++;
            System.out.println("Это файл номер " + validFileCount);
        }
    }
}

