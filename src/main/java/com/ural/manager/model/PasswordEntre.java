package com.ural.manager.model;

import java.time.LocalDate;

public class PasswordEntre {
    private int id;
    private String name;
    private String service;
    private String login;
    private String encryptPassword;
    private LocalDate createdAt;
    private String notion;
    private String group;

    private PasswordEntre() {
    }

    public static class Builder {
        private PasswordEntre record;

        public Builder() {
            this.record = new PasswordEntre();
        }

        public Builder addID(int id) {
            record.id = id;
            return this;
        }

        public Builder addName(String name) {
            record.name = name;
            return this;
        }

        public Builder addGroup(String group) {
            record.group = group;
            return this;
        }

        public Builder addService(String service) {
            record.service = service;
            return this;
        }

        public Builder addLogin(String login) {
            record.login = login;
            return this;
        }

        public Builder addEncryptPassword(String encryptPassword) {
            record.encryptPassword = encryptPassword;
            return this;
        }

        public Builder addCreatedAt(LocalDate createAt) {
            record.createdAt = createAt;
            return this;
        }

        public Builder addNotion(String notion) {
            record.notion = notion;
            return this;
        }

        public PasswordEntre build() {
            return record;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getService() {
        return service;
    }

    public String getLogin() {
        return login;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getNotion() {
        return notion;
    }

    @Override
    public String toString() {
        return "PasswordEntre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", service='" + service + '\'' +
                ", login='" + login + '\'' +
                ", encryptPassword='" + encryptPassword + '\'' +
                ", createdAt=" + createdAt +
                ", notion='" + notion + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
