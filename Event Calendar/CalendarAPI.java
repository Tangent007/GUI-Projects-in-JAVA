/*
Design a Calendar where user should be able to select the Month and the Year and
The Calendar of that year and month should be displayed.
Use DateTimeAPI.
*/

import javax.swing.*;

import jdk.jfr.BooleanFlag;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * EventC
 */
class EventC implements ActionListener {
    static JDialog jd;
    static JLabel en, tag, details;
    static JTextArea ed;
    static JTextField get, de;
    static JButton ok,del;
    static JTextField tf;
    static JTextArea deta;
    static ArrayList<String> dtails = new ArrayList<String>();
    static JComboBox<String> ev,ta;

    @Override
    public void actionPerformed(ActionEvent e) {

        // TODO Auto-generated method stub
        if (e.getSource() == eventL.add) {
            int i = eventL.clickeddate;
            eventL.jd.dispose();
            System.out.println(CalendarAPI.buttons[i].getText());
            System.out.println(CalendarAPI.today);
            Integer td = Integer.valueOf(CalendarAPI.buttons[i].getText());
            CalendarAPI.cur = CalendarAPI.cur.withDayOfMonth(td);
            if ((CalendarAPI.cur.isAfter(CalendarAPI.today)) || (CalendarAPI.cur.isEqual(CalendarAPI.today))) {
                jd = new JDialog(CalendarAPI.jf, "ADD an event");
                jd.setLayout(null);
                en = new JLabel("Date :");
                tag = new JLabel("Tag (Birthday/Meeting/Work) :");
                details = new JLabel("Details :");
                get = new JTextField();
                String[] tags={"Work","Birthday","Others"};
                ta = new JComboBox<>(tags);
                de = new JTextField();
                // System.out.println("enter date in yyyy-dd-mm :");
                jd.add(en);
                en.setBounds(20, 10, 150, 30);
                jd.add(get);
                get.setBounds(40, 50, 100, 30);
                get.setText(CalendarAPI.cur.toString());
                get.setEditable(false);
                jd.add(tag);
                tag.setBounds(20, 90, 200, 30);
                jd.add(ta);
                ta.setBounds(40, 120, 100, 30);
                jd.add(details);
                details.setBounds(20, 150, 100, 30);
                jd.add(de);
                de.setBounds(40, 180, 100, 30);
                jd.add(CalendarAPI.submit);
                CalendarAPI.submit.setBounds(50, 240, 80, 30);

                jd.setLocationRelativeTo(CalendarAPI.jf);
                jd.setSize(250, 320);
                jd.setVisible(true);
            }
        } else if (e.getSource() == eventL.delete) {
            int i = eventL.clickeddate;
            ResultSet rs;
            eventL.jd.dispose();
            int j=0;
            // String[] devent={};
            ArrayList<String> devent = new ArrayList<String>();
            // List<String> list = new ArrayList<String>();
            Integer td = Integer.valueOf(CalendarAPI.buttons[i].getText());
            CalendarAPI.cur = CalendarAPI.cur.withDayOfMonth(td);
            //  System.out.println(CalendarAPI.cur);
            // List devent ;
            try {
                PreparedStatement p = CalendarAPI.con.prepareStatement("select tag,details from event where date=?");
                p.setString(1, CalendarAPI.cur.toString());
                rs = p.executeQuery();
                // while(rs.next()){
                //     // devent[j]=rs.getString(1);
                //     System.out.println(rs.getString(1));
                //     j++;
                // }
                // // devent = new List(j, false);
                // rs = p.executeQuery();
                while (rs.next()) {
                    // devent[j]=rs.getString(1);
                    dtails.add(rs.getString(2));
                    devent.add(rs.getString(1));
                    // System.out.println(rs.getString(1));
                    // j++;
                }
            } catch (Exception er) {
                //TODO: handle exception
            }
            // List devent = new List(j, false);
            for(String x:devent){
                System.out.println(x);
            }
            String[] eve=devent.stream().toArray(String[]::new);
            boolean Dcheck=false;
            try {
                PreparedStatement pop = CalendarAPI.con.prepareStatement("select * from event where date=?");
                pop.setString(1, CalendarAPI.cur.toString());
                ResultSet rst = pop.executeQuery();
                while (rst.next()) {
                    Dcheck=true;
                }
            } catch (Exception err) {
                //TODO: handle exception
            }
            // eve= (String[]) devent.toArray();
            jd = new JDialog(CalendarAPI.jf,"Delete");
            jd.setLayout(null);
            JTextArea noE = new JTextArea("No event found");
            del=new JButton("DEL");
            deta= new JTextArea();
            deta.setLineWrap(true);
            ev = new JComboBox<>(eve);
            if(Dcheck)
            jd.add(ev);
            ev.setBounds(20,20,100,40);
            if (Dcheck)
            jd.add(del);
            del.setBounds(150,20,80,30);
            if (Dcheck)
            jd.add(deta);
            deta.setBounds(20,80,200,200);
            deta.setBackground(new Color(238, 236, 236));

            if(!Dcheck)
                jd.add(noE);
            noE.setBounds(20,50,100,100);
            noE.setBackground(new Color(238, 236, 236));
            noE.setEditable(false);

            jd.setLocationRelativeTo(CalendarAPI.jf);
            jd.setSize(250, 260);
            jd.setVisible(true);

            EventC ford=new EventC();
            ev.addActionListener(ford);
            del.addActionListener(ford);

            // try {
            //     PreparedStatement ps = CalendarAPI.con.prepareStatement("delete from event where date=?");
            //     ps.setString(1, CalendarAPI.cur.toString());
            //     ps.executeUpdate();
            //     // eventREminder.con.close();
            // } catch (Exception eww) {
            //     // TODO: handle exception
            //     eww.printStackTrace();
            // }
            eventL.jd.dispose();

        } else if(e.getSource()==CalendarAPI.snooze){

            try {
                Statement st = CalendarAPI.con.createStatement();
                ResultSet rs = st.executeQuery("select * from event");
                tf = new JTextField();
                ok = new JButton("OK");
                JLabel ti = new JLabel("Enter time: (hours:min)");
                System.out.println("snooze pressed");
                LocalTime t = LocalTime.now();
                System.out.println(t.toString());
                JDialog op = new JDialog(CalendarAPI.jf, "snooze");
                op.setLayout(null);

                op.add(tf);
                tf.setBounds(20,20,150,30);

                op.add(ok);
                ok.setBounds(20,60,60,30);

                EventC ce = new EventC();
                ok.addActionListener(ce);
                
                op.setLocationRelativeTo(CalendarAPI.jf);
                op.setSize(280, 160);
                op.setVisible(true);
            } catch (Exception eg) {
                //TODO: handle exception
            }
        } else if(e.getSource()==ok){
            try {
                System.out.println("ok pressed");
                LocalTime p = LocalTime.parse(tf.getText());
                PreparedStatement te = CalendarAPI.con
                        .prepareStatement("update event set time=?,snooze=? where tag=? and details=?");
                te.setString(1, p.toString());
                te.setString(2, "1");
                te.setString(3, CalendarAPI.Stag);
                te.setString(4, CalendarAPI.Sdetails);
                te.executeUpdate();
            } catch (Exception etr) {
                //TODO: handle exception
                etr.printStackTrace();
            }
        } else if(e.getSource()==ev){
            int i = ev.getSelectedIndex();
            deta.setText(dtails.get(i));
            deta.setEditable(false);
        } else if(e.getSource()==del){
            System.out.println("del pressed");
            try {
                int i = ev.getSelectedIndex();
            PreparedStatement ps = CalendarAPI.con.prepareStatement("delete from event where tag=? and details=?");
            ps.setString(1, ev.getSelectedItem().toString());
            ps.setString(2, dtails.get(ev.getSelectedIndex()));
            ps.executeUpdate();
            // eventREminder.con.close();
            } catch (Exception eww) {
            // TODO: handle exception
            eww.printStackTrace();
            }
            EventC.jd.dispose();
        }
    }

}

