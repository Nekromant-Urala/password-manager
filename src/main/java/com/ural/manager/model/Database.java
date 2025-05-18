package com.ural.manager.model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private int version;
    private MetaData metaData;
    private List<Record> entries;

    private Database() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Database database;

        public Builder() {
            this.database = new Database();
        }

        public Builder version(int version) {
            database.version = version;
            return this;
        }

        public Builder metaData(MetaData meta) {
            database.metaData = meta;
            return this;
        }

        public Builder entries(List<Record> entries) {
            database.entries = entries;
            return this;
        }

        public Database build() {
            if (database.entries == null) {
                database.entries = new ArrayList<>();
            }
            return database;
        }
    }

    public int getVersion() {
        return version;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public List<Record> getEntries() {
        return entries;
    }
}
