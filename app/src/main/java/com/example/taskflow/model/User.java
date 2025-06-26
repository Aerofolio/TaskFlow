package com.example.taskflow.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    private String name;

    @NonNull
    private String password;

    @NonNull
    private String email;

    @NonNull
    private String companyCode;

    public User(@NonNull String name, @NonNull String password, @NonNull String email, @NonNull String companyCode) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.companyCode = companyCode;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(@NonNull String companyCode) {
        this.companyCode = companyCode;
    }

    public int getId() {
        return id;
    }
}