package com.ural.manager.model;

import java.util.Arrays;

public class MasterPasswordHolder {
    private static char[] masterPassword;

    private MasterPasswordHolder() {

    }

    public static void setMasterPassword(char[] password) {
        MasterPasswordHolder.clear();
        masterPassword = Arrays.copyOf(password, password.length);
    }

    public static char[] getMasterPassword() {
        if (masterPassword == null) {
            throw new IllegalStateException("Мастер пароль не установлен");
        }
        return Arrays.copyOf(masterPassword, masterPassword.length);
    }

    public static void clear() {
        if (masterPassword != null) {
            Arrays.fill(masterPassword, '\0');
            masterPassword = null;
        }
    }
}
