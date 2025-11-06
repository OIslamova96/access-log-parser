import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите первое целое число и нажмите <Enter>: ");
        int num1 = scanner.nextInt();
        System.out.print("Введите второе целое число и нажмите <Enter>: ");
        int num2 = scanner.nextInt();
        int sum = num1 + num2;
        System.out.println("Сумма: " + sum);
        int difference = num1 - num2;
        System.out.println("Разность: " + difference);
        int product = num1 * num2;
        System.out.println("Произведение: " + product);
        if (num2 != 0) {
            double quotient = (double) num1 / num2;
            System.out.println("Частное: " + quotient);
        } else {
            System.out.println("Деление на ноль невозможно.");
        }
        scanner.close();
    }
}