/**
 * menu
 */
class eventL implements ActionListener {
    static JDialog jd;
    static JLabel en, tag, details;
    static JTextArea ed;
    static JTextField get, ta, de;
    static int clickeddate;
    static JButton add, delete;

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == CalendarAPI.show) {
            System.out.println("show pressed");
            jd = new JDialog(CalendarAPI.jf, "Events");
            jd.setLayout(null);

            LocalDate current = LocalDate.now();
            LocalDate check;
            en = new JLabel("Events :");
            ed = new JTextArea();
            jd.add(en);
            en.setBounds(20, 20, 60, 30);
            jd.add(ed);
            ed.setBounds(20, 60, 250, 100);
            boolean fl = false;
            // System.out.println(current.plusDays(2));
            try {

                Statement st = CalendarAPI.con.createStatement();
                ResultSet rs = st.executeQuery("select * from event");

                // System.out.println(rs.getString("date"));
                while (rs.next()) {
                    // System.out.println(rs.getString(""));
                    check = LocalDate.parse(rs.getString("date"));
                    // System.out.println(check + " " + current);
                    if ((check.isAfter(current)) || (check.isEqual(current))) {
                        ed.append(rs.getString("date") + " " + rs.getString("tag") + " " + rs.getString("details")
                                + "\n");
                        fl = true;
                    }
                }
                if (!fl) {
                    ed.append("no event in the next three days");
                }

                // eventREminder.con.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            ed.setEditable(false);
            ed.setLineWrap(true);
            ed.setBackground(new Color(238, 236, 236));
            jd.setSize(300, 300);
            jd.setLocationRelativeTo(null);
            jd.setVisible(true);

        } else if (e.getSource() == CalendarAPI.submit) {
            System.out.println("submit pressed");
            LocalDate date = LocalDate.parse(EventC.get.getText());
            // System.out.println(date.toString() + EventC.ta.getText() + EventC.de.getText());
            try {
                PreparedStatement ps = CalendarAPI.con.prepareStatement("insert into event values(?,?,?,?,?)");
                ps.setString(1, date.toString());
                ps.setString(2, EventC.ta.getSelectedItem().toString());
                ps.setString(3, EventC.de.getText());
                ps.setString(4, "null");
                ps.setString(5, "0");
                ps.executeUpdate();
                // eventREminder.con.close();
            } catch (Exception eww) {
                // TODO: handle exception
                eww.printStackTrace();
            }
            EventC.jd.dispose();
        } else
            for (int i = 0; i < 42; i++) {
                if (e.getSource() == CalendarAPI.buttons[i]) {
                    clickeddate = i;
                    Integer td = Integer.valueOf(CalendarAPI.buttons[i].getText());
                    CalendarAPI.cur = CalendarAPI.cur.withDayOfMonth(td);
                    if ((CalendarAPI.cur.isAfter(CalendarAPI.today)) || (CalendarAPI.cur.isEqual(CalendarAPI.today))) {
                        jd = new JDialog(CalendarAPI.jf, "Events :");
                        jd.setLayout(null);
                        add = new JButton("ADD");
                        delete = new JButton("DELETE");

                        jd.add(add);
                        add.setBounds(20, 40, 90, 40);
                        jd.add(delete);
                        delete.setBounds(140, 40, 110, 40);

                        EventC ec = new EventC();
                        add.addActionListener(ec);
                        delete.addActionListener(ec);

                        jd.setLocationRelativeTo(CalendarAPI.jf);
                        jd.setSize(280, 160);
                        jd.setVisible(true);
                    }
                }
            }

    }

}

