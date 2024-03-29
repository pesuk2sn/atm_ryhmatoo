/*********************************************************************
 *
 *
 * Projekti autorid: Robert Juhkam, Joonas Tiitson
 *
 * Projekti kirjeldus: Projektiks on töötav pangaautomaat(ATM) erinevate funktsioonidega,
 * kus saab kasutaja teha erinevaid toiminguid. Lisaks on programmi sisse implementeeritud krüpteerimine ja kontode loomine.
 * Programmi käivitamise alguses saab kas siseneda juba valmis tehtyd kasutaja kontoga  või teha uue konto.
 * ATM funktsioonide kasutamine käib numbritega(iga fun. on kindla numbri all).
 *
 * Klasside eesmärgid: Ekraan - prindib välja pangaautomaadi teksti
 * Konto - isendi konto loomiseks mõeldud klass
 * Failihaldur - failist lugemine, krüpteerimine, dekrüpteerimine ja uue konto loomine ja selle kirjutamine faili
 * Main - Põhifunktsioonide ja nende kasutamine
 * Olulisemad meetodid ja kirjeldused on toodud välja kommentaarina meetodi juures
 *
 * Protsess - mõlemad tegelesime enda ülesannetega iseseisvalt. Kuna meie ülesanded olid küllaltki erinevad, ei pidanud üksteise järel ootama.
 * Alguses tegi Joonas valmis esialgse faili, mis oli krüpteeritud ja kus olid kontode andmed. Nendega sai Robert edasi toimetada, lugedes need konto isendisse.
 * Seejärel sai selle isendiga erinevaid funktsioone luua, mida tavaliselt ATMides kasutatakse
 *
 *Joonase tegevused:
 *failist lugemine(0.5h)
 *info krüpteerimine ja dekrüpteerimine(3h)
 *faili kirjutamine(0.5h)
 *konto loomine(0.5h)
 *PIN koodi muutmine ATM-is(0.5h)
 *
 *Roberti tegevused:
 *ATM teavitustekstid(0.5h)
 *konto isendi loomine sisestuse järgi(0.3h)
 *erinevad tegevused kontol(raha sisse ja välja, jäägi vaatamine)(3h)
 *kviitungi kirjutamine teksti(1h)
 *
 * Mured: kuna võtsime jõukohase teema, siis probleeme projekti tegemisel ei esinenud.
 * Samuti on Joonas sarnase krüpteerimise projektiga juba varem tegelenud.
 *
 * Hinnang: Mõlemad rühmaliikmed jäid oma tulemusega rahule. Programm teeb seda mida vajab. Projekt pole nii lihtne aga samas ka mitte liiga raske.
 * Arvame, et saime hästi hakkama meetodite koostamisega. Paremini oleks saanud teha main klassi, sest hetkel on see liiga kohmakas ja raske on järge ajada.
 * Saaks teha veel klasse, et sinna viia main klassis kasutatavaid meetodeid.
 *
 * Testimine: programmi sai testitud klassi kaupa. Kuna klasse sai eraldi iseseisvalt testida, polnud vaja eeltingimusteks teisi klasse.
 * Kui programmi mingi klass töötas, siis leidis see kohe ka põhiprogrammis rakendust. Igasuguste muudatuste tegemisel kasutasime Githubi, et kursis olla, mida teine rühmaliige tegi.
 *
 *
*********************************************************************/

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
        List<String[]> read = haldur.loeAndmed(); //Loeb iga rea listi
        for (String[] elem : read) {
            System.out.println(Arrays.toString(elem));
        }
        Scanner sisend = new Scanner(System.in);
        System.out.println("Sisestage PIN/Uue konto loomiseks sisestage 'UUS': ");
        String pin = sisend.nextLine();
        if(pin.equals("UUS")){ //uue kasutaja loomine
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
        for (String[] elem : read) { //Leiab otsitava PINi järgi konto andmed
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
        System.out.println();
        System.out.println();
        Ekraan.valjasta();
        while (aktiivne) {
            System.out.print("Sisestage toimingu number: ");
            int number = Integer.parseInt(sisend.nextLine());
            switch (number){
                case (1): //Konto jäägi kuvamine
                    System.out.println(konto);
                    break;
                case(2): //Raha arvele panemine
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
                case(3): //Raha arvelt võtmine
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
                    System.out.println(konto);
                    break;
                case(4): //PIN koodi muutmine
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
                case(5): //Kviitungi kirjutamine tekstifaili
                    FileWriter failiKirjutaja = new FileWriter("kviitung.txt");
                    int kviitungiNumber = (int)(Math.random()*((9999-1000)+1))+1000;
                    failiKirjutaja.write("Kviitungi nr: "+ kviitungiNumber +", kliendi nimi: "+konto.getNimi()+", kuupäev: " + LocalDate.now());
                    failiKirjutaja.close();
                    System.out.println("Kviitung kirjutatud");
                    break;

            case(6): //Lõpetab main funktsiooni
                System.out.println("Peatse kohtumiseni!");
                aktiivne=false;
                break;
            }
        }
        haldur.kirjutaAndmed(read); //kirjutab uuesti andmed üle
    }
}