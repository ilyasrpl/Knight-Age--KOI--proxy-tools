package com.KoiProxy;

public class CharOpt {
    public int id;
    public String name;
    public int level;

    CharOpt(int id, String name, int level) {
        this.id =id;
        this.name = name;
        this.level = level;
    }

    public static CharOpt findById(CharOpt[] charArr, int id) {
        for (CharOpt charOpt : charArr) {
            if (charOpt.id == id) {
                return charOpt;
            }
        }
        return null;
    }
}