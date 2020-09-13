import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.event.*;


// listener for font part
class fontlistener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==menu.ap){
            String type = menu.cb.getItemAt(menu.cb.getSelectedIndex());
            Integer size = menu.cb2.getItemAt(menu.cb2.getSelectedIndex());
            notepad.ta.setFont(new Font(type,3,size));
            menu.jd.add(menu.prompt);
            menu.prompt.setBounds(80, 240, 150, 20);
            menu.prompt.setFont(new Font("Calibri", 3, 15));
            System.out.println("apply pressed");
            menu.jd.dispose();
        }
        else if(e.getSource()==menu.cb){
            String type = menu.cb.getItemAt(menu.cb.getSelectedIndex());
            Integer size = menu.cb2.getItemAt(menu.cb2.getSelectedIndex());
            menu.pr.setFont(new Font(type,3,size));
        }
        else if(e.getSource()==menu.cb2){
            String type = menu.cb.getItemAt(menu.cb.getSelectedIndex());
            Integer size = menu.cb2.getItemAt(menu.cb2.getSelectedIndex());
            menu.pr.setFont(new Font(type,3,size));
        }

    }
    
}


// listener for menu part
class menu implements ActionListener{
    static JDialog jd;
    static JLabel cs,cf,prompt,preview,pr;
    // static JTextField pr;
    static JComboBox<String> cb;
    static JComboBox<Integer> cb2;
    static JButton ap;
    static String copy;

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==(notepad.m31)){
            //  font menu dialog part
            jd = new JDialog(notepad.jf,"font menu");
            jd.setLayout(null);

            // test part


            // test part end


            // create a label
            cs = new JLabel("Choose size");
            cf = new JLabel("Choose Font");
            prompt = new JLabel("font applied");
            preview = new JLabel("Preview :");
            // jd.add(l);
            pr = new JLabel("AaBbCc");

            jd.add(preview);
            preview.setBounds(30, 30, 90, 20);
            preview.setFont(new Font("Serif", 3, 15));

            jd.add(pr);
            pr.setBounds(100, 20, 200, 40);
            pr.setFont(new Font("Serif", 3, 15));

            jd.add(cf);
            cf.setBounds(10, 100, 90, 20);
            cf.setFont(new Font("Calibri",3,15));

            
            // String[] arr = { "Serif", "Monaco", "Calibri", "Helvetica Neue", "Monospaced Plain" };
            String fonts[] = 
      GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
            cb = new JComboBox<>(fonts);
            jd.add(cb);
            cb.setBounds(100, 100, 160, 20);

            jd.add(cs);
            cs.setBounds(10, 140, 90, 20);
            cs.setFont(new Font("Serif", 3, 15));

            Integer[] arr2 = {8,10,12,14,16,20,30,40 };
            cb2 = new JComboBox<>(arr2);
            jd.add(cb2);
            cb2.setBounds(100, 140, 80, 20);


            ap =new JButton("Apply");
            jd.add(ap);
            ap.setBounds(80, 200, 90, 20);


            fontlistener fl = new fontlistener();
            ap.addActionListener(fl);
            cb.addActionListener(fl);
            cb2.addActionListener(fl);

            // String[] col = { "font name" };
            // String[][] font = { { "Serif"}, { "Calibri" } };

            // JTable jt = new JTable(font, col);
            // JScrollPane sp = new JScrollPane(cb, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            //         JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            // JScrollBar jb = new JScrollBar();
            // sp.add(jb);
            // jd.add(sp,BorderLayout.CENTER);


            // setsize of dialog
            jd.setSize(300, 350);
            jd.setLocationRelativeTo(null);

            jd.setVisible(true);

            // dialog end
            System.out.println("font pressed");
        }
        else if(e.getSource()==notepad.m21){
            copy = notepad.ta.getSelectedText();
            System.out.println("copied");
        }
        else if(e.getSource()==notepad.m22){
            // String s = notepad.ta.getText();
            if (copy!=null){
                notepad.ta.append(copy);
                System.out.println("pasted");
                copy=null;
            }
            else{
                System.out.println("nothing to paste");
            }
            
        }
        else if(e.getSource()==notepad.m23){
            copy = notepad.ta.getSelectedText();
            notepad.ta.replaceSelection("");
        }
        else if(e.getSource()==notepad.m14){
            notepad.jf.dispose();
        }
        else if(e.getSource()==notepad.m32){
            if(notepad.m32.isSelected()){
                notepad.ta.setLineWrap(true);
            }
            else if(!notepad.m32.isSelected()){
                notepad.ta.setLineWrap(false);
                // notepad.m32.
            }
        }

    }
    
}

public class notepad {
    static JFrame jf = new JFrame("notepad");
    static JTextArea ta = new JTextArea();
    static JMenu m1,m2,m3,m4;
    static JMenuItem m11,m12,m13,m14,m21,m22,m23,m31,m32;
    static JSeparator s = new JSeparator();
    public static void main(String[] args) {

        // jf.add(ta,BorderLayout.CENTER);
        // ta.setLineWrap(true);
        JScrollPane sp = new JScrollPane(ta);
        jf.add(sp,BorderLayout.CENTER);

        m1=new JMenu("File");
        m2 = new JMenu("Edit");
        m3 = new JMenu("View");
        m4 = new JMenu("About");

        // m1 submenu
        m11 = new JMenuItem("new");
        m12 = new JMenuItem("open");
        m13 = new JMenuItem("save");
        m14 = new JMenuItem("exit");
        
        // m2 submenu
        m23 = new JMenuItem("cut");
        m21=new JMenuItem("copy");
        m22 = new JMenuItem("paste");


        // m3 submenu
        m31 = new JMenuItem("font");
        m32 =new JCheckBoxMenuItem("Word wrap");

        JMenuBar mb = new JMenuBar();
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.add(m4);
        m1.add(m11);
        m1.add(m12);
        m1.add(m13);
        m1.add(s);
        m1.add(m14);
        m2.add(m21);
        m2.add(m22);
        m2.add(m23);
        m3.add(m31);
        m3.add(m32);

        // for when just jmenu is clicked since it doesnot extends abstract button
        m4.addMenuListener(new MenuListener() {

            public void menuSelected(MenuEvent e) {
                JDialog jd = new JDialog(notepad.jf, "About");
                JLabel t = new JLabel();

                t.setText("Noob Notepad\n v1.0");
                jd.add(t, BorderLayout.CENTER);

                jd.setSize(100, 100);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
                // try {
                //     jd.wait(5000);
                // } catch (Exception ex) {
                //     //TODO: handle exception
                // }
                // jd.dispose();
                // System.out.println("menuSelected");
            }

            public void menuDeselected(MenuEvent e) {
                // System.out.println("menuDeselected");

            }

            public void menuCanceled(MenuEvent e) {
                // System.out.println("menuCanceled");

            }
        });

        

        jf.add(mb,BorderLayout.NORTH);


        menu ma = new menu();
        m11.setEnabled(false);
        m12.setEnabled(false);
        m13.setEnabled(false);
        m14.addActionListener(ma);
        m21.addActionListener(ma);
        m22.addActionListener(ma);
        m23.addActionListener(ma);
        m31.addActionListener(ma);
        m32.addActionListener(ma);
        // m4.addActionListener(new MyAction());




        jf.setSize(800, 500);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.getContentPane().setBackground(new Color(255, 175, 115));
        jf.setVisible(true);
    }
}
