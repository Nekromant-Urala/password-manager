package com.ural.manager.model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private int version;
    private MetaData metaData;
    private List<PasswordEntre> entries = new ArrayList<>();

    private Database() {
    }

    public static class Builder {
        private Database database;

        public Builder() {
            this.database = new Database();
        }

        public Builder addVersion(int version) {
            database.version = version;
            return this;
        }

        public Builder addMetaData(MetaData meta) {
            database.metaData = meta;
            return this;
        }

        public Builder addEntre(PasswordEntre entre) {
            database.entries.add(entre);
            return this;
        }

        public Builder addEntries(List<PasswordEntre> entries) {
            database.entries = entries;
            return this;
        }

        public Database build() {
            return database;
        }
    }

    public int getVersion() {
        return version;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public List<PasswordEntre> getEntries() {
        return entries;
    }
}
