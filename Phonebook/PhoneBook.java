package phonebook;

import javax.swing.*;
import java.io.*;
import java.util.*;

/*
* The following is a phonebook program that manages a phonebook it has the following functions
* retrieve book
* insert
* erase
* upadate
* search
* clear phonebook
* save phonebook to file
*
* The phonebook itself is a TreeMap a self balacing tree. Which is especilly efficient
*
*
* */


public class PhoneBook {
    private TreeMap<String, Integer> book;
//constructor
    public PhoneBook() {
        book = new TreeMap<>();
    }
//clear screen
    public static void clearScreen() {
        System.out.print('\u000c');
    }

    //a shorter print command

    public static void p(Object o) {
        System.out.print(o);
    }

    //getter
    public TreeMap<String, Integer> getBook() {
        return book;
    }


    //print out all the phone book entries
    public void showPhoneBook() {
        clearScreen();
        p("Current Map(Size:" + book.size() + "): \n");
        for (String E : book.keySet()) {
            System.out.printf("%-10s%10s%n", E, book.get(E));
        }
    }

// the user functions utililize joption pane

    //insert a new entry
    public void insert() {

        String name = JOptionPane.showInputDialog(null, "Please Insert a name:");
        String phonenNum = JOptionPane.showInputDialog(null, "Please Insert a phone number:");
        if (name != null && phonenNum != null) {
            book.put(name, Integer.parseInt(phonenNum));
            JOptionPane.showMessageDialog(null, "Insertion complete");
        } else
            JOptionPane.showMessageDialog(null, "Nothing was Entered");
    }

    //erase an entry name and phone number
    public int erase() {

        String name = JOptionPane.showInputDialog(null, "Please Insert a name to Remove:");
        if (name != null) {
            Integer value = book.remove(name);
            JOptionPane.showMessageDialog(null, name + " Has been removed");
            return value;
        } else {
            JOptionPane.showMessageDialog(null, "Nothing was Entered");
        }
        return -1;
    }


    //update  a phone number of a specific contact
    public void updateNumber() {
        String name = JOptionPane.showInputDialog(null, "Please Insert the name of the person whose number you want to update:");
        String phonenNum = JOptionPane.showInputDialog(null, "Please Insert the updated number:");
        if (name != null && phonenNum != null) {
            book.put(name, Integer.parseInt(phonenNum));
            JOptionPane.showMessageDialog(null, "Updated");
        } else
            JOptionPane.showMessageDialog(null, "Nothing was Entered");
    }


    //conduct a search using the name  of the phone owner
    public void searchPhoneNumber(String query) {
        JOptionPane.showMessageDialog( null,
                "Please enter person's name whose number you're looking for in the search Field");
        if (query.equals("")) {
            JOptionPane.showMessageDialog(null, "Nothing was Entered");
            return;
        }

        if (book.containsKey(query))
            JOptionPane.showMessageDialog(null, query + " phone number is: " + book.get(query));
        else
            JOptionPane.showMessageDialog(null, "יThe specified contact was not found");
    }


// write down the Data Structure in a file

    public void saveToFile(String fileName) {
        try {
            FileWriter fWriter = new FileWriter(fileName);
            BufferedWriter bWriter = new BufferedWriter(fWriter);

            for (String name : book.keySet()) {
                String phone = Integer.toString(book.get(name));

                // adds the equals signs help identify a correct entry
                bWriter.write(name + "=" + phone + "\n");
            }
            bWriter.close();
        } catch (IOException err) {
            p("IO Exception");
            err.getMessage();

        }
    }


    // loads all legal entries into phonebook
    public void loadFromFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            String line = "";
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                //mean a legal entry is found
                if (line.contains("=")) {
                    String[] strings = line.split("=");
                    book.put(strings[0].trim(), Integer.parseInt(strings[1].trim()));
                }
            }
            reader.close();

        } catch (IOException err) {
            p("IO Exception");
            err.getMessage();
        }
    }

    //clears the text file from content
    public void clearFile(String filePath) {
        PrintWriter writer = null;

        String answer = JOptionPane.showInputDialog(null, "Are you sure you want to clear the  entire file? (y/n");

        if(answer != null) {
            if (answer.equals("y")) {
                try {
                    writer = new PrintWriter(filePath);
                    writer.print("");
                    writer.close();
                    book.clear();
                    JOptionPane.showMessageDialog(null, "Erased");
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                } // end of try catch

                }else if(answer.equals("n")){
                    JOptionPane.showMessageDialog(null, "Operation cancelled");
                }else
                    JOptionPane.showMessageDialog(null, "Wrong Character");

        }

    }

}
