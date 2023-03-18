import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int kontojääk = 0;
        String nimi = null;
        String isikukood=null;
        Failihaldur haldur = new Failihaldur();
        List<String[]> read = haldur.loeAndmed();
        for (String[] elem : read) {
            System.out.println(Arrays.toString(elem));
        }
        Scanner sisend = new Scanner(System.in);
        System.out.println("Sisestage PIN/Uue konto loomiseks sisestage 'UUS': ");
        String pin = sisend.nextLine();
        if(pin.equals("UUS")){
            System.out.println("Sisestage uue konto PIN: ");
            String uuspin=sisend.nextLine();
            System.out.println("Sisestage uue konto omaniku ees ja perenimi: ");
            String uusnimi=sisend.nextLine();
            System.out.println("Sisestage uue konto omaniku idkood: ");
            String uusidkood=sisend.nextLine();
            System.out.println("Sisestage raha: ");
            String raha=sisend.nextLine();
            haldur.looKonto(uuspin,uusnimi,uusidkood,raha);
            System.out.println("Konto loodud! Käivitage protsess uuesti et edasisi tegevusi jätkata.");
            return;
        }
        for (String[] elem : read) {
            if (elem[0].equals(pin)) {
                kontojääk = Integer.parseInt(elem[3]);
                nimi = elem[1];
                isikukood=elem[2];
                break;
            }
        }
        Konto konto = new Konto(nimi, kontojääk, pin, isikukood);
        boolean aktiivne = true;
        System.out.println("Tere tulemast pangaautomaati " + konto.getNimi());
        while (aktiivne) {
            Ekraan.valjasta();
            System.out.print("Sisestage toimingu number: ");
            int number = Integer.parseInt(sisend.nextLine());
            switch (number){
                case (1):
                    System.out.println(konto);
                    break;
                case(2):
                    System.out.print("Kui palju soovid raha arvele panna? ");
                    int rahaSisse = Integer.parseInt(sisend.nextLine());
                    konto.kannaRahaArvele(rahaSisse);
                    for (String[] elem2 : read) {
                        if (elem2[2].equals(konto.getIsikukood())) {
                            elem2[3]=Integer.toString(konto.getKontoJääk());
                            read.set(read.indexOf(elem2), elem2);
                            break;
                        }
                    }
                    System.out.println(konto);
                    break;
                case(3):
                    System.out.println("Kui palju soovid raha arvelt võtta?");
                    int rahaVälja = Integer.parseInt(sisend.nextLine());
                    if( rahaVälja > konto.getKontoJääk()){
                        System.out.println("Teie kontolt ei saa nii palju raha välja võtta");
                        break;
                    }
                    konto.võtaRahaArvelt(rahaVälja);
                    for (String[] elem : read) {
                        if (elem[2].equals(konto.getIsikukood())) {
                            elem[3]=Integer.toString(konto.getKontoJääk());
                            read.set(read.indexOf(elem), elem);
                            break;
                        }
                    }
                    for(String[] elem:read){
                        System.out.println(Arrays.toString(elem));
                    }
                    System.out.println(konto);
                    break;
                case(4):
                    System.out.println("Sisestage oma isikukood: ");
                    String kontrollIsikukood = sisend.nextLine();
                    if (kontrollIsikukood.equals(konto.getIsikukood())){

                        }
                    else {
                        System.out.println("Sisestasite vale isikukoodi, viin tagasi menüüsse");
                        break;
                        }
                    System.out.println("Sisestage vana PIN: ");
                    for (int i = 1; i <= 3; i++) {
                        String vanaPin = sisend.nextLine();
                        if (vanaPin.equals(konto.getPin())){
                            System.out.println("Sisestage uus soovitud PIN: ");
                            String uusPin = sisend.nextLine();
                            konto.muudaPin(uusPin);
                            for (String[] elem : read) {
                                if (elem[2].equals(konto.getIsikukood())) {
                                    elem[0]=konto.getPin();
                                    read.set(read.indexOf(elem), elem);
                                    break;
                                }
                            }
                            System.out.println("Teie PIN on muudetud");
                            break;
                        }else {
                            if (i == 3) {
                                System.out.println("Sisestasid PINi kolm korda valesti");
                                break;
                            }
                            System.out.println("Sisestasite vale PINI, teil on veel " + (3 - i) + " võimalust");
                        }
                    }
                break;
                case(5):
                    FileWriter failiKirjutaja = new FileWriter("kviitung.txt");
                    int kviitungiNumber = (int)(Math.random()*((9999-1000)+1))+1000;
                    failiKirjutaja.write("Kviitungi nr: "+ kviitungiNumber +", kliendi nimi: "+konto.getNimi()+", kuupäev: " + LocalDate.now());
                    failiKirjutaja.close();
                    System.out.println("Kviitung kirjutatud");
                    break;

            case(6):
                System.out.println("Peatse kohtumiseni!");
                aktiivne=false;
                break;
            }
        }
        haldur.kirjutaAndmed(read);
    }
}