public class CalendarAPI implements ActionListener {
    static JFrame jf = new JFrame("My_Calendar");
    static JComboBox<String> month = new JComboBox<>();
    static JComboBox<Integer> year = new JComboBox<>();
    static JButton view = new JButton("VIEW");
    static LocalDate cur, today;
    static Connection con;
    static JButton show = new JButton("show");
    static JButton submit = new JButton("Submit");
    static JButton snooze;
    static eventL el = new eventL();

    static LocalDate forSnooze;
    static boolean checkk,c;
    static boolean ran =true;
    static LocalDate check;
    static String Stag,Sdetails;
    // static JLabel nextevent = new JLabel();

    static JButton[] buttons = new JButton[42];

    public static void main(String[] rk) throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cse406", "root", "Tmysqlroot@99");
        jf.setLayout(null);

        show();
        for (Month m : java.time.Month.values())
            month.addItem(m.toString());
        for (int i = 2000; i < 2100; i++)
            year.addItem(i);

        jf.add(month);
        month.setBounds(350, 20, 100, 40);
        jf.add(year);
        year.setBounds(470, 20, 100, 40);
        jf.add(view);
        view.setBounds(590, 20, 100, 40);
        view.addActionListener(new CalendarAPI());

        // showing event part
        submit.addActionListener(el);

        jf.add(show);
        show.setBounds(500, 500, 90, 30);
        show.addActionListener(el);

        // Panel for Buttons

        JPanel p = new JPanel(new GridLayout(7, 7));
        jf.add(p);
        p.setBounds(20, 80, 1000, 400);

        // Buttons for Days

        for (DayOfWeek d : DayOfWeek.values()) {
            JButton b;
            p.add(b = new JButton(d.toString()));
            b.setBackground(Color.ORANGE);
            b.setFont(new Font("Serif", 1, 16));
        }
        // Buttons for Dates

        for (int i = 0; i < 42; i++) {
            buttons[i] = new JButton();
            buttons[i].setBackground(Color.LIGHT_GRAY);
            buttons[i].setFont(new Font("Serif", 1, 24));
            p.add(buttons[i]);
        }

