import javax.swing.*;
import java.awt.*;

public class Form extends JPanel {

    private JFrame myFrame;
    private JButton startButton, resetButton, setBoardButton, MCbutton, energiaButton;
    private JPanel buttonPanel, graphicPanel, energyPanel;
    private JComboBox wyborSasiedztwa;
    private JSpinner zarodki, promien, X, Y, iteracje;
    private JRadioButton s100, s900, s300, s, period;
    private JRadioButton losowe, zPromieniem, jednorodne;

    void setAndShowGUI() {
        myFrame = new JFrame("Rozrost Ziaren");
        myFrame.setLayout(new BorderLayout());
        myFrame.setSize(2000, 960);

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(200, myFrame.getHeight()));

        graphicPanel = new JPanel(new BorderLayout());
        graphicPanel.setPreferredSize(new Dimension( 920,920));

        energyPanel = new JPanel(new BorderLayout());
        energyPanel.setPreferredSize(new Dimension(920,920));
        //energyPanel.setBackground(Color.BLUE);

        //buttons
        setBoardButton = new JButton("Ustaw planszę");
        setBoardButton.setPreferredSize(new Dimension(150,30));
        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(150,50));
        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(150,50));
        MCbutton = new JButton("Monte Carlo");
        MCbutton.setPreferredSize(new Dimension(150,50));
        energiaButton = new JButton("Wizualizacja Energii");
        energiaButton.setPreferredSize(new Dimension(150,50));

        String[] sasiedztwa = new String[] {"Moore", "Von Neumann", "Heksagonalne lewe",
                "Heksagonalne prawe", "Heksagonalne losowe", "Pentagonalne losowe"};
        wyborSasiedztwa = new JComboBox<>(sasiedztwa);

        //spinners
        SpinnerModel spinnerModel = new SpinnerNumberModel(20, 10, 500, 10);
        zarodki = new JSpinner(spinnerModel);
        zarodki.setPreferredSize(new Dimension(150,30));

        SpinnerModel spinnerModel1 = new SpinnerNumberModel(10,1,100,10);
        promien = new JSpinner(spinnerModel1);
        promien.setPreferredSize(new Dimension(150,30));

        SpinnerModel spinnerModel2 = new SpinnerNumberModel(5, 1, 100, 1);
        SpinnerModel spinnerModel3 = new SpinnerNumberModel(5, 1, 100, 1);
        X = new JSpinner(spinnerModel2);
        Y = new JSpinner(spinnerModel3);
        X.setPreferredSize(new Dimension(80,20));
        Y.setPreferredSize(new Dimension(80,20));

        SpinnerModel spinnerModel4 = new SpinnerNumberModel(10, 10, 1000,10);
        iteracje = new JSpinner(spinnerModel4);
        iteracje.setPreferredSize(new Dimension(150,20));

        //button groups
        jednorodne = new JRadioButton("Jednorodne");
        losowe = new JRadioButton("Losowe");
        zPromieniem = new JRadioButton("Z promieniem");
        jednorodne.setPreferredSize(new Dimension(150,20));
        losowe.setPreferredSize(new Dimension(150,20));
        zPromieniem.setPreferredSize(new Dimension(150,20));

        ButtonGroup sposobZarodkowania = new ButtonGroup();
        sposobZarodkowania.add(jednorodne);
        sposobZarodkowania.add(losowe);
        sposobZarodkowania.add(zPromieniem);

        Dimension checkBoxSize = new Dimension(150,20);

        s100 = new JRadioButton("100x100");
        s900 = new JRadioButton("900x900");
        s300 = new JRadioButton("300x300");
        s = new JRadioButton("300x100");
        s100.setPreferredSize(checkBoxSize);
        s300.setPreferredSize(checkBoxSize);
        s900.setPreferredSize(checkBoxSize);
        s.setPreferredSize(checkBoxSize);

        period = new JRadioButton("Periodyczne");
        JRadioButton absorb = new JRadioButton("Nieperiodyczne");
        period.setPreferredSize(checkBoxSize);
        absorb.setPreferredSize(checkBoxSize);

        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(s100);
        sizeGroup.add(s900);
        sizeGroup.add(s300);
        sizeGroup.add(s);

        ButtonGroup condGroup = new ButtonGroup();
        condGroup.add(period);
        condGroup.add(absorb);

        s100.setSelected(true); //default
        period.setSelected(true);
        promien.setEnabled(false);
        X.setEnabled(true);
        Y.setEnabled(true);
        zarodki.setEnabled(false);
        jednorodne.setSelected(true);
        MCbutton.setEnabled(false);
        energiaButton.setEnabled(false);

        buttonPanel.add(new JLabel("Wymiary siatki:"));
        buttonPanel.add(s100);
        buttonPanel.add(s);
        buttonPanel.add(s300);
        buttonPanel.add(s900);

        buttonPanel.add(setBoardButton);
        buttonPanel.add(new JLabel("Sąsiedztwo:"));
        buttonPanel.add(wyborSasiedztwa);
        buttonPanel.add(new JLabel("Sposób zarodkowania:"));
        buttonPanel.add(jednorodne);
        buttonPanel.add(losowe);
        buttonPanel.add(zPromieniem);
        buttonPanel.add(new JLabel("Początkowa ilość komórek:"));
        buttonPanel.add(zarodki);

        buttonPanel.add(new JLabel("Wymiar X:"));
        buttonPanel.add(X);
        buttonPanel.add(new JLabel("Wymiar Y:"));
        buttonPanel.add(Y);
        buttonPanel.add(new JLabel("Długość promienia:"));
        buttonPanel.add(promien);
        buttonPanel.add(new JLabel("Warunki brzegowe:"));
        buttonPanel.add(period);
        buttonPanel.add(absorb);
        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(new JLabel("Ilość iteracji dla Monte Carlo:"));
        buttonPanel.add(iteracje);
        buttonPanel.add(MCbutton);
        buttonPanel.add(energiaButton);


        myFrame.setResizable(false);
        myFrame.add(graphicPanel, BorderLayout.CENTER);
        myFrame.add(buttonPanel, BorderLayout.WEST);
        myFrame.add(energyPanel, BorderLayout.EAST);
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    JButton getStartButton(){return startButton;}

    JButton getResetButton(){return resetButton; }

    JButton getSetBoardButton() {return setBoardButton;}

    JButton getMCbutton() {return MCbutton;}
    JButton getEnergiaButton(){return energiaButton;}
    JComboBox getWyborSasiedztwa(){return wyborSasiedztwa; }

    JSpinner getZarodki(){return zarodki;}
    JSpinner getIteracje(){return iteracje;}

    JSpinner getXnum() {return X;}
    JSpinner getYnum(){return Y;}

    JSpinner getPromien(){return promien;}

    JRadioButton getS100() {return s100;}

    JRadioButton getS900() {return s900; }

    JRadioButton getS300() {return s300; }

    JRadioButton getS() {return s;}

    JRadioButton getPeriod() {return period;}

    JRadioButton getLosowe() {
        return losowe;
    }

    JRadioButton getzPromieniem() {
        return zPromieniem;
    }

    JRadioButton getJednorodne() {
        return jednorodne;
    }

    JPanel getGraphicPanel(){return graphicPanel;}
    JPanel getEnergyPanel(){return energyPanel;}

    JFrame getMyFrame(){return myFrame;}
}
