package com.ural.gui.windows.main;

public class PasswordRecord {
    private String name;
    private String login;
    private String password;
    private String service;
    private String notion;

    public PasswordRecord(String name, String login, String password, String service, String notion) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.service = service;
        this.notion = notion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getNotion() {
        return notion;
    }

    public void setNotion(String notion) {
        this.notion = notion;
    }
}