        jf.setSize(1060, 600);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setBackground(Color.CYAN);
        jf.setVisible(true);
    }

    public static void show() {
        LocalDate current = LocalDate.now();
        // LocalDate check;
        // JLabel en = new JLabel("Events :");
        JTextArea nextevent = new JTextArea();
        // jf.add(en);
        // en.setBounds(20, 20, 60, 30);
        jf.add(nextevent);
        nextevent.setBounds(20, 500, 250, 100);
        nextevent.setEditable(false);
        nextevent.setLineWrap(true);
        nextevent.setBackground(Color.cyan);
        boolean fl = false;
        try {

            Statement st = CalendarAPI.con.createStatement();
            ResultSet rs = st.executeQuery("select * from event");

            // System.out.println(rs.getString("date"));
            while (rs.next()) {
                // System.out.println(rs.getString(""));
                check = LocalDate.parse(rs.getString("date"));
                // System.out.println(check + " " + current);
                if ((((check.isAfter(current))) || (check.isEqual(current))) && (check.isBefore(current.plusDays(4)))) {
                    nextevent.append(
                            rs.getString("date") + " " + rs.getString("tag") + " " + rs.getString("details") + "\n");
                    fl = true;
                }
            }
            if (!fl) {
                nextevent.setText("no event in the next three days");
            }

            // eventREminder.con.close();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // try {
        //     Statement st = CalendarAPI.con.createStatement();
        //     ResultSet rs = st.executeQuery("select * from event");
        //     boolean c=false;
        //     while (rs.next()) {
        //         // System.out.println(rs.getString(""));
        //         check = LocalDate.parse(rs.getString("date"));
        //         c = rs.getBoolean("snooze");
        //         System.out.println(c);
        //         String s = rs.getString("time");
        //         boolean checkk = true;
        //         if(!s.equals("null")){
        //             LocalTime lo = LocalTime.parse(rs.getString("time"));
                
        //             // System.out.println(check + " " + current);
        //             LocalTime lo2 = LocalTime.now();
                    
        //             if(lo.isAfter(lo2)){
        //                 checkk=false;
        //             }
        //             else {
        //                 checkk=true;
        //                 c=false;
        //             }

        //         }
        //         else{
        //             checkk=true;
        //         }
        //         if ((check.isEqual(current))&&(!c)&&(checkk)) {
        //             forSnooze=check;
        //             snooze = new JButton("Snooze");
        //             JTextArea eve = new JTextArea();
        //             JDialog di= new JDialog(CalendarAPI.jf,"Today");
        //             di.setLayout(null);
        //             eve.append(
        //                     rs.getString("date") + " " + rs.getString("tag") + " " + rs.getString("details") + "\n");
        //             di.add(eve);
        //             eve.setBounds(40,40,200,80);
        //             eve.setEditable(false);
        //             eve.setLineWrap(true);
        //             eve.setBackground(new Color(238, 236, 236));
        //             di.add(snooze);
        //             snooze.setBounds(160,10,80,30);

        //             EventC ec = new EventC();
        //             snooze.addActionListener(ec);


        //             di.setLocationRelativeTo(jf);
        //             di.setSize(280, 160);
        //             di.setVisible(true);
        //         }
        //     }
        // } catch (Exception e) {
        //     //TODO: handle exception
        // }
        Thread sn = new Thread(()->{
            System.out.println("thread running");
            while(true)
            try {
            Statement st = CalendarAPI.con.createStatement();
            ResultSet rs = st.executeQuery("select * from event");
            c=false;
            while (rs.next()) {
                // System.out.println(rs.getString(""));
                check = LocalDate.parse(rs.getString("date"));
                c = rs.getBoolean("snooze");
                // System.out.println(c);
                String s = rs.getString("time");
                checkk = true;
                if(!s.equals("null")){
                    // ran =true;
                    LocalTime lo = LocalTime.parse(rs.getString("time"));
                
                    // System.out.println(check + " " + current);
                    LocalTime lo2 = LocalTime.now();
                        int a = lo2.getHour();
                        int b = lo2.getMinute();
                        LocalTime po = LocalTime.of(a, b);
                    
                    // if(lo.isAfter(po)){
                    //     checkk=false;
                    // }
                    if(lo.equals(po)){
                                if ((check.isEqual(current))&&(c)) {
                                    PreparedStatement te = CalendarAPI.con
                                            .prepareStatement("update event set snooze=? where time=?");
                                    te.setString(1, "0");
                                    te.setString(2, lo.toString());
                                    te.executeUpdate();
                                    forSnooze = check;
                                    snooze = new JButton("Snooze");
                                    JTextArea eve = new JTextArea();
                                    JDialog di = new JDialog(CalendarAPI.jf, "Today");
                                    di.setLayout(null);
                                    eve.append(rs.getString("date") + " " + rs.getString("tag") + " "
                                            + rs.getString("details") + "\n");
                                    Stag = rs.getString("tag");
                                    Sdetails = rs.getString("details");
                                    di.add(eve);
                                    eve.setBounds(40, 40, 200, 80);
                                    eve.setEditable(false);
                                    eve.setLineWrap(true);
                                    eve.setBackground(new Color(238, 236, 236));
                                    di.add(snooze);
                                    snooze.setBounds(160, 10, 80, 30);

                                    EventC ec = new EventC();
                                    snooze.addActionListener(ec);

                                    di.setLocationRelativeTo(jf);
                                    di.setSize(280, 160);
                                    di.setVisible(true);

                                }
                    }

                }
                else if(ran){
                    checkk=true;
                    ran=false;
                            if (check.isEqual(current)) {
                                forSnooze = check;
                                snooze = new JButton("Snooze");
                                JTextArea eve = new JTextArea();
                                JDialog di = new JDialog(CalendarAPI.jf, "Today");
                                di.setLayout(null);
                                eve.append(rs.getString("date") + " " + rs.getString("tag") + " "
                                        + rs.getString("details") + "\n");
                                di.add(eve);
                                eve.setBounds(40, 40, 200, 80);
                                eve.setEditable(false);
                                eve.setLineWrap(true);
                                eve.setBackground(new Color(238, 236, 236));
                                Stag=rs.getString("tag");
                                Sdetails=rs.getString("details");
                                di.add(snooze);
                                snooze.setBounds(160, 10, 80, 30);

                                EventC ec = new EventC();
                                snooze.addActionListener(ec);

                                di.setLocationRelativeTo(jf);
                                di.setSize(280, 160);
                                di.setVisible(true);

                            }
                }
                // if ((check.isEqual(current))&&(!c)&&(checkk)) {
                //     forSnooze=check;
                //     snooze = new JButton("Snooze");
                //     JTextArea eve = new JTextArea();
                //     JDialog di= new JDialog(CalendarAPI.jf,"Today");
                //     di.setLayout(null);
                //     eve.append(
                //             rs.getString("date") + " " + rs.getString("tag") + " " + rs.getString("details") + "\n");
                //     di.add(eve);
                //     eve.setBounds(40,40,200,80);
                //     eve.setEditable(false);
                //     eve.setLineWrap(true);
                //     eve.setBackground(new Color(238, 236, 236));
                //     di.add(snooze);
                //     snooze.setBounds(160,10,80,30);

                //     EventC ec = new EventC();
                //     snooze.addActionListener(ec);


                //     di.setLocationRelativeTo(jf);
                //     di.setSize(280, 160);
                //     di.setVisible(true);
                    
                // }
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
        });

        sn.start();

    }

    public void actionPerformed(ActionEvent e) {
        String m = (String) month.getSelectedItem();
        int y = (int) year.getSelectedItem();
        for (int i = 0; i < 42; i++) {
            buttons[i].setText(null);
        }

        /*
         * Write the code to find the number of Days in the selected month and then
         * display the Calendar. Given JButtons Buttons [];
         */
        // System.out.println("You have selected "+ month.getSelectedItem() + " "+
        // year.getSelectedItem());

        // int monn = Arrays.asList(mon).indexOf(m);
        // monn+=1;
        int monn = month.getSelectedIndex() + 1;
        LocalDate d = LocalDate.of(y, monn, 1);
        // d = d.withYear(y);
        // d = d.withMonth(monn);
        // d = d.withDayOfMonth(1);
        cur = d;

        DayOfWeek dow = d.getDayOfWeek();
        int week = dow.getValue();
        // System.out.println(monn + " " + week + " " + dow);
        LocalDate current = LocalDate.now();
        today = current;

        int last = d.lengthOfMonth();
        // System.out.println(last);

        for (int i = (week - 1), j = 1; j <= last; j++, i++) {
            // int temp =i;
            if ((j == current.getDayOfMonth()) && (d.getYear() == current.getYear())
                    && (d.getMonth() == current.getMonth()))
                buttons[i].setBackground(Color.ORANGE);
            else
                buttons[i].setBackground(Color.LIGHT_GRAY);
            String a = String.valueOf(j);
            if (((i + 1) % 7 == 0))
                buttons[i].setBackground(Color.CYAN);
            else if ((i + 1) % 7 == 1)
                buttons[i + 6].setBackground(Color.CYAN);
            buttons[i].setText(a);
            buttons[i].addActionListener(el);
        }
        // System.out.println(tday.minusDays(1));
        // System.out.println(d);
    }
}