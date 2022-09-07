package a1.view;

import javax.swing.JTable;

public class PopularCurrencies extends JTable{
    private String[] columns;
    private Window window;
    private String[][] contents;
    
    public PopularCurrencies(Window window, String[][] contents, String[] columns) {
        super(contents, columns);

        this.window = window;
        this.contents = contents;
        this.columns = columns;

        setBounds(350, 300, 300, 200);
        window.add(this);
    }
}
