import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int tercih = -1;
        boolean oyundevam;
        Oyun oyun = new Oyun();
        Scanner scanner = new Scanner(System.in);
        oyun.oyunKurallariYazdir();
        System.out.println("1)Yapay Zeka İle 1v1 Oyna\n2)İki Kişi Oyna\n3)Üç Kişi Oyna\n4)Dört Kişi Oyna");
        tercih = scanner.nextInt();
        switch (tercih) {
            case 1:
                oyun.oyuncuEkle(new Oyuncu("Oyuncu 1"));
                oyun.oyuncuEkle(new Oyuncu("Yapay Zeka"));
                break;
            case 2:
                oyun.oyuncuEkle(new Oyuncu("Oyuncu 1"));
                oyun.oyuncuEkle(new Oyuncu("Oyuncu 2"));
                break;
            case 3:
                oyun.oyuncuEkle(new Oyuncu("Oyuncu 1"));
                oyun.oyuncuEkle(new Oyuncu("Oyuncu 2"));
                oyun.oyuncuEkle(new Oyuncu("Oyuncu 3"));
                break;
            case 4:
                oyun.oyuncuEkle(new Oyuncu("Oyuncu 1"));
                oyun.oyuncuEkle(new Oyuncu("Oyuncu 2"));
                oyun.oyuncuEkle(new Oyuncu("Oyuncu 3"));
                oyun.oyuncuEkle(new Oyuncu("Oyuncu 4"));
                break;
            default:
                System.out.println("Geçersiz bir tuşlama yapıldı. Oyun Kapandı. Bir sonrakine dikkatli ol!");
                System.exit(1);
        }
        oyun.kartDagit();
        oyun.kartYazdir();
        oyundevam = oyun.kartAt();
        while (oyundevam) {
            oyun.kartYazdir();
            oyundevam = oyun.kartAt();
        }
    }
}