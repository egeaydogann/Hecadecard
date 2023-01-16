import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Oyun {
    private ArrayList<Kart> duzenli = new ArrayList<>();
    private ArrayList<Kart> karismis = new ArrayList<>();
    private ArrayList<Kart> ortayAtilanKart = new ArrayList<>();
    private Oyuncu sira;
    private Oyuncu son = null;
    private Oyuncu ilk = null;
    private int secim = 0;

    //Kart desteleri oluşturuluyor. Önce düzenli bir deste oluşturulup rasgele sayılar kullanarak karışmış deste oluşturuyoruz.
    //En sonda da oyunun düzgün devam edebilmesi için ortaya karışmış kartlardan bir tane kart koyuyoruz.
    public Oyun() {
        String[] ozel = {"A(10)[RENGİ DEĞİŞTİR]", "C(12)[BLOKLA]", "E(14)[2 KART ÇEKTİR]", "F(15)[4KART ÇEKTİR]"};
        String[] semboller = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "B(11)", "D(13)"};
        String[] renkler = {"Mavi", "Kırmızı", "Sarı", "Yeşil"};
        for (String renk : renkler) {
            for (String sembol : semboller) {
                Kart gecicikart = new Kart(renk, sembol);
                duzenli.add(gecicikart);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (String ozellik : ozel) {
                Kart gecicikart = new Kart("ÖZEL", ozellik);
                duzenli.add(gecicikart);
            }
        }

        int a = duzenli.size();
        for (int i = 0; i < a; i++) {
            Random random = new Random();
            karismis.add(duzenli.remove(random.nextInt(0, duzenli.size())));
        }

        ortayAtilanKart.add(karismis.remove(karismis.size() - 1));
    }

    // Veri yapılarını kullanarak oyuncu sayısını arttırıyoruz.
    public void oyuncuEkle(Oyuncu oyuncu) {
        if (son == null) {
            son = oyuncu;
            son.sonraki = son;
            ilk = oyuncu;
        } else {
            oyuncu.sonraki = son.sonraki;
            son.sonraki = oyuncu;
            son = oyuncu;
        }
        sira = ilk;
    }


    // Veri yapılarını kullanarak bütün oyunculara 7 şer tane kart dağıtıyoruz.
    public void kartDagit() {
        Oyuncu simdiki = son.sonraki;
        while (simdiki != son) {
            for (int i = 0; i < 7; i++) {
                simdiki.kartCek(karismis);
            }
            simdiki = simdiki.sonraki;
        }
        for (int i = 0; i < 7; i++) {
            simdiki.kartCek(karismis);
        }
    }

    //kartAt() fonksiyonunda sıra bir kez daha geçeceği için burda bir sonraki oyuncuya anlık geçiriyoruz.
    //sira.sonraki.sonraki olmuş oluyor yani bi sonraki oyuncu bloklanmış oluyor.
    public void blokla() {
        sira = sira.sonraki;
    }

    //bir sonraki oyuncuya iki kart çektirir.
    public void ikiKartCek() {
        sira.sonraki.kartCek(karismis);
        sira.sonraki.kartCek(karismis);
    }

    // Kart listesi yazdırılıyor.
    public void kartYazdir() {
        if (!(sira.getIsim().equals("Yapay Zeka"))) {
            System.out.println("Ortada : " + sonAtilanKart().getRenk() + " " + sonAtilanKart().getSembol());
            System.out.println("------------------" + sira.getIsim() + "------------------"); //sırası olan oyuncunun ismi
            System.out.println("ORTAYA AT");
            //Kart listesinin indexleri 0 dan başlıyor ama 1) 2) diye gitmesi gerektiğinden i değerine her seferinde 1 ekliyorum.
            for (int i = 0; i < sira.getKartlistesi().size(); i++) {
                System.out.println((i + 1) + ") " + sira.getKartlistesi().get(i).getRenk() + " " + sira.getKartlistesi().get(i).getSembol());
            }
            secim = sira.getKartlistesi().size() + 1; // listelemeyi yapabilmek için ÖR: 5)Mavi 9 6)Kart çek olabilsin diye
            System.out.println("YA DA\n" + secim + ") Desteden kart çek ");
        }
    }

    public void renkDegistir() {
        int renksecim;
        if (!sira.getIsim().equals("Yapay Zeka")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Renk seçiniz:\n1)Kırmızı\n2)Mavi\n3)Sarı\n4)Yeşil\nSeçim:");
            renksecim = scanner.nextInt();
        } else {
            renksecim = 3;
        }

        String renk = "Boş";
        if (renksecim < 5 && renksecim > 0) {
            if (renksecim == 1) {
                renk = "Kırmızı";
            } else if (renksecim == 2) {
                renk = "Mavi";

            } else if (renksecim == 3) {
                renk = "Sarı";


            } else if (renksecim == 4) {
                renk = "Yeşil";
            }
            ortayAtilanKart.add(new Kart(renk, "<<--Renk değiştirme kullanıldı."));
        } else {
            System.out.println("Renk değiştirilemedi. Geçersiz giriş. Tekrar tercih yapınız.");
            renkDegistir(); // Eğer kullanıcı geçersiz giriş yaparsa recursive olarak fonksiyon tekrar çağrılır.
        }
    }

    // Son atılan kartı her seferinde uzun uzun yazmak hem kod anlaşılırlığını hem de yazma zorluğu oluşturuyordu fonksiyon oluşturdum.
    public Kart sonAtilanKart() {
        return ortayAtilanKart.get(ortayAtilanKart.size() - 1);
    }

    public boolean kartAt() {
        Scanner scanner = new Scanner(System.in);
        secim = sira.getKartlistesi().size() + 1;
        int atilacakkart = 1;
        if (!sira.getIsim().equals("Yapay Zeka")) {
            atilacakkart = scanner.nextInt();
        } else {
            for (int i = 0; i < sira.getKartlistesi().size(); i++) {
                if (sonAtilanKart().getRenk().equals(sira.getKartlistesi().get(i).getRenk()) || sonAtilanKart().getSembol().equals(sira.getKartlistesi().get(i).getSembol()) || sira.getKartlistesi().get(i).getRenk().equals("ÖZEL") || sonAtilanKart().getRenk().equals("ÖZEL")) {
                    atilacakkart = i + 1;
                    break;
                } else {
                    atilacakkart = sira.getKartlistesi().size() + 1;
                }
            }

        }
        // eğer giriş yanlış olursa tekrardan bir kart seçmesini istemek için hem de oyun devamlılığını sağlamak için yapılmış döngü.
        while (true) {
            if (!(atilacakkart <= sira.getKartlistesi().size() + 1) || atilacakkart == 0) { // eğer oyuncunun kart destesinde olmayan bir index girilirse çalışır.
                System.out.println("Geçersiz giriş tekrar deneyiniz.");
                atilacakkart = scanner.nextInt();
                continue;
            }
            if (atilacakkart == secim) {
                sira.kartCek(karismis);
            } else if (sonAtilanKart().getSembol().equals(sira.getKartlistesi().get(atilacakkart - 1).getSembol()) || sonAtilanKart().getRenk().equals(sira.getKartlistesi().get(atilacakkart - 1).getRenk()) || sira.getKartlistesi().get(atilacakkart - 1).getRenk().equals("ÖZEL") || sonAtilanKart().getRenk().equals("ÖZEL")) {
                Kart atilankart = sira.kartBirak(atilacakkart - 1);
                ortayAtilanKart.add(atilankart);
                if (sonAtilanKart().getSembol().equals("F(15)[4KART ÇEKTİR]")) {
                    ikiKartCek();
                    ikiKartCek();
                    System.out.println("Sıradaki oyuncu dört kart çekti.");
                } else if (sonAtilanKart().getSembol().equals("E(14)[2 KART ÇEKTİR]")) {
                    ikiKartCek();
                    System.out.println("Sıradaki oyuncu iki kart çekti.");
                } else if (sonAtilanKart().getSembol().equals("C(12)[BLOKLA]")) {
                    blokla();
                    System.out.println("Sıradaki oyuncu bloklandı.");
                } else if (sonAtilanKart().getSembol().equals("A(10)[RENGİ DEĞİŞTİR]")) {
                    renkDegistir();
                    System.out.println("Renk değiştirildi.");
                }


            } else {
                System.out.println("Bu kartı atamazsınız! Ortadaki renk ile attığınız renk aynı olmalı veya sembol ile sembol aynı olmalı.");
                System.out.println("Özel kartlar her zaman atılabilir");
                atilacakkart = sira.getKartlistesi().size() + 28;
                //sonsuz döngüye girmesin diye atilacakkart değişkenini rasgele bi değerle arttırıyorum.
                //Giriş herhangibir hataya neden olmuyor ancak oyun kuralları gereği atılmaması gerekiyor
                // böyle olunca da tekrar giriş aldırmak için girişini arttırmam gerekiyor.
                continue;
            }

            if (sira.getKartlistesi().isEmpty()) {
                System.out.println(sira.getIsim() + " KAZANDI OYUN BİTTİ!");
                return false;
            } else if (karismis.isEmpty()) {
                System.out.println("ORTADAKİ KART BİTTİ. BERABERE!");
                return false;
            }
            sira = sira.sonraki;
            return true;
        }
    }

    public void oyunKurallariYazdir() {
        System.out.println("----------------- HEXADECARD  -------------------");
        System.out.println("Bu oyun 2-4 kişilik ile oynanmaktadır ve kaç kişi ile oynanacağı oyunun başında seçilir.");
        System.out.println("Oyun 0 dan F(15) e kadar(onaltılık taban) sayıları içermektedir.");
        System.out.println("Bir destede 64 kart vardır. A-C-E-F özel yeteneği olan kartlardır ve çeşitli özellikleri vardır.");
        System.out.println("A(10):Ortadaki rengi değiştirir. C(12):Sizden sonraki oyuncuyu bloklar kart atamaz.");
        System.out.println("E(14): Sıradaki oyuncuya iki kart çektirir. F(15): Sıradaki oyuncuya dört kart çektirir.");
        System.out.println("Kartların birleşme özelliği yoktur.\n");
        System.out.println("OYUN NASIL OYNANIR?");
        System.out.println("Oyun başladığında elinize rasgele olarak 7 kart gelir. Ortadaki kart ile aynı sembol-sayı ya da aynı renk bir kart atmalısınız.");
        System.out.println("[ÖZEL] ile belirtilen kartları her rengin üzerine atabilirsiniz.");
        System.out.println("Elindeki kartları en çabuk bitiren kazanır!");
    }
}
