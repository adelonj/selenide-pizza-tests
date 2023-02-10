package org.example.delonj.dao;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class UserAccount {
    private String userName;
    private String email;
    private String phone;

    public UserAccount getNewUser(){
        UserAccount client = new UserAccount();
        client.setUserName("act" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss")));
        client.setEmail(client.userName + "@me.com");
        client.setPhone("7" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss")));
        return client;
    }
}
