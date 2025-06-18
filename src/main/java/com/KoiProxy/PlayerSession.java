package com.KoiProxy;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PlayerSession {
   public int id;
    public Socket socket;
    public List<Monster> monsterCollection;
    public String email;
    public String password;
    public String areaName;
    public String name;
    public int level;
    public CharOpt[] CharOpt;

    public PlayerSession(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
        this.monsterCollection = new ArrayList<>();
        this.CharOpt = new CharOpt[3];
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
