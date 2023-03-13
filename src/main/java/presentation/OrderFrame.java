package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Vector;

import static presentation.Controller.*;

public class OrderFrame extends JFrame{


    Container container = getContentPane();

    private JTextField welcomeText;
    private JButton jcomp2;
    private JButton jcomp3;
    public OrderFrame() {
        container.setLayout(new GridLayout(2,2,0,0));

        jcomp2 = new JButton ("Comanda");
        jcomp3 = new JButton ("Vizualizeaza Comenzi");


        jcomp2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugaOrder(e);
            }
        });
        jcomp3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectOrder(e);
            }
        });

        this.setTitle("Order Frame");
        this.setVisible(true);
        this.setBounds(500, 500, 500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        addComponents();

    }
    public void addComponents() {
        container.add(jcomp2);
        container.add(jcomp3);

    }



    public void adaugaOrder(ActionEvent e){
        JFrame adaugaFrame = new JFrame();
        JPanel grid = new JPanel();
        Vector<String> jcomp4Items = findClientNames();
        Vector<String> jcomp5Items = findProductNames();
        JLabel jcomp1;
        JLabel jcomp2;
        JLabel jcomp3;
        JComboBox jcomp4;
        JComboBox jcomp5;
        JTextField jcomp6;
        JButton jcomp7;

        jcomp1 = new JLabel ("Client");
        jcomp2 = new JLabel ("Produs");
        jcomp3 = new JLabel ("Cantitate");
        jcomp4 = new JComboBox (jcomp4Items);
        jcomp5 = new JComboBox (jcomp5Items);
        jcomp6 = new JTextField (5);
        jcomp7 = new JButton ("Cumpara");
        jcomp7.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String[] fields = new String[2];
                fields[0] = (String) jcomp4.getSelectedItem();
                fields[1] = (String) jcomp5.getSelectedItem();
                int quant = Integer.parseInt(jcomp6.getText());
                int pret = insertOrder(fields,quant);
                if(pret > 0){
                    FrameMesaj("Comanda s-a efectuat cu succes");
                    bill(fields, quant, pret);
                }
                else{
                    FrameMesaj("Eroare la efectuarea comenzii. Nu exista destule produse pe stoc");
                }
            }
        });

        grid.setPreferredSize (new Dimension (373, 322));
        grid.setLayout (null);

        grid.add (jcomp1);
        grid.add (jcomp2);
        grid.add (jcomp3);
        grid.add (jcomp4);
        grid.add (jcomp5);
        grid.add (jcomp6);
        grid.add (jcomp7);


        jcomp1.setBounds (60, 40, 40, 25);
        jcomp2.setBounds (160, 40, 45, 25);
        jcomp3.setBounds (260, 40, 60, 25);
        jcomp4.setBounds (20, 75, 100, 25);
        jcomp5.setBounds (135, 75, 100, 25);
        jcomp6.setBounds (250, 75, 100, 25);
        jcomp7.setBounds (135, 140, 100, 25);

        adaugaFrame.setContentPane(grid);
        adaugaFrame.pack();
        adaugaFrame.setLocationRelativeTo(null);
        adaugaFrame.setVisible(true);

     }

    private void selectOrder(ActionEvent e){
        JFrame selectFrame = new JFrame();
        JTable table;
        String[] columns = Controller.getColoaneOrder();
        String[][] data = Controller.getDataOrder(columns.length);
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


