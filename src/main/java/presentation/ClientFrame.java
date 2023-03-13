package presentation;


/**
 *  Clasa corespunzatoare frameului client
 */

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;

import static presentation.Controller.findClientIds;
import static presentation.Controller.insertClient;

public class ClientFrame extends JFrame {
    Container container = getContentPane();

    private JTextField welcomeText;
    private JButton jcomp2;
    private JButton jcomp3;
    private JButton jcomp4;
    private JButton jcomp5;
    public ClientFrame() {
        container.setLayout(new GridLayout(4,2,0,0));

        jcomp2 = new JButton ("Adauga Client");
        jcomp3 = new JButton ("Editeaza datele unui client");
        jcomp4 = new JButton ("Vizualizeaza clienti");
        jcomp5 = new JButton ("Sterge un client");

        jcomp2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugaClient(e);
            }
        });
        jcomp3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClient(e);
            }
        });
        jcomp4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectClient( e);
            }
        });
        jcomp5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient( e);
            }
        });

        this.setTitle("Client Frame");
        this.setVisible(true);
        this.setBounds(500, 500, 500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        addComponents();

    }

    public void addComponents() {
        container.add(jcomp2);
        container.add(jcomp3);
        container.add(jcomp4);
        container.add(jcomp5);

    }

    private void adaugaClient(ActionEvent e){
        JFrame frameAdauga = new JFrame();
        JPanel grid = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NORTHWEST;

        constraints.gridx = 2;
        constraints.gridy = 0;

        grid.add(getEmptyLabel(new Dimension(150,150)), constraints);
        JLabel titlu = new JLabel("Introduceti valorile de inserat");
        grid.add(titlu,constraints);
        ++constraints.gridy;
        constraints.gridx = 0;

        JLabel id = new JLabel("ID");
        grid.add(id,constraints);
        ++constraints.gridy;

        JTextField idText = new JTextField();
        constraints.ipadx =100;
        grid.add(idText,constraints);
        ++constraints.gridx;
        --constraints.gridy;

        constraints.ipadx =0;
        JLabel name = new JLabel("name");
        grid.add(name,constraints);
        ++constraints.gridy;

        JTextField nameText = new JTextField();
        constraints.ipadx =100;
        grid.add(nameText,constraints);
        ++constraints.gridx;
        --constraints.gridy;

        constraints.ipadx =0;
        JLabel address = new JLabel("address");
        grid.add(address,constraints);
        ++constraints.gridy;

        JTextField addressText = new JTextField();
        constraints.ipadx =150;
        grid.add(addressText,constraints);
        ++constraints.gridx;
        --constraints.gridy;

        constraints.ipadx =0;
        JLabel age = new JLabel("age");
        grid.add(age,constraints);
        ++constraints.gridy;

        JTextField ageText = new JTextField();
        constraints.ipadx =100;
        grid.add(ageText,constraints);
        ++constraints.gridx;
        --constraints.gridy;

        constraints.ipadx =0;
        JLabel email = new JLabel("email");
        grid.add(email,constraints);
        ++constraints.gridy;

        JTextField emailText = new JTextField();
        constraints.ipadx =100;
        grid.add(emailText,constraints);

        JButton buton = new JButton("Adauga");
        buton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String[] fields = new String[5];
                fields[0] = idText.getText();
                fields[1] = nameText.getText();
                fields[2] = addressText.getText();
                fields[3] = ageText.getText();
                fields[4] = emailText.getText();
                if(insertClient(fields) == true)
                    FrameMesaj("Client adaugat cu succes");
                else FrameMesaj("Eroare. Introduceti date valide");
            }
        });
        constraints.gridy++;
        constraints.gridy++;
        constraints.gridx=2;
        constraints.ipadx=100;
        grid.add(buton,constraints);
        frameAdauga.setContentPane(grid);
        frameAdauga.pack();
        frameAdauga.setLocationRelativeTo(null);
        frameAdauga.setVisible(true);
    }

    private void updateClient(ActionEvent e){
        Vector <Integer> jcomp1Items = findClientIds();
        JFrame frameUpdate = new JFrame();
        JPanel grid = new JPanel();
        JComboBox jcom1;
        JLabel jcom2;
        JRadioButton jcom3;
        JRadioButton jcom4;
        JRadioButton jcom5;
        JRadioButton jcom6;
        JLabel jcom7;
        JLabel jcom8;
        JLabel jcom9;
        JTextField jcom10;
        JTextField jcom11;
        JTextField jcom12;
        JTextField jcom13;
        JButton jcom14;

        //construct components
        jcom1 = new JComboBox(jcomp1Items);
        jcom2 = new JLabel ("name");
        jcom3 = new JRadioButton ();
        jcom4 = new JRadioButton ();
        jcom5 = new JRadioButton ();
        jcom6 = new JRadioButton ();
        jcom7 = new JLabel ("address");
        jcom8 = new JLabel ("age");
        jcom9 = new JLabel ("email");
        jcom10 = new JTextField (5);
        jcom11 = new JTextField (5);
        jcom12 = new JTextField (5);
        jcom13 = new JTextField (5);
        jcom14 = new JButton ("Update");

        grid.setPreferredSize (new Dimension (397, 230));
        grid.setLayout (null);


        grid.add (jcom1);
        grid.add (jcom2);
        grid.add (jcom3);
        grid.add (jcom4);
        grid.add (jcom5);
        grid.add (jcom6);
        grid.add (jcom7);
        grid.add (jcom8);
        grid.add (jcom9);
        grid.add (jcom10);
        grid.add (jcom11);
        grid.add (jcom12);
        grid.add (jcom13);
        grid.add (jcom14);

        jcom1.setBounds (135, 30, 100, 25);
        jcom2.setBounds (85, 90, 60, 25);
        jcom3.setBounds (20, 90, 50, 25);
        jcom4.setBounds (20, 115, 55, 25);
        jcom5.setBounds (20, 140, 50, 25);
        jcom6.setBounds (20, 165, 50, 25);
        jcom7.setBounds (85, 115, 65, 25);
        jcom8.setBounds (85, 140, 65, 25);
        jcom9.setBounds (85, 165, 60, 25);
        jcom10.setBounds (155, 85, 100, 25);
        jcom11.setBounds (155, 110, 100, 25);
        jcom12.setBounds (155, 135, 100, 25);
        jcom13.setBounds (155, 160, 100, 25);
        jcom14.setBounds (140, 200, 100, 25);


        jcom14.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                int id = (int) jcom1.getSelectedItem();
                String[] fields = new String[20];
                int count = 0;
                if(jcom3.isSelected()){
                    fields[count] = "name";
                    count++;
                    fields[count] = jcom10.getText();
                    count++;
                }
                if(jcom4.isSelected()){
                    fields[count] = "address";
                    count++;
                    fields[count] = jcom11.getText();
                    count++;
                }
                if(jcom5.isSelected()){
                    fields[count] = "age";
                    count++;
                    fields[count] = jcom12.getText();
                    count++;
                }
                if(jcom6.isSelected()){
                    fields[count] = "email";
                    count++;
                    fields[count] = jcom13.getText();
                    count++;
                }
                if(Controller.updateClient(id,fields)== true) FrameMesaj("Client updatat cu succes");
                else FrameMesaj("Eroare. Introduceti date valide");
            }
        });

        frameUpdate.setContentPane(grid);
        frameUpdate.pack();
        frameUpdate.setLocationRelativeTo(null);
        frameUpdate.setVisible(true);
    }

    private void deleteClient(ActionEvent e){
        Vector <Integer> jcomp1Items = findClientIds();
        JFrame frameUpdate = new JFrame();
        JPanel grid = new JPanel();
        JComboBox jcom1 = new JComboBox(jcomp1Items);

        JButton jcom14;

        //construct components
        jcom14 = new JButton ("Delete");

        grid.setPreferredSize (new Dimension (397, 230));
        grid.setLayout (null);

        grid.add(jcom1);
        grid.add (jcom14);

        jcom1.setBounds (135, 30, 100, 25);
        jcom14.setBounds (140, 200, 100, 25);


        jcom14.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                int id = (int) jcom1.getSelectedItem();
                Controller.deleteClient(id);
            }
        });

        frameUpdate.setContentPane(grid);
        frameUpdate.pack();
        frameUpdate.setLocationRelativeTo(null);
        frameUpdate.setVisible(true);
    }

    private void selectClient(ActionEvent e){
        JFrame selectFrame = new JFrame();
        JTable table;
        String[] columns = Controller.getColoaneClient();
        String[][] data = Controller.getDataClient(columns.length);
        table = new JTable(data, columns);
        table.setBounds(30,40,200,300);

        JScrollPane sp = new JScrollPane(table);
        selectFrame.add(sp);
        selectFrame.setSize(500,500);
        selectFrame.setLocationRelativeTo(null);
        selectFrame.setVisible(true);

    }

    private JLabel getEmptyLabel(Dimension d) {
        JLabel label = new JLabel();
        label.setPreferredSize(d);
        return label;
    }

    private void FrameMesaj(String mesajText){
        JFrame frameMesaj = new JFrame();
        JPanel grid = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        grid.add(getEmptyLabel(new Dimension(300,80)), constraints);
        constraints.gridy = 0;
        constraints.gridx = 0;

        JLabel mesaj = new JLabel(mesajText);
        grid.add(mesaj,constraints);
        ++constraints.gridy;
        JButton butonClose = new JButton("OK");
        butonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameMesaj.dispatchEvent(new WindowEvent(frameMesaj, WindowEvent.WINDOW_CLOSING));
            }
        });
        grid.add(butonClose,constraints);

        frameMesaj.setContentPane(grid);
        frameMesaj.pack();
        frameMesaj.setLocationRelativeTo(null);
        frameMesaj.setVisible(true);
    }
}