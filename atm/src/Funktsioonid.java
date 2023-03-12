public class Funktsioonid {

    public static int kannaRahaArvele(int sisestatudRaha, Konto konto){
        konto.setKontoJääk(konto.getKontoJääk()+sisestatudRaha);
        return konto.getKontoJääk();
    }
}
