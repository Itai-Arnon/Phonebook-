package phonebook;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class PhoneInterface extends JPanel {
    private static String fileName = "PhoneBook.txt";
    private JButton add_entry, erase_entry, update_phone, search_phone, load_table, clear_table , clear_file;
    private PhoneBook pb;
    private JTable txtTable;
    private PhoneBook phoneBook;
    private JScrollPane txtScrollPane;
    DefaultTableModel tableModel;
    private JTextField searchField;


    public PhoneInterface() {
        pb = new PhoneBook();
        // box layout assures every part of the gui structure is laid out as its defined size
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new GridLayout(3,1));


        //table setup
        createTable();
        //provides dimensions of the Table
        txtTable.setPreferredScrollableViewportSize(new Dimension(600, 900));
        //acts to fill the entire allocated space with the table
        txtTable.setFillsViewportHeight(true);
        txtScrollPane = new JScrollPane(txtTable);
        add(txtScrollPane);

        //sets up search fields
        JPanel consolePanel = new JPanel(new GridLayout(2, 1));
        JPanel upperConsole = new JPanel(new GridLayout(1, 5));
        UpperConsoleListener consoleListener = new UpperConsoleListener();
        searchField = new JTextField(15);

        //adds the different buttons
        search_phone = new JButton("Search Contact");
        search_phone.addActionListener(consoleListener);

        load_table = new JButton("Load Table");
        load_table.addActionListener(consoleListener);

        clear_table = new JButton("Clear Table");
        clear_table.addActionListener(consoleListener);

        clear_file = new JButton("Clear File");
        clear_file.addActionListener(consoleListener);

        //adds the buttons to the GUI
        upperConsole.add(load_table);
        upperConsole.add(clear_table);
        upperConsole.add(clear_file);
        upperConsole.add(search_phone);
        upperConsole.add(searchField);


//sets up buttons and listeners of the lower console
        add_entry = new JButton("Add Entry");
        erase_entry = new JButton("Erase Entry");
        update_phone = new JButton("Update Phone");

        JPanel buttons = new JPanel(new GridLayout(1, 3));
        buttons.add(add_entry);
        buttons.add(erase_entry);
        buttons.add(update_phone);

        consolePanel.add(upperConsole);
        consolePanel.add(buttons);
        buttonsListener bListener = new buttonsListener();
        add_entry.addActionListener(bListener);
        erase_entry.addActionListener(bListener);
        update_phone.addActionListener(bListener);
        JPanel filler = new JPanel();
        filler.setVisible(false);
        //Color T = new Color(0,0,0,0);
        //filler.setBackground(T);
        this.add(filler);
        this.add(consolePanel);
    }

//setups a new jtable

    private void createTable() {
        txtTable = new JTable();
        txtTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        defineDefaultTableModel();
        defineAndRepaintTable();

    }

    //initializes and updates the table

    private void defineAndRepaintTable() {
        txtTable.setModel(tableModel);
        txtTable.setRowSelectionAllowed(true);
        txtTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        txtTable.revalidate();
        txtTable.repaint();
    }

    // abstract model is not flexible enough
    private void defineDefaultTableModel() {
        //if it has not define before
        if (tableModel == null) {
            Object[] col_names = {"Name", "Number"};
            //2nd parameter is number of rows will be added later
            tableModel = new DefaultTableModel(col_names, 0);
        }
    }

    //will receive data from the Model Tree Map and ultimately  the Table
    //invoked when the buttons are in use
    private void modelUpdate(PhoneBook pBook) {
        cleanModel();
        pBook.loadFromFile(fileName);
        //using the instance Tree Map
        Set<String> transfer = pBook.getBook().keySet();
        for (String E : transfer) {
            Object[] row = {E, pBook.getBook().get(E)};
            tableModel.addRow(row);
        }
    }

    //erases the table model
    private void cleanModel() {

        while (tableModel != null && tableModel.getRowCount() != 0) {
            tableModel.removeRow(0);
        }


    }

    //designed to activate: Add entry , Erase entry and Update Entry
    public class buttonsListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == add_entry) {
                pb.loadFromFile(fileName);
                pb.insert();
                pb.saveToFile(fileName);
                modelUpdate(pb);
                defineAndRepaintTable();

            }else if (e.getSource() == erase_entry) {
                pb.loadFromFile(fileName);
                pb.erase();
                pb.saveToFile(fileName);
                modelUpdate(pb);
                defineAndRepaintTable();

            }else {
                pb.loadFromFile(fileName);
                pb.updateNumber();
                pb.saveToFile(fileName);
                modelUpdate(pb);
                defineAndRepaintTable();
            }
        }
    }
    // code for searching a phone number, clearing the table, loading it again
    //  & deleting the text file
    private class UpperConsoleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
              if (e.getSource() == search_phone) {
                    String query = searchField.getText();
                    pb.searchPhoneNumber(query);
              }
              else if (e.getSource() == clear_table) {
                    cleanModel();
              }else if(e.getSource() == load_table) {
                    modelUpdate(pb);

              }else {
                  pb.clearFile(fileName);
                  PhoneInterface.this.revalidate();
                  PhoneInterface.this.repaint();
              }

            }
        }


    }



