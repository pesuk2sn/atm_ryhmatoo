import java.io.FileNotFoundException;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int kontojääk = 0;
        String nimi = null;
        Failihaldur haldur = new Failihaldur();
        List<String[]> read = haldur.loeAndmed();
        for (String[] elem : read) {
            System.out.println(Arrays.toString(elem));
        }
        haldur.kirjutaAndmed(read);
        //haldur.looKonto("password3","Karmen Kass","12039428999","1200");
        read = haldur.loeAndmed();
        for (String[] elem : read) {
            System.out.println(Arrays.toString(elem));
        }
        Scanner sisend = new Scanner(System.in);
        System.out.println("Sisestage PIN: ");
        String pin = sisend.nextLine();
        for (String[] elem : read) {
            if (elem[0].equals(pin)) {
                kontojääk = Integer.parseInt(elem[3]);
                nimi = elem[1];

                break;
            } else {
            }
        }

        Konto konto = new Konto(nimi, kontojääk, pin );
        boolean aktiivne = true;
        System.out.println("Tere tulemast pangaautomaati " + konto.getNimi());
        while (aktiivne) {
            Ekraan.valjasta();
            System.out.println("Sisestage toimingu number: ");
            int number = Integer.parseInt(sisend.nextLine());
            switch (number){
                case (1):
                System.out.println(konto.getKontoJääk());
                break;
                case(2):
                System.out.println("Kui palju soovid raha arvele panna?");
                int rahaSisse = Integer.parseInt(sisend.nextLine());
                konto.kannaRahaArvele(rahaSisse);
                konto.toString();
                break;
            case(3):
                System.out.println("Kui palju soovid raha arvelt võtta?");
                int rahaVälja = Integer.parseInt(sisend.nextLine());
                if( rahaVälja > konto.getKontoJääk()){
                    System.out.println("Teie kontolt ei saa nii palju raha välja võtta");
                    break;
                }
                konto.võtaRahaArvelt(rahaVälja);
                konto.toString();
                break;
                case(4):
                System.out.println("Sisestage vana PIN: ");
                for (int i = 1; i <= 3; i++) {
                    String vanaPin = sisend.nextLine();
                    if (vanaPin.equals(konto.getPin())){
                        System.out.println("Sisestage uus soovitud PIN: ");
                        String uusPin = sisend.nextLine();
                        konto.muudaPin(uusPin);
                        System.out.println("Teie PIN on muudetud");
                        break;
                    }
                    else {
                        if (i==3){
                            System.out.println("Sisestasid PINi kolm korda valesti");
                            break;
                        }
                        System.out.println("Sisestasite vale PINI, teil on veel " + (3-i) + " võimalust");
                    }
                }
                break;
            case(6):
                System.out.println("Peatse kohtumiseni!");
                aktiivne=false;
                break;
            }
        }
    }
}