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
        System.out.println("Sisestage salasõna: ");
        String otsitavSalasõna = sisend.nextLine();
        for (String[] elem : read) {
            if (elem[0] == otsitavSalasõna) {
                kontojääk = Integer.parseInt(elem[3]);
                nimi = elem[1];
                break;
            } else {
            }
        }


        while (true) {
            Konto konto = new Konto(nimi, kontojääk);
            System.out.println("Tere tulemast pangaautomaati " + konto.getNimi());
            Ekraan.valjasta();
            String kasutajaValik = sisend.nextLine();
            int number = Integer.parseInt(kasutajaValik);
            if (number == 1){
                
            }
            if (number == 6) {
                break;
            }
        }
    }
}