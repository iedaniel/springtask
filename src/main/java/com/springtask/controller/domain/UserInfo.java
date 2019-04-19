package com.springtask.controller.domain;

public class UserInfo {
    private Integer id;
    private String name;
    private String surname;
    private String birth;
    private String email;
    private String password;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirth() {
        return birth;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserInfo(Integer id, String name, String surname, String birth, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.email = email;
        this.password = password;
    }
}
