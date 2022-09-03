package a1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Admin extends User{
    public Syst syst;
    public Data data;
    public Admin(Syst syst, Data data) {this.syst = syst; this.data = data;};

    public void addRate(String from, String to, Double rate){
        if(syst.currencyHist.keySet().contains(from)){
            //just write to top of list
            File info = syst.currencyHist.get(from);
            try{
                String text = new String(Files.readString(Paths.get(info.getPath())));
                String toAdd = Syst.currencyFormat(from, to, rate);
                FileWriter overwrite = new FileWriter(info);
                overwrite.write(toAdd + text);
                overwrite.close();
                data.updateCurrencyTable(syst); // Anh doing: Whenever the admin adds a rate, update the currency table
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            //create new file and write price
            try{
                File tmp = new File(syst.dir, from);
                syst.currencyHist.put(from, tmp);
                tmp.createNewFile();

                String toAdd = Syst.currencyFormat(from, to, rate);
                FileWriter writer = new FileWriter(tmp);
                writer.write(toAdd);
                writer.close();
                data.updateCurrencyTable(syst); // Anh doing: Whenever the admin adds a rate, update the currency table
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

    }
}
