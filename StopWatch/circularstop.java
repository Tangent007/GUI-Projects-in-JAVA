

import java.awt.*;
import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

/**
 * StopWatch
 */
/**
 * listen 
 */
class listen implements ActionListener{
    
    
    boolean first=true;
    public void write() {
        String fileName = "log.txt";

        try {
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(fileName, true);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.append(circularstop.disp.getText().toString());
            bufferedWriter.append("\n");

            // Always close files.
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        // Thread t = new Thread(()->{
        // for (circularstop.i = circularstop.a;; circularstop.i++) {
        // for (circularstop.j = circularstop.b; circularstop.j<60; circularstop.j++) {
        //     for (circularstop.k = circularstop.c; circularstop.k < 60; circularstop. k++) {
        //         for (circularstop.l = circularstop.d; circularstop.l < 100; circularstop.l++) {
        //             if (circularstop.stop) {
        //                 circularstop.a=0;
        //                 circularstop.b = 0;
        //                 circularstop.c = 0;
        //                 circularstop.d = 0;
        //                 NumberFormat nf = new DecimalFormat("00");
        //                 circularstop.disp.setText(
        //                                 nf.format(0) + ":" +nf.format(0) + ":" + nf.format(0) + ":" + nf.format(0));
        //                 circularstop.log.setText(circularstop.disp.getText().toString());
        //                 break;

        //             } else if (circularstop.pause) {
        //                 circularstop.a = circularstop.i;
        //                 circularstop.b = circularstop.j;
        //                 circularstop.c = circularstop.k;
        //                 circularstop.d = circularstop.l;
        //                 // System.out.println(circularstop.a);
        //                 // NumberFormat nf = new DecimalFormat("00");
        //                 // circularstop.disp.setText(nf.format(
        //                 //                 circularstop.i) + ":" +nf.format(
        //                 //                         circularstop.j) + ":" + nf.format(circularstop.k) + ":" + nf.format(circularstop.l));
        //                 break;

        //             } else {
        //                 // System.out.println("running");
        //                 NumberFormat nf = new DecimalFormat("00");
        //                 circularstop.disp.setText(nf.format(
        //                                 circularstop.i) + ":" +nf.format(
        //                                         circularstop.j) + ":" + nf.format(circularstop.k) + ":" + nf.format(circularstop.l));
        //             }
        //             try {
        //                 Thread.sleep(10);
        //             } catch (Exception e) {
        //             }
        //         }
        //     }
        //     }
        // }});
        if (ae.getSource()== circularstop.start){
            // if (circularstop.watch.isAlive()) {
                // System.out.println("alive");
            // }
            System.out.println("start");
            // circularstop.pause = false;
            // circularstop.stop = false;
            if(first){
                circularstop.watch.start();
                first=false;
            }
            if(circularstop.pause){
                circularstop.pause=false;
                circularstop.watch.resume();
                // if (!circularstop.watch.isAlive()) {
                //     circularstop.watch.resume();
                // }
            }
            if (circularstop.stop) {
                circularstop.stop = false;
                circularstop.watch.resume();
                // if (!circularstop.watch.isAlive()) {
                // circularstop.watch.resume();
                // }
            }
            

            // btn.setText("Stop");
        } else if (ae.getSource()==circularstop.paus) {
            try {
                circularstop.watch.suspend();
                if(!circularstop.watch.isAlive()){
                    System.out.println("thread stopped");
                }
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
            circularstop.pause = true;
            System.out.println(circularstop.disp.getText().toString());
            
        } else if (ae.getSource()==circularstop.sto) {
            circularstop.pause = false;
            System.out.println("stop");
            write();
            circularstop.stop = true;
           
            System.out.println(circularstop.i);
            circularstop.watch.suspend();
            circularstop.i = 0;
            circularstop.j = 0;
            circularstop.k = 0;
            circularstop.l = 0;
            NumberFormat nf = new DecimalFormat("00");
            circularstop.disp.setText(nf.format(0) + ":" + nf.format(0) + ":" + nf.format(0) + ":" + nf.format(0));
            circularstop.log.setText(circularstop.disp.getText().toString());
        } else if (ae.getActionCommand().equals("log")) {
            JDialog jd = new JDialog(circularstop.co, "Log");
            JLabel mes = new JLabel();
            JTextArea temp = new JTextArea();

            System.out.println("C:/Users/ASSASSIN/Desktop/Term 7/cse 406/Java/JDBC/log.txt");
            Path filePath = Paths.get("C:/Users/ASSASSIN/Desktop/Term 7/cse 406 Java/JDBC/log.txt");

            Charset charset = StandardCharsets.UTF_8;

            try (BufferedReader bufferedReader = Files.newBufferedReader(filePath, charset)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    temp.append(line + "\n");
                    // System.out.println(line);
                }
            } catch (IOException ex) {
                System.out.format("I/O error: %s%n", ex);
            }
            mes.setText(temp.getText().toString());
            System.out.println(mes.getText().toString());

            jd.setLayout(null);
            jd.add(temp);
            temp.setEditable(false);
            temp.setBounds(40, 10, 100, 100);
            temp.setBackground(new Color(238, 236, 236));
            jd.setSize(200, 200);
            jd.setBackground(Color.black);
            jd.setLocationRelativeTo(circularstop.co);
            jd.setVisible(true);
        }
        else if(ae.getActionCommand().equals("exit")){
            circularstop.co.dispose();
            
            circularstop.co.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
        }
    }
    
    
}

public class circularstop {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static int i, j, k, l;
    static JFrame co=new JFrame("StopWatch");
    static JLabel disp, log, title;
    static JButton start, sto, paus, lo,exit;
    static boolean stop = false, pause = false;
    
