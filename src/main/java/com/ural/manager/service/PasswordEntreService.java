package com.ural.manager.service;

import com.ural.manager.model.Database;
import com.ural.manager.model.MasterPasswordHolder;
import com.ural.manager.model.MetaData;
import com.ural.manager.model.PasswordEntre;
import com.ural.manager.serialization.JsonFileStorage;
import com.ural.security.encryption.service.CipherFactory;
import com.ural.security.encryption.service.EncryptionService;
import com.ural.security.encryption.service.KeyGeneratorFactory;
import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.bouncycastle.openssl.EncryptionException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PasswordEntreService {
    private final DatabaseService databaseService;
    private final Path pathFile;
    private EncryptionService encryptionService;
    private Database database;

    public PasswordEntreService() {
        databaseService = new DatabaseService();
        JsonFileStorage fileStorage = new JsonFileStorage();
        pathFile = Paths.get(fileStorage.loadPaths().get(0));
        loadDatabase(pathFile);
    }

    private void loadDatabase(Path path) {
        try {
            database = databaseService.loadDatabase(path);
            MetaData metaData = database.getMetaData();
            encryptionService = new EncryptionService(
                    CipherFactory.getNameCipher(metaData.getEncryptAlgorithm()),
                    KeyGeneratorFactory.getKeyGenerator(metaData.getKeyGenerator()),
                    metaData.getIterations()
            );
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке базы данных. " + e.getMessage());
        }
    }

    public void getPasswordFromEntre(PasswordEntre passwordEntre) {
        byte[] encryptedPassword = passwordEntre.getPassword().getBytes(StandardCharsets.UTF_8);
        try {
            byte[] password = encryptionService.decrypt(encryptedPassword, MasterPasswordHolder.getMasterPassword());
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(new String(password, StandardCharsets.UTF_8));
            clipboard.setContent(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clearClipboard();
    }

    private void clearClipboard() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Clipboard.getSystemClipboard().clear();
                });
            }
        }, 60000);
    }

    public void addPasswordEntre(PasswordEntre passwordEntre) {
        byte[] encryptedPassword;
        try {
            encryptedPassword = encryptionService.encrypt(
                    passwordEntre.getPassword().getBytes(StandardCharsets.UTF_8),
                    MasterPasswordHolder.getMasterPassword()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PasswordEntre entre = new PasswordEntre.Builder(passwordEntre)
                .addPassword(new String(encryptedPassword, StandardCharsets.UTF_8))
                .build();

        database.getEntries().add(entre);
        databaseService.saveChanges(pathFile, database);
    }

    public List<PasswordEntre> getEntreOfGroups(String nameGroup) {
        List<PasswordEntre> entries = getAllElements();

        return entries.stream()
                .filter(element -> element.getGroup().equals(nameGroup))
                .collect(LinkedList::new, LinkedList::offer, LinkedList::addAll);
    }

    public List<PasswordEntre> getOtherGroups() {
        List<PasswordEntre> entries = getAllElements();
        List<String> groups = List.of("Общие", "Сеть", "Интернет", "Почта", "Счета", "OC");
        return entries.stream()
                .filter(element -> !groups.contains(element.getGroup()))
                .collect(LinkedList::new, LinkedList::offer, LinkedList::addAll);
    }

    public void deletePasswordEntre(PasswordEntre passwordEntre) {
        try {
            Database database = databaseService.loadDatabase(pathFile);
            database.getEntries().remove(passwordEntre);
            databaseService.saveChanges(pathFile, database);
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке базы данных. " + e.getMessage());
        }
    }

    public List<PasswordEntre> updateKeyForEntries(char[] key) {
        List<PasswordEntre> entries = getAllElements();
        Stream<PasswordEntre> entreStream = entries.stream().map(entre -> {
            try {
                byte[] password = encryptionService.decrypt(entre.getPassword().getBytes(StandardCharsets.UTF_8), MasterPasswordHolder.getMasterPassword());
                byte[] encryptPassword = encryptionService.encrypt(password, key);
                entre = entre.toBuilder().addPassword(new String(encryptPassword, StandardCharsets.UTF_8)).build();
            } catch (InvalidAlgorithmParameterException | EncryptionException | NoSuchPaddingException |
                     IllegalBlockSizeException | InvalidKeySpecException | NoSuchAlgorithmException |
                     BadPaddingException |
                     KeyException e) {
                System.err.println("Ошибка при шифровании паролей! " + e.getMessage());
            }
            return entre;
        });
        return entreStream.collect(Collectors.toList());
    }

    public List<PasswordEntre> getAllElements() {
        try {
            Database database = databaseService.loadDatabase(pathFile);
            return database.getEntries();
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке базы данных. " + e.getMessage());
            return List.of();
        }
    }
}
