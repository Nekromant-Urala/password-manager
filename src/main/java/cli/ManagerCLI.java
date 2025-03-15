package cli;

import manager.generator.passsword.entropy.PasswordEntropy;
import manager.generator.passsword.GeneratorPassword;
import manager.generator.salt.GeneratorSalt;
import manager.storage.SaveData;

import java.util.Scanner;

public class ManagerCLI {
    public static void main(String[] args) {
        GeneratorSalt generatorSalt = new GeneratorSalt();
        SaveData.saveSalt(generatorSalt.getSalt());
        boolean bFlag = run();
        while (bFlag) {
            bFlag = run();
        }
    }

    public static boolean run() {
        GeneratorPassword generator = new GeneratorPassword();
        Scanner in = new Scanner(System.in);
        int[] generationParameters = new int[6];
        System.out.println("~~~~~~~~~~~~Manager CLI~~~~~~~~~~~~");
        System.out.print("Хотите сгенерировать пароль?[y/n]: ");

        if (in.next().equals("n")) {
            System.out.print("Хотите получить пароль, который ранее сохраняли?[y/n]: ");
            if (in.next().equals("n")) {
                System.out.println("Завершение работы....");
                in.close();
                return false;
            }
            in.nextLine();
            System.out.print("Введите логин, по которому сохранен пароль: ");
            String login = in.nextLine();
            System.out.println("Ваш пароль от «" + login + "»: " + SaveData.getPasswordFromFile(login));
            System.out.print("Хотите сгенерировать ещё пароль?[y/n]: ");
            if (in.next().equals("n")) {
                System.out.println("Завершение работы....");
                in.close();
                return false;
            }
        }

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

        String password = generator.getPassword(generationParameters);
        String passwordSecurity = PasswordEntropy.getEntropy(generationParameters);
        System.out.println();
        System.out.println("Ваш пароль: " + password);
        System.out.println("Надежность вашего пароля оценивается как: " + passwordSecurity);
        System.out.print("Хотите сохранить пароль?[y/n]: ");

        if (in.next().equals("y")) {
            in.nextLine();
            System.out.print("Введите название сайта, для которого генерируете пароль: ");
            String login = in.nextLine();
            System.out.println("Ваш логин для пароля: " + login);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            SaveData.savePassword(login, password);
            System.out.println("Ваш пароль успешно зашифрован и сохранён.");
            System.out.println();
        }

        System.out.print("Завершить работу?[y/n]: ");

        if (in.next().equals("y")) {
            System.out.println("Завершение работы......");
            in.close();
            return false;
        }

        System.out.println();
        return true;
    }
}
