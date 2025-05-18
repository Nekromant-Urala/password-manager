package com.ural.manager.model;

import java.time.LocalDate;

public class Record {
    private int id;
    private String name;
    private String service;
    private String login;
    private String encryptPassword;
    private LocalDate createdAt;
    private String notion;

    private Record() {
    }

    public static class Builder {
        private Record record;

        public Builder() {
            this.record = new Record();
        }

        public Builder id(int id) {
            record.id = id;
            return this;
        }

        public Builder name(String name) {
            record.name = name;
            return this;
        }

        public Builder service(String service) {
            record.service = service;
            return this;
        }

        public Builder login(String login) {
            record.login = login;
            return this;
        }

        public Builder encryptPassword(String encryptPassword) {
            record.encryptPassword = encryptPassword;
            return this;
        }

        public Builder createdAt(LocalDate createAt) {
            record.createdAt = createAt;
            return this;
        }

        public Builder notion(String notion) {
            record.notion = notion;
            return this;
        }

        public Record build() {
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
}
