package com.example.taskflow.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.taskflow.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Transaction
    @Query("SELECT * FROM User")
    LiveData<List<User>> getUsers();

    @Transaction
    @Query("SELECT * FROM User WHERE id = :id")
    User getUserById(int id);

    @Transaction
    @Query("SELECT * FROM User WHERE email = :email")
    User getUserByEmail(String email);

    @Transaction
    @Query("SELECT * FROM User WHERE companyCode = :companyCode AND id != :loggedUserId")
    List<User> getUsersByCompanyCode(String companyCode, int loggedUserId);

    @Insert
    long addUser(User user);
}
