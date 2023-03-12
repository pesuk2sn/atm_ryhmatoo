public class Konto {
    private String nimi;
    private int kontoJääk;
    private String pin;


    public Konto(String nimi, int kontoJääk, String pin) {
        this.nimi = nimi;
        this.kontoJääk = kontoJääk;
        this.pin = pin;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setKontoJääk(int kontoJääk) {
        this.kontoJääk = kontoJääk;

    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNimi() {
        return nimi;
    }

    public int getKontoJääk() {
        return kontoJääk;
    }
    public  int kannaRahaArvele(int sisestatudRaha){
        this.kontoJääk += sisestatudRaha;
        return this.kontoJääk;
    }
    public int võtaRahaArvelt(int väljastatudRaha){
        this.kontoJääk -= väljastatudRaha;
        return this.kontoJääk;
    }
    public String muudaPin(String uusPin){
        this.pin =uusPin;
        return this.pin;
    }
    @Override
    public String toString() {
        return nimi + " - sinu kontojääk on : " + kontoJääk +" eurot";
    }
}
