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
    public int level;

    public PlayerSession(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
        this.monsterCollection = new ArrayList<>();
    }
}

