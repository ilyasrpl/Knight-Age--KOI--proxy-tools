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
    public long gold;
    public String name;
    public int level;
    public String expPercent;
    public CharOpt[] CharOpt;

    public PlayerSession(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
        this.monsterCollection = new ArrayList<>();
        this.CharOpt = new CharOpt[3];
        this.gold = 0;
        this.expPercent = "";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public void setExpPercent(short exp) {
        this.expPercent =  String.valueOf(exp / 10.0).replace('.', ',');;
    }
}
