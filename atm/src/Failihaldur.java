/** Failihaldur tehtud Joonas Tiitsoni poolt
* erinevad funktsioonid et luua ja muuta panga andmeid
* dekrypteerib ja krypteerib andmeid
**/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.io.PrintWriter;
public class Failihaldur {
    private String failitee= "kontod.txt";//failitee andmine, alati sama
    private String tahestik1="QWERTYUIOP{}ASDFGH JKL:ZXCVBNM<>?qwertyuiop[]asdfghjkl'zxcvbnm,./üõöäÜÕÖÄ1234567890!@#$%^&*()_+-=";//kaks tähestikku krüpteerimiseks ja dekrüpteerimiseks
    private String tahestik2=" {}ASDFGHJKL:ZXCVBNM<>?QWERTYUIOP]asdfghjklqwertyuiop[/üõöäÜÕÖÄ1234567890'zxcvbnm,.!@#$%^&*()_+-=";
    public void Failihaldur(){
    }
    //Password,Ees ja perenimi,idkood,rahasumma,krypteerimisnr
    public List<String[]> loeAndmed() throws FileNotFoundException{
        File fail=new File(failitee);
        Scanner scanner = new Scanner(fail);
        List<String[]> krypteeritudkontod=new ArrayList();
        List<String[]> kontod=new ArrayList();
        while(scanner.hasNextLine()){
            krypteeritudkontod.add((scanner.nextLine()).split(";"));//failist andmed krypteeritud kujul otse listi
        }
        for(String[] elem:krypteeritudkontod){
            kontod.add(dekrypteeri(elem));//dekrüpteerib andmed ja lisab kontode listi
        }
        scanner.close();
        return kontod;//tagastab dekrüpteeritud kontod
    }
    public void looKonto(String password, String nimi, String idkood, String rahasumma) throws FileNotFoundException{
        List<String[]> kontod=this.loeAndmed();
        String[] uuskonto={password,nimi,idkood,rahasumma,"0"};
        kontod.add(uuskonto);//lisab kontode listi uue konto andmed
        this.kirjutaAndmed(kontod);//kirjutab andmed faili

    }

    public void kirjutaAndmed(List<String[]> andmed) throws FileNotFoundException{
        PrintWriter fail=new PrintWriter(failitee);
        for(String[] elem:andmed){//käib listi läbi et krüpteerida
            String uussone="";
            elem=krypteeri(elem);//krüpteerib rea
            for(String sone:elem){
                uussone+=sone;
                uussone+=";";//teeb rea String kujule, muidu oli Array
            }
            fail.println(uussone.substring(0,uussone.length()-1));//kirjutab rea faili
        }
        fail.close();
    }
    protected String[] dekrypteeri(String[] rida){
        String[] dekrypteeritudrida=new String[5];
        int dekryptnr=Integer.parseInt(rida[4],10);//võtab võtme et dekrüpteerida
        int tahestikpikkus=tahestik1.length();//võtab tähestiku pikkuse
        for(int i=0;i<4;i++){//läbib rea
            String uussone="";
            int sonepikkus=rida[i].length();
            for(int j=0;j<sonepikkus;j++){
                int taheindeks=tahestik1.indexOf(rida[i].charAt(j))+dekryptnr+j;//leiab uue tähe indeksi tahestik 1-s
                if(tahestikpikkus<=taheindeks){//läheb liiga suure indeksi puhul tähestikku algusesse ja loeb sealt otsast
                    taheindeks-=tahestikpikkus;
                }
                uussone+=tahestik2.charAt(taheindeks);//võtab tähestik 2st tähe mis on dekrypteeritud
            }
            dekrypteeritudrida[i]=uussone;
        }
        dekrypteeritudrida[4]="%d".formatted((int)(Math.random()*10));//suvaline võti järgmiseks krüpteerimiseks
        return dekrypteeritudrida;
    }
    protected String[] krypteeri(String[] rida){//teeb sama mis dekrüpteerimine ainult et vastupidises suunas
        String[] krypteeritudrida=new String[5];
        int kryptnr=Integer.parseInt(rida[4]);
        int tahestikpikkus=tahestik2.length();
        for(int i=0;i<4;i++){
            String uussone="";
            int sonepikkus=rida[i].length();
            for(int j=0;j<sonepikkus;j++){
                int taheindeks=tahestik2.indexOf(rida[i].charAt(j))-kryptnr-j;
                if(taheindeks<=-1){
                    taheindeks+=tahestikpikkus;
                }
                uussone+=tahestik1.charAt(taheindeks);
            }
            krypteeritudrida[i]=uussone;
        }
        krypteeritudrida[4]="%d".formatted(kryptnr);
        return krypteeritudrida;
    }
}
