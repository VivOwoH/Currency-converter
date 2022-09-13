package a1.view;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class PopularCurrencies extends JTable{
    private String[] columns;
    private Window window;
    private String[][] contents;
    private DefaultTableModel model;
    
    public PopularCurrencies(Window window, String[][] contents, String[] columns) {
        // super(contents, columns);

        this.window = window;
        this.contents = contents;
        this.columns = columns;

        this.model = new DefaultTableModel(contents, columns);
        setModel(model);

        setBounds(350, 300, 300, 200);

        // JScrollPane sp = new JScrollPane(this);

        // window.add(sp);
    }
}

// class RowHeaderRenderer extends JLabel implements ListCellRenderer {

//     RowHeaderRenderer(JTable table) {
//       JTableHeader header = table.getTableHeader();
//       setOpaque(true);
//       setBorder(UIManager.getBorder("TableHeader.cellBorder"));
//       setHorizontalAlignment(CENTER);
//       setForeground(header.getForeground());
//       setBackground(header.getBackground());
//       setFont(header.getFont());
//     }
  
//     public Component getListCellRendererComponent(JList list, Object value,
//         int index, boolean isSelected, boolean cellHasFocus) {
//       setText((value == null) ? "" : value.toString());
//       return this;
//     }
//   }
