package com.KoiProxy.catalog;

import java.util.Hashtable;

public class Monsters {
    public static Hashtable<Short, Monster> monster = new Hashtable<>();

    public static Monster createMonster(short id, String name, short level, int hp, byte idk) {
        Monster newMonster = new Monster(id, name, level, hp, idk);
        monster.put(id, newMonster);
        return newMonster;
    }
 
    public static Monster getMonster(short id) {
        return monster.get(id);
    }

    public static Monster updateMonster(short id, String name, short level, int hp, byte idk) {
        if (monster.containsKey(id)) {
            Monster updatedMonster = new Monster(id, name, level, hp, idk);
            monster.put(id, updatedMonster);
            return updatedMonster;
        }
        return null; // Monster not found
    }

    public static boolean deleteMonster(short id) {
        if (monster.containsKey(id)) {
            monster.remove(id);
            return true;
        }
        return false; // Monster not found
    }
}

class Monster {
    public short id;
    public String name;
    public short level;
    public int hp;
    public byte idk;

    Monster(short id, String name, short level, int hp, byte idk) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.idk = idk;
    }
}
