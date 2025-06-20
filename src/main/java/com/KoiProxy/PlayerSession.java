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
    public static String[] mapNames = {"Desa Kecil", "Desa Serigala", "Tambang", "Tepi Hutan", "Gua Api", "Hutan Keramat", "Ngarai", "Padang Rumput Serigala", "Lembah Misterius", "Danau Keramat", "Zona Kosong", "Pesisir Pantai", "Tebing Batu", "Daerah Batu Karang", "Bangkai Kapal", "Rawa", "Kuil Kuna", "Gua Kelelawar", "Gua Siluman Serigala", "Pantai", "Gurun", "Bukit Pasir", "Zona Lembah", "Jurang Kematian", "Kuburan Pasir", "Hutan Kematian", "Pemandian Air Panas", "Lembah Batu", "Monster Penjaga", "Bangunan Bawah Tanah Tingkat 1", "Bangunan Bawah Tanah Tingkat 2", "Bangunan Bawah Tanah Tingkat 3", "Raja Mummi", "Kota Emas", "Sisi Barat", "Sisi Timur", "Arena", "Gunung Langit", "Jalur Pegunungan", "Tebing", "Gunung Pelangi", "Pintu Masuk Ke Dunia Atas", "Jalan Lembah", "Pintu Masuk Kebawah Tanah", "Tebing", "Pintu Masuk Dunia Bawah", "Arena", "Bukit Mayat", "Persimpangan Kematian", "Phó bản mới", "Kebun", "Gerbang Surga", "Gerbang Neraka", "Equip", "Desa Cahaya", "Equip", "Desa Angin", "Prepare", "Desa Halilintar", "Prepare", "Desa Api", "Medan Perang", "Neraka Lantai 1", "Hutan Medusa", "Hutan Angker", "Hutan Monster", "Air Terjun", "Kota Pelabuhan", "Area pantai selatan", "Area pantai utara", "Area pantai barat", "Hutan tikus", "Hutan bunga merah ", "Teluk Caribe ", "Labirin", "Labirin Lantai 1", "Labirin Lantai 2", "Labirin Lantai 3", "Labirin Lantai 4", "Labirin Lantai Terakhir", "DUEL", "", "Buka Lapak", "Gerbang Timur", "Gerbang Barat", "Gerbang Selatan", "Gerbang Utara", "Arena", "Gua", "Bukit Berpasir", "Padang Rumput", "Bukit Berumput", "Gerbang Putih", "Kota Salju", "Lembah Beku", "Dasar Gunung Salju", "Jalur Beku Pegunungan ", "Jurang Berkabut", "Gunung Salju", "Map Boss", "Assemble", "", "Tournament", "Duel", "Black Market", "Labirin ", "Labirin ", "Labirin ", "Labirin ", "Labirin ", "Labirin ", "Labirin ", "Labirin ", "Labirin ", "Labirin ", "Labirin ", "Area Menunggu", "Area Berburu Zombie ", "Pulau Ksatria", "Pulau Assassin", "Pulau Penyihir", "Pulau Gunner", "Pulau Tengkorak", "Pulau Marshall", "Boss Map Lembah Batu", "Jembatan Terkutuk", "Jurang Dalam", "Ruin Arid", "Ruin Senyap", "Tanah Berkabut", "Kuburan Tulang", "Tanah Kematian", "Sungai Mati", "Jejak Mendung", "Neraka Mimpi Buruk", "Desa Embun", "Pasar Material", ""};
    public String locationNow;
    public byte zone;
    public Pos pos;

    public PlayerSession(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
        this.monsterCollection = new ArrayList<>();
        this.CharOpt = new CharOpt[3];
        this.gold = 0;
        this.expPercent = "";
        this.locationNow = "";
        this.pos = new Pos(0, 0);
        this.zone = 0;
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

    public void setZone(byte zone) {
        this.zone = (byte) (zone + 1);
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }
}
