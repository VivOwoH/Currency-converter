package a1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Admin extends User {

    public Admin(Data data, Syst syst) {
        super(data, syst);
    }

    public void addRate(String from, String to, Double rate) {
        if (syst.getCurrencyHist().keySet().contains(from)) {
            // just write to top of list
            File currentFile = syst.getCurrencyHist().get(from);
            
            try {
                String text = new String(Files.readString(Paths.get(currentFile.getPath())));
                String toAdd = Syst.currencyFormat(from, to, rate);
                FileWriter overwrite = new FileWriter(currentFile);
                overwrite.write(toAdd + text);
                overwrite.close();
                db.updateCurrencyTable(syst); // Whenever the admin adds a rate, update the currency table
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // create new file and write price
            try {
                File tmp = new File(syst.getDirectory(), from);
                syst.getCurrencyHist().put(from, tmp);
                tmp.createNewFile();
                db.addCountryToIdx(from);

                String toAdd = Syst.currencyFormat(from, to, rate);
                FileWriter writer = new FileWriter(tmp);
                writer.write(toAdd);
                writer.close();
                db.updateCurrencyTable(syst); //Whenever the admin adds a rate, update the currency table
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
