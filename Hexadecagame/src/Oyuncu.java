import java.util.ArrayList;

public class Oyuncu {
    public Oyuncu(String isim) {
        this.isim = isim;
    }

    Oyuncu sonraki = null;
    private ArrayList<Kart> kartlistesi = new ArrayList<>();
    private String isim;

    public void kartCek(ArrayList<Kart> ortadakiDeste) {
        kartlistesi.add(ortadakiDeste.remove(ortadakiDeste.size() - 1));
    }

    public Kart kartBirak(int index) {
        return kartlistesi.remove(index);
    }

    public ArrayList<Kart> getKartlistesi() {
        return kartlistesi;
    }

    public void setKartlistesi(ArrayList<Kart> kartlistesi) {
        this.kartlistesi = kartlistesi;
    }

    public String getIsim() {
        return isim;
    }
}
