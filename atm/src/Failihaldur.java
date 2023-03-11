import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.io.PrintWriter;
public class Failihaldur{
    private String failitee= "kontod.txt";
    private String tahestik1="QWERTYUIOP{}ASDFGH JKL:ZXCVBNM<>?qwertyuiop[]asdfghjkl'zxcvbnm,./üõöäÜÕÖÄ1234567890!@#$%^&*()_+-=";
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
            krypteeritudkontod.add((scanner.nextLine()).split(";"));
        }
        for(String[] elem:krypteeritudkontod){
            kontod.add(dekrypteeri(elem));
        }
        scanner.close();
        return kontod;
    }
    public void looKonto(String password, String nimi, String idkood, String rahasumma) throws FileNotFoundException{
        List<String[]> kontod=this.loeAndmed();
        String[] uuskonto={password,nimi,idkood,rahasumma,"0"};
        kontod.add(uuskonto);
        this.kirjutaAndmed(kontod);

    }

    public void kirjutaAndmed(List<String[]> andmed) throws FileNotFoundException{
        PrintWriter fail=new PrintWriter(failitee);
        for(String[] elem:andmed){
            String uussone="";
            elem=krypteeri(elem);
            for(String sone:elem){
                uussone+=sone;
                uussone+=";";
            }
            fail.println(uussone.substring(0,uussone.length()-1));
        }
        fail.close();
    }
    protected String[] dekrypteeri(String[] rida){
        String[] dekrypteeritudrida=new String[5];
        int dekryptnr=Integer.parseInt(rida[4],10);
        int tahestikpikkus=tahestik1.length();
        for(int i=0;i<4;i++){
            String uussone="";
            int sonepikkus=rida[i].length();
            for(int j=0;j<sonepikkus;j++){
                int taheindeks=tahestik1.indexOf(rida[i].charAt(j))+dekryptnr+j;
                if(tahestikpikkus<=taheindeks){
                    taheindeks-=tahestikpikkus;
                }
                uussone+=tahestik2.charAt(taheindeks);
            }
            dekrypteeritudrida[i]=uussone;
        }
        dekrypteeritudrida[4]="%d".formatted((int)(Math.random()*10));
        return dekrypteeritudrida;
    }
    protected String[] krypteeri(String[] rida){
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