    static int a = 0, b = 0;
    static int c = 0;

    static int d = 0;
    static Thread watch;
    public static void main(String[] args) {


        watch = new Thread(() -> {
            for (circularstop.i = circularstop.a;; circularstop.i++) {
                for (circularstop.j = circularstop.b; circularstop.j < 60; circularstop.j++) {
                    for (circularstop.k = circularstop.c; circularstop.k < 60; circularstop.k++) {
                        for (circularstop.l = circularstop.d; circularstop.l < 100; circularstop.l++) {
                            if (circularstop.stop) {
                                circularstop.a = 0;
                                circularstop.b = 0;
                                circularstop.c = 0;
                                circularstop.d = 0;
                                NumberFormat nf = new DecimalFormat("00");
                                circularstop.disp.setText(
                                        nf.format(0) + ":" + nf.format(0) + ":" + nf.format(0) + ":" + nf.format(0));
                                circularstop.log.setText(circularstop.disp.getText().toString());
                                break;

                            } else if (circularstop.pause) {
                                circularstop.a = circularstop.i;
                                circularstop.b = circularstop.j;
                                circularstop.c = circularstop.k;
                                circularstop.d = circularstop.l;
                                // System.out.println(circularstop.a);
                                // NumberFormat nf = new DecimalFormat("00");
                                // circularstop.disp.setText(nf.format(
                                // circularstop.i) + ":" +nf.format(
                                // circularstop.j) + ":" + nf.format(circularstop.k) + ":" +
                                // nf.format(circularstop.l));
                                break;

                            } else {
                                // System.out.println("running");
                                NumberFormat nf = new DecimalFormat("00");
                                circularstop.disp.setText(nf.format(circularstop.i) + ":" + nf.format(circularstop.j)
                                        + ":" + nf.format(circularstop.k) + ":" + nf.format(circularstop.l));
                            }
                            try {
                                Thread.sleep(10);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        });

        title = new JLabel("Stop Watch");
        disp = new JLabel();
        log = new JLabel();
        start = new JButton("Start");
        paus = new JButton("pause");
        sto = new JButton("stop");
        lo = new JButton("log");
        exit= new JButton("exit");
        disp.setFont(new Font("Helvetica", Font.PLAIN, 20));
        disp.setBackground(Color.cyan);
        disp.setForeground(Color.red);
        // Container co = getContentPane();
        co.addComponentListener(new ComponentAdapter() {
            // Give the window an elliptical shape.
            // If the window is resized, the shape is recalculated here.
            @Override
            public void componentResized(ComponentEvent e) {
                co.setShape(new Ellipse2D.Double(0, 0, co.getWidth(), co.getHeight()));
            }
        });

        co.setLayout(null);
        co.add(title);
        title.setBounds(90, 30, 200, 40);
        title.setFont(new Font("French Script MT", 1, 30));
        title.setForeground(Color.red);
        co.add(disp);
        disp.setBounds(80, 100, 200, 40);
        disp.setFont(new Font("Calibri", 1, 30));
        co.add(start);
        start.setBounds(40, 180, 60, 30);
        start.setFont(new Font("Calibri", 1, 12));
        co.add(paus);
        paus.setBounds(115, 180, 70, 30);
        paus.setFont(new Font("Calibri", 1, 12));
        co.add(sto);
        sto.setBounds(200, 180, 60, 30);
        sto.setFont(new Font("Calibri", 1, 12));
        co.add(lo);
        lo.setBounds(70, 240, 70, 30);
        lo.setFont(new Font("Calibri", 1, 12));
        co.add(exit);
        exit.setBounds(150, 240, 70, 30);
        exit.setFont(new Font("Calibri", 1, 12));
        listen lis = new listen();
        start.addActionListener(lis);
        sto.addActionListener(lis);
        paus.addActionListener(lis);
        lo.addActionListener(lis);
        exit.addActionListener(lis);

        co.setUndecorated(true);
        co.setSize(300, 300);
        co.setLocationRelativeTo(null);
        co.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        co.setVisible(true);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        final boolean isTranslucencySupported = gd.isWindowTranslucencySupported(TRANSLUCENT);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                // Set the window to 80% translucency, if supported.
                if (isTranslucencySupported) {
                    co.setOpacity(0.8f);
                }

                // Display the window.
                co.setVisible(true);
            }
        });

    }
}