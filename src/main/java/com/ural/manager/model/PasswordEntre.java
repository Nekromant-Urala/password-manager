package com.ural.manager.model;

import java.time.LocalDate;
import java.util.Objects;

public class PasswordEntre {
    private String name;
    private String service;
    private String login;
    private String password;
    private String createdAt;
    private String notion;
    private String group;

    private PasswordEntre() {
    }

    public static class Builder {
        private PasswordEntre record;

        public Builder() {
            this.record = new PasswordEntre();
        }

        public Builder(PasswordEntre existingRecord) {
            this.record = new PasswordEntre();

            this.record.name = existingRecord.name;
            this.record.service = existingRecord.service;
            this.record.login = existingRecord.login;
            this.record.password = existingRecord.password;
            this.record.createdAt = existingRecord.createdAt;
            this.record.notion = existingRecord.notion;
            this.record.group = existingRecord.group;
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

        public Builder addPassword(String password) {
            record.password = password;
            return this;
        }

        public Builder addNotion(String notion) {
            record.notion = notion;
            return this;
        }

        public PasswordEntre build() {
            record.createdAt = LocalDate.now().toString();
            return record;
        }
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

    public String getPassword() {
        return password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getGroup() {
        return group;
    }

    public String getNotion() {
        return notion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordEntre that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(service, that.service) && Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(createdAt, that.createdAt) && Objects.equals(notion, that.notion) && Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, service, login, password, createdAt, notion, group);
    }

    @Override
    public String toString() {
        return "PasswordEntre{" +
                ", name='" + name + '\'' +
                ", service='" + service + '\'' +
                ", login='" + login + '\'' +
                ", encryptPassword='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", notion='" + notion + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
