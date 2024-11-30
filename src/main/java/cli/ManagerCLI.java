package cli;

import password.entropy.PasswordEntropy;
import password.generator.Generator;

import java.util.Scanner;

public class ManagerCLI {
    public static void main(String[] args) {
        boolean bFlag = run();
        while (bFlag) {
            bFlag = run();
        }
    }

    public static boolean run() {
        Scanner in = new Scanner(System.in);
        int[] generationParameters = new int[6];
        System.out.println("~~~~Введите условия для генерации пароля~~~~");
        System.out.print("Введите длину пароля: ");
        generationParameters[5] = in.nextInt();
        System.out.print("Введите количество цифр, которое должно присутствовать в пароле: ");
        generationParameters[0] = in.nextInt();
        System.out.print("Введите количество строчных букв, которое должно присутствовать в пароле: ");
        generationParameters[1] = in.nextInt();
        System.out.print("Введите количество заглавных букв, которое должно присутствовать в пароле: ");
        generationParameters[2] = in.nextInt();
        System.out.print("Введите количество спец. символов, которое должно присутствовать в пароле: ");
        generationParameters[3] = in.nextInt();
        System.out.print("Введите количество символов пунктуации, которое должно присутствовать в пароле: ");
        generationParameters[4] = in.nextInt();

        String password = Generator.getPassword(generationParameters);
        String passwordSecurity = PasswordEntropy.getEntropy(generationParameters);
        System.out.println();
        System.out.println("Ваш пароль: " + password);
        System.out.println("Надежность вашего пароля оценивается как: " + passwordSecurity);
        System.out.print("Хотите сгенерировать ещё?[y/n]: ");

        if (in.next().equals("n")) {
            in.close();
            return false;
        }

        System.out.println();
        return true;
    }
}
