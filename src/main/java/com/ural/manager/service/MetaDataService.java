package com.ural.manager.service;

import com.ural.manager.model.Database;
import com.ural.manager.model.MetaData;
import com.ural.manager.serialization.JsonFileStorage;
import com.ural.security.encryption.service.CipherFactory;
import com.ural.security.encryption.service.EncryptionService;
import com.ural.security.encryption.service.KeyGeneratorFactory;
import org.bouncycastle.openssl.EncryptionException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class MetaDataService {
    private final DatabaseService databaseService;
    private final Path pathFile;
    private EncryptionService encryptionService;
    private Database database;

    public MetaDataService() {
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

    public MetaData changPassword(String newPassword) {
        MetaData metaData = database.getMetaData();
        byte[] encryptedNewPassword = getEncryptedPassword(newPassword);
        if (encryptedNewPassword.length > 0) {
            return metaData.toBuilder()
                    .encryptedPasswordWithMeta(new String(encryptedNewPassword, StandardCharsets.UTF_8))
                    .build();
        }
        return null;
    }

    private byte[] getEncryptedPassword(String password) {
        try {
            return encryptionService.encrypt(password.getBytes(StandardCharsets.UTF_8), password.toCharArray());
        } catch (EncryptionException | InvalidKeySpecException | NoSuchAlgorithmException | KeyException |
                 InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException e) {
            System.err.println("Ошибка при шифровании нового пароля! " + e.getMessage());
        }
        return new byte[]{};
    }

    private byte[] convertArray(char[] chars) {
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(chars));
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);

        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }
}
