package phonebook;

import javax.swing.*;

public class PhoneFrame extends JFrame {

    public PhoneFrame() {
        super();

        setTitle("Phone Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setVisible(true);

        //pack();
        PhoneInterface pc = new PhoneInterface();
        pc.setOpaque(true);
        add(pc);
        revalidate();
        repaint();
    }

}
