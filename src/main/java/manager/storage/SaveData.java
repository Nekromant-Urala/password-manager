package manager.storage;

import manager.encryption.aes.AdvancedEncryptionStandard256;

import java.io.*;

public class SaveData {

    public static void saveSalt(String salt) {
        try (FileWriter fileWriter = new FileWriter("D:\\PetProject\\src\\main\\java\\manager\\storage\\salt.txt", false)) {
            fileWriter.write(salt);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSaltFromFile() {
        String salt;
        try (FileReader fileReader = new FileReader("D:\\PetProject\\src\\main\\java\\manager\\storage\\salt.txt")) {
            BufferedReader br = new BufferedReader(fileReader);
            salt = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return salt;
    }

    public static void savePassword(String login, String password) {
        try (FileWriter fileWriter = new FileWriter("D:\\PetProject\\src\\main\\java\\manager\\storage\\passwords.txt", true)) {
            fileWriter.write( login + " " + AdvancedEncryptionStandard256.encrypt(password, login) + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPasswordFromFile(String login) {
        try (FileReader fileReader = new FileReader("D:\\PetProject\\src\\main\\java\\manager\\storage\\passwords.txt")) {
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null){
                if (line.startsWith(login)) {
                    return AdvancedEncryptionStandard256.decrypt(line.split(" ")[1], login);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Ошибка! Пароль не найден!";
    }
}
