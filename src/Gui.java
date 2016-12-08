import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame {
    private JButton buttonStart = new JButton("Start");
    private JButton buttonStop = new JButton("Zatrzymaj");
    private JButton buttonFreeze = new JButton("Zamróź");
    private JSlider sliderProducers = new JSlider(1,1,10,5);
    private JSlider sliderBuyers = new JSlider(1,1,10,5);
    private JLabel labelHMProducers = new JLabel(Integer.toString(sliderProducers.getValue()));
    private JLabel labelHMBuyers = new JLabel(Integer.toString(sliderBuyers.getValue()));
    private JMenuBar menuBar = new JMenuBar();
    private static int howManyProducers = 5;
    private static int howManyBuyers = 5;
    private JTextArea textArea = new JTextArea();

    private JScrollPane scrollPane = new JScrollPane(textArea);
    private GraphicRepresentation graphicRepresentation= new GraphicRepresentation(this);
    private static Dimension frameSize = new Dimension(800, 700);

    private Gui() {
        super("Tobiasz Rumian Laboratorium 5.2");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(frameSize);
        buttonStop.setEnabled(false);
        buttonFreeze.setEnabled(false);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.addComponentListener(new CustomComponentListener());
        this.addWindowStateListener(new CustomWindowStateListener());

        menuBar.add(buttonStart);
        createSliders();
        menuBar.add(buttonFreeze);
        menuBar.add(buttonStop);
        JPanel panel = new JPanel();
        textArea.setEditable(false);
        setContentPane(panel);
        scrollPane.setPreferredSize(new Dimension((int) frameSize.getWidth() - 20, (int) (frameSize.getHeight() * 0.25)));
        panel.add(scrollPane);
        panel.add(graphicRepresentation);
        graphicRepresentation.setVisible(true);
        graphicRepresentation.setPreferredSize(new Dimension((int) frameSize.getWidth(), (int) (frameSize.getWidth() * 0.75)));
        graphicRepresentation.setBackground(Color.blue);
        setJMenuBar(menuBar);
        setVisible(true);

        buttonStart.addActionListener(actionEvent -> {
            buttonStart.setEnabled(false);
            sliderBuyers.setEnabled(false);
            sliderProducers.setEnabled(false);
            buttonStop.setEnabled(true);
            buttonFreeze.setEnabled(true);
            howManyBuyers = sliderBuyers.getValue();
            howManyProducers = sliderProducers.getValue();
            graphicRepresentation.createFigures();
        });
        sliderProducers.addChangeListener(changeEvent -> labelHMProducers.setText(Integer.toString(sliderProducers.getValue())));
        sliderBuyers.addChangeListener(changeEvent -> labelHMBuyers.setText(Integer.toString(sliderBuyers.getValue())));
        buttonStop.addActionListener(actionEvent -> {
            buttonStart.setEnabled(true);
            sliderBuyers.setEnabled(true);
            sliderProducers.setEnabled(true);
            buttonStop.setEnabled(false);
            buttonFreeze.setEnabled(false);
            textArea.setText("");
            graphicRepresentation.kill();
            graphicRepresentation = new GraphicRepresentation(this);
            graphicRepresentation.setVisible(true);
            graphicRepresentation.setPreferredSize(new Dimension((int) frameSize.getWidth(), (int) (frameSize.getWidth() * 0.75)));
            graphicRepresentation.setBackground(Color.blue);
        });
        buttonFreeze.addActionListener(actionEvent -> {
            graphicRepresentation.freeze();
        });
    }
    private void createSliders() {
        sliderProducers.setMajorTickSpacing(5);
        sliderProducers.setMinorTickSpacing(1);
        sliderProducers.setPaintTicks(true);
        sliderProducers.setPaintLabels(true);
        JMenu menuProducers = new JMenu("Ilu producentów?");
        menuProducers.add(sliderProducers);
        menuProducers.add(labelHMProducers);
        menuBar.add(menuProducers);
        sliderBuyers.setMajorTickSpacing(5);
        sliderBuyers.setMinorTickSpacing(1);
        sliderBuyers.setPaintTicks(true);
        sliderBuyers.setPaintLabels(true);
        JMenu menuBuyers = new JMenu("Ilu kupców?");
        menuBuyers.add(sliderBuyers);
        menuBuyers.add(labelHMBuyers);
        menuBar.add(menuBuyers);
    }
    public static void main(String[] args) {
        new Gui();
    }

    static int getHowManyProducers() {
        return howManyProducers;
    }

    static int getHowManyBuyers() {
        return howManyBuyers;
    }

    void addToTextArea(String text) {
        textArea.append(text + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    static Dimension getFrameSize() {
        return frameSize;
    }

    private void setComponentsSize(ComponentEvent e) {
        frameSize = e.getComponent().getBounds().getSize();
        graphicRepresentation.setPreferredSize(new Dimension((int) frameSize.getWidth(), (int) ((frameSize.getWidth()-menuBar.getHeight()) * 0.75)));
        //graphicRepresentation.setSize(new Dimension((int) frameSize.getWidth(), (int) ((frameSize.getWidth()-menuBar.getHeight()) * 0.75)));
        graphicRepresentation.setMinimumSize(null);
        graphicRepresentation.setMaximumSize(null);
        scrollPane.setPreferredSize(new Dimension((int) frameSize.getWidth() - 20, (int) ((frameSize.getWidth()-menuBar.getHeight()) * 0.25)));
        graphicRepresentation.refreshCoordinates();
    //TODO: Sprawdzić dlaczego trzyma się wyznaczonej proporcji.
    }
    public Dimension getJmenuSize(){
        return menuBar.getSize();
    }

    class CustomComponentListener implements ComponentListener {
        public void componentResized(ComponentEvent e) {
            setComponentsSize(e);
        }
        public void componentMoved(ComponentEvent e) {}
        public void componentShown(ComponentEvent e) {}
        public void componentHidden(ComponentEvent e) {}
    }

    class CustomWindowStateListener implements WindowStateListener {
        @Override
        public void windowStateChanged(WindowEvent e) {
            setComponentsSize(e);
        }
    }
}
