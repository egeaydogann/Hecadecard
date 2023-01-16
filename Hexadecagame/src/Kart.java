public class Kart {
    private String renk;
    private String sayi;

    public Kart(String renk, String sayi) {
        this.setRenk(renk);
        this.sayi = sayi;
    }


    public String getRenk() {
        return renk;
    }

    public String getSembol() {
        return sayi;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }
}
