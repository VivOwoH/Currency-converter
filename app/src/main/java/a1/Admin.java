package a1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Admin extends User{
    public static HashMap<String, File> currencyHist = new HashMap<>();
    public Admin() {}

    public void addRate(String from, String to, Double rate){
        if(currencyHist.keySet().contains(from)){
            //just write to top of list
            File info = currencyHist.get(from);
            try{
                String text = new String(Files.readString(Paths.get(info.getPath())));
                String toAdd = App.currencyFormat(from, to, rate);
                FileWriter overwrite = new FileWriter(info);
                overwrite.write(toAdd + text);
                overwrite.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            //create new file

        }

    }
}
