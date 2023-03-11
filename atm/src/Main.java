import java.io.FileNotFoundException;
import java.util.List;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Failihaldur haldur=new Failihaldur();
        List<String[]> read= haldur.loeAndmed();
        for(String[] elem:read){
            System.out.println(Arrays.toString(elem));
        }
        haldur.kirjutaAndmed(read);
        //haldur.looKonto("password3","Karmen Kass","12039428999","1200");
        read=haldur.loeAndmed();
        for(String[] elem:read){
            System.out.println(Arrays.toString(elem));
        }
    }
}
