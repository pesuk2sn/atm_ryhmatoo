public class Konto {
    private String nimi;
    private int kontoJääk;

    public Konto(String nimi, int kontoJääk) {
        this.nimi = nimi;
        this.kontoJääk = kontoJääk;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setKontoJääk(int kontoJääk) {
        this.kontoJääk = kontoJääk;

    }

    public String getNimi() {
        return nimi;
    }

    public int getKontoJääk() {
        return kontoJääk;
    }

    @Override
    public String toString() {
        return nimi + " - sinu kontojääk on : " + kontoJääk +" eurot";
    }
}
