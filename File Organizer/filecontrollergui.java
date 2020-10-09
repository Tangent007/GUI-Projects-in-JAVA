import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.BreakIterator;
import java.util.*;
import java.util.HashSet;
import java.util.stream.Stream;

import javax.swing.*;

// alag dialog ka sunne k liye
class mainlistener implements ActionListener {
    static void organize(Path p) throws IOException {
        Stream<Path> st = Files.list(p);
        st.forEach(file -> {
            if (file.toFile().isFile()) {

                String f = file.getFileName().toString();
                String[] ar = f.split("\\.");
                System.out.println(p.resolve(ar[1]));
                if (Files.exists(p.resolve(ar[1])) == true) {
                    Path temp = p.resolve(ar[1] + "\\" + file.getFileName());
                    try {
                        Files.move(file, temp);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        // e.printStackTrace();
                    }
                } else {
                    System.out.println(file);
                    Path temp = p.resolve(ar[1] + "\\");
                    try {
                        Files.createDirectories(temp);
                        temp = temp.resolve(file.getFileName());
                        Files.move(file, temp);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        // e.printStackTrace();
                    }

                }
            }
        });
    }

    static void copy(String path, Path p, Path dest) throws Exception {
        // Path desti = dest;
        Files.walkFileTree(Paths.get(path), new HashSet<>(), 2, new FileVisitor<Path>() {
            Path desti = dest;

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("preVisitDirectory: " + dir + "\n" + p);

                if (!dir.equals(p)) {
                    desti = desti.resolve(dir.getFileName());
                    Files.copy(dir, desti);
                    System.out.println("write: " + desti);

                    System.out.println("write: " + desti);
                } else {
                    Files.copy(dir, desti);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("visitFile: " + file);
                desti = desti.resolve(file.getFileName());
                Files.copy(file, desti);
                System.out.println("write: " + desti);
                // int index = desti.getNameCount();
                desti = desti.getParent();
                System.out.println("write: " + desti);

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("visitFileFailed: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("postVisitDirectory: " + dir);
                // Files.copy(in, target, options)
                // int index = desti.getNameCount();
                desti = desti.getParent();
                return FileVisitResult.CONTINUE;
            }
        });
    }

    static void deleterec(File[] files) {
        Stream<File> str = Arrays.stream(files);
        str.forEach(file -> {
            if (file.isDirectory()) {
                deleterec(file.listFiles());
                file.delete();
            } else {
                file.delete();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == filelistener.but1) { // organize
            Path p = Paths.get(filelistener.sour.getText().toString());
            System.out.println(filelistener.sour.getText().toString());
            try {
                organize(p);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                // e1.printStackTrace();
            }
            filelistener.sour.setText(null);
            filelistener.message.setText("Success!");
            filelistener.jd.add(filelistener.message);
            filelistener.message.setBounds(100, 240, 80, 20);
        } else if (e.getSource() == filelistener.but2) { // move
            Path source = Paths.get(filelistener.sour.getText().toString());
            Path dest = Paths.get(filelistener.des.getText().toString());
            File[] str = new File(filelistener.sour.getText().toString()).listFiles();
            if (source.toFile().isDirectory()) {
                try {
                    copy(filelistener.sour.getText().toString(), source, dest);
                    deleterec(str);
                    source.toFile().delete();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    // e1.printStackTrace();
                }
            } else {
                try {
                    // dest=dest.resolve(source.getFileName());
                    Files.move(source, dest);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    // e1.printStackTrace();
                }
            }
            filelistener.sour.setText(null);
            filelistener.des.setText(null);
            filelistener.message.setText("Success!");
            filelistener.jd.add(filelistener.message);
            filelistener.message.setBounds(100, 240, 80, 20);
        } else if (e.getSource() == filelistener.but3) { // copy
            Path source = Paths.get(filelistener.sour.getText().toString());
            Path dest = Paths.get(filelistener.des.getText().toString());
            if (source.toFile().isDirectory()) {
                try {
                    copy(filelistener.sour.getText().toString(), source, dest);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    // e1.printStackTrace();
                }
            } else {
                try {
                    Files.copy(source, dest);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    // e1.printStackTrace();
                }
            }
            filelistener.sour.setText(null);
            filelistener.des.setText(null);
            filelistener.message.setText("Success!");
            filelistener.jd.add(filelistener.message);
            filelistener.message.setBounds(100, 240, 80, 20);
        } else if (e.getSource() == filelistener.but4) { // delete
            Path p = Paths.get(filelistener.sour.getText().toString());
            File[] str = new File(filelistener.sour.getText().toString()).listFiles();
            if (p.toFile().exists()) {
                if (p.toFile().isDirectory()) {
                    deleterec(str);
                    System.out.println(p.getFileName());
                    p.toFile().delete();
                } else {
                    p.toFile().delete();
                }
                filelistener.sour.setText(null);
                // filelistener.des.setText(null);
                filelistener.message.setText("Success!");
                filelistener.jd.add(filelistener.message);
                filelistener.message.setBounds(100, 240, 120, 20);
            } else {
                filelistener.sour.setText(null);
                // filelistener.des.setText(null);
                filelistener.message.setText("File does not exist");
                filelistener.jd.add(filelistener.message);
                filelistener.message.setBounds(100, 240, 120, 20);

            }

        } else if (e.getSource() == filelistener.but5) { // search
            String name = filelistener.sour.getText().toString();
            Path p = Paths.get(filelistener.des.getText().toString());
            
            Stream<Path> st;

            if (p.toFile().exists()) {
                System.out.println("exists");
                filelistener.message.setText("null");
                // boolean exist = false;
                try {
                    // boolean exist = false;
                    st = Files.walk(p);
                    st.forEach((file) -> {
                        if (file.toFile().isFile()) {
                            // System.out.println(file.getFileName());
                            if (file.getFileName().toString().indexOf(name) != -1) {
                                // System.out.println(file);
                                filelistener.sour.setText(null);
                                filelistener.des.setText(null);
                                filelistener.message.setText(file.toString());
                                filelistener.jd.add(filelistener.message);
                                filelistener.message.setBounds(50, 240, 200, 20);
                            }
                        }
                    });
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    // e1.printStackTrace();
                }
                if (filelistener.message.getText().toString() == "null") {
                    filelistener.jd.remove(filelistener.message);
                    filelistener.jd.revalidate();
                    filelistener.jd.repaint();
                    filelistener.sour.setText(null);
                    filelistener.des.setText(null);
                    filelistener.message.setText("File does not exist");
                    filelistener.jd.add(filelistener.message);
                    filelistener.message.setBounds(100, 240, 120, 20);
                }
            } else {
                filelistener.jd.remove(filelistener.message);
                filelistener.jd.revalidate();
                filelistener.jd.repaint();
                System.out.println("not exists");

                filelistener.sour.setText(null);
                filelistener.des.setText(null);
                filelistener.message.setText("Path does not exist");
                filelistener.jd.add(filelistener.message);
                filelistener.message.setBounds(100, 240, 120, 20);

            }
        }

    }

}

class filelistener implements ActionListener {
    static JDialog jd;
    static JLabel source, dest;
    static JLabel message = new JLabel();
    static JTextField sour, des;
    static JButton but1, but2, but3, but4, but5;

    @Override
    public void actionPerformed(ActionEvent e) {
        mainlistener ml = new mainlistener();
        // TODO Auto-generated method stub
        if (e.getSource() == filecontrollergui.b1) {
            jd = new JDialog(filecontrollergui.jf, "Organize");
            jd.setLayout(null);

            but1 = new JButton("Organize");
            source = new JLabel("Enter path");
            sour = new JTextField();
            // dest = new JLabel();
            // des = new JTextArea();

            jd.add(source);
            source.setBounds(30, 130, 150, 30);
            jd.add(sour);
            sour.setBounds(110, 130, 150, 30);
            jd.add(but1);
            but1.setBounds(100, 200, 110, 20);

            but1.addActionListener(ml);
            jd.setSize(300, 350);
            jd.setBackground(Color.black);
            jd.setLocationRelativeTo(null);

            jd.setVisible(true);

        } else if (e.getSource() == filecontrollergui.b2) {
            jd = new JDialog(filecontrollergui.jf, "Move");
            jd.setLayout(null);

            but2 = new JButton("Move");
            source = new JLabel("Enter source");
            sour = new JTextField();
            dest = new JLabel("Enter dest.");
            des = new JTextField();

            jd.add(source);
            source.setBounds(30, 80, 150, 30);
            jd.add(sour);
            sour.setBounds(110, 80, 150, 30);
            jd.add(dest);
            dest.setBounds(30, 130, 150, 30);
            jd.add(des);
            des.setBounds(110, 130, 150, 30);
            jd.add(but2);
            but2.setBounds(100, 200, 110, 20);

            but2.addActionListener(ml);

            jd.setSize(300, 350);
            jd.setBackground(Color.black);
            jd.setLocationRelativeTo(null);

            jd.setVisible(true);
        } else if (e.getSource() == filecontrollergui.b3) {
            jd = new JDialog(filecontrollergui.jf, "Copy");
            jd.setLayout(null);

            but3 = new JButton("Copy");
            source = new JLabel("Enter source");
            sour = new JTextField();
            dest = new JLabel("Enter dest.");
            des = new JTextField();

            jd.add(source);
            source.setBounds(30, 80, 150, 30);
            jd.add(sour);
            sour.setBounds(110, 80, 150, 30);
            jd.add(dest);
            dest.setBounds(30, 130, 150, 30);
            jd.add(des);
            des.setBounds(110, 130, 150, 30);
            jd.add(but3);
            but3.setBounds(100, 200, 110, 20);

            but3.addActionListener(ml);

            jd.setSize(300, 350);
            jd.setBackground(Color.black);
            jd.setLocationRelativeTo(null);

            jd.setVisible(true);
        } else if (e.getSource() == filecontrollergui.b4) {
            jd = new JDialog(filecontrollergui.jf, "Delete");
            jd.setLayout(null);

            but4 = new JButton("delete");
            source = new JLabel("Enter path");
            sour = new JTextField();

            jd.add(source);
            source.setBounds(30, 130, 150, 30);
            jd.add(sour);
            sour.setBounds(110, 130, 150, 30);
            jd.add(but4);
            but4.setBounds(100, 200, 110, 20);

            but4.addActionListener(ml);

            jd.setSize(300, 350);
            jd.setBackground(Color.black);
            jd.setLocationRelativeTo(null);

            jd.setVisible(true);
        } else if (e.getSource() == filecontrollergui.b5) {
            jd = new JDialog(filecontrollergui.jf, "Search");
            jd.setLayout(null);

            but5 = new JButton("Search");
            source = new JLabel("Enter file name");
            sour = new JTextField();
            dest = new JLabel("Enter root directory");
            des = new JTextField();

            jd.add(source);
            source.setBounds(15, 80, 150, 30);
            jd.add(sour);
            sour.setBounds(130, 80, 150, 30);
            jd.add(dest);
            dest.setBounds(15, 130, 150, 30);
            jd.add(des);
            des.setBounds(130, 130, 150, 30);
            jd.add(but5);
            but5.setBounds(100, 200, 110, 20);

            but5.addActionListener(ml);

            jd.setSize(300, 350);
            jd.setBackground(Color.black);
            jd.setLocationRelativeTo(null);

            jd.setVisible(true);
        }

    }

}

public class filecontrollergui {
    static JFrame jf;
    static JButton b1, b2, b3, b4, b5;
    static JLabel organize, move, copy, delete, search;

    public static void main(String[] args) {
        jf = new JFrame("File Manager");
        b1 = new JButton("organize");
        b2 = new JButton("move");
        b3 = new JButton("copy");
        b4 = new JButton("delete");
        b5 = new JButton("search");

        organize = new JLabel("Organizer");
        move = new JLabel("Mover");
        copy = new JLabel("Copy");
        delete = new JLabel("Delete");
        search = new JLabel("Search");

        jf.setLayout(null);
        jf.add(organize);
        organize.setBounds(80, 90, 100, 40);
        jf.add(b1);
        b1.setBounds(200, 90, 100, 30);

        jf.add(move);
        move.setBounds(80, 130, 100, 40);
        jf.add(b2);
        b2.setBounds(200, 130, 100, 30);

        jf.add(copy);
        copy.setBounds(80, 170, 100, 40);
        jf.add(b3);
        b3.setBounds(200, 170, 100, 30);

        jf.add(delete);
        delete.setBounds(80, 210, 100, 40);
        jf.add(b4);
        b4.setBounds(200, 210, 100, 30);

        jf.add(search);
        search.setBounds(80, 250, 100, 40);
        jf.add(b5);
        b5.setBounds(200, 250, 100, 30);

        filelistener fl = new filelistener();
        b1.addActionListener(fl);
        b2.addActionListener(fl);
        b3.addActionListener(fl);
        b4.addActionListener(fl);
        b5.addActionListener(fl);

        jf.setBackground(Color.gray);
        jf.setSize(350, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}
