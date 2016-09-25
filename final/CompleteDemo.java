// package com.javacodegeeks.snippets.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CompleteDemo extends JPanel implements KeyListener, ActionListener {

  JTextArea textArea;
  JTextField textFiled;
  static final String nl = "n";

  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        createAndShowGUI();
      }
    });
  }
  
  public CompleteDemo() {
    super(new BorderLayout());
    JButton jb = new JButton("Clear");
    jb.addActionListener(this);
    textFiled = new JTextField(20);
    textFiled.addKeyListener(this);
  //typingArea.setFocusTraversalKeysEnabled(false);
    textArea = new JTextArea();
    textArea.setEditable(false);
    JScrollPane scrPanl = new JScrollPane(textArea);
    scrPanl.setPreferredSize(new Dimension(375, 125));
    add(textFiled, BorderLayout.PAGE_START);
    add(scrPanl, BorderLayout.CENTER);
    add(jb, BorderLayout.PAGE_END);
  }

  @Override
  public void keyPressed(KeyEvent event) {
    display(event, "KEY PRESSED: ");
  }

  @Override
  public void keyTyped(KeyEvent event) {
    display(event, "KEY TYPED: ");
  }

  @Override
  public void actionPerformed(ActionEvent event) {
  //Clear the text components.
    textArea.setText("");
    textFiled.setText("");
  //Return the focus to the typing area.
    textFiled.requestFocusInWindow();
  }
  @Override
  public void keyReleased(KeyEvent event) {
    display(event, "KEY RELEASED: ");
  }
  protected void display(KeyEvent event, String str) {
      String keystr, modStr, tmpStr, actionstr, locStr;

      java.util.Date date= new java.util.Date();
      // System.out.println(new Timestamp(date.getTime()));

      int eventID = event.getID();
      if (eventID == KeyEvent.KEY_TYPED) {
        char key = event.getKeyChar();
        keystr = "key str = '" + key + "'";
      } else {
        int kCode = event.getKeyCode();
        keystr = "kCode = " + kCode + " ("
        + KeyEvent.getKeyText(kCode) + ")";
      }
      int mods = event.getModifiersEx();
      modStr = "mods = " + mods;
      tmpStr = KeyEvent.getModifiersExText(mods);
      if (tmpStr.length() > 0) {
        modStr += " (" + tmpStr + ")";
      } else {
        modStr += " (no mods)";
      }
      actionstr = "action key? ";
      if (event.isActionKey()) {
        actionstr += "YES";
      } else {
        actionstr += "NO";
      }
      locStr = "key location: ";
      int location = event.getKeyLocation();
      if (location == KeyEvent.KEY_LOCATION_STANDARD) {
        locStr += "standard";
      } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
        locStr += "left";
      } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
        locStr += "right";
      } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
        locStr += "numpad";
  } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
    locStr += "unknown";
  }
  textArea.append(str + nl + "    " + keystr + nl + "    "
    + modStr + nl + "    " + actionstr + nl
    + "    " + locStr + nl + " "+date.getTime()+"\n");
  // textArea.append(keystr + nl + " " + actionstr + nl
  //   + "    " + date.getTime()+"\n");
  textArea.setCaretPosition(textArea.getDocument().getLength());
  }

  private static void createAndShowGUI() {
    //Make sure we have nice window decorations.
    JFrame.setDefaultLookAndFeelDecorated(true);
    //Create and set up the window.
    JFrame frame = new JFrame("KeyEventDemo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Create and set up the content pane.
    JComponent newContentPane = new CompleteDemo();
    newContentPane.setOpaque(true); //content panes must be opaque
    frame.setContentPane(newContentPane);
    //Display the window.
    frame.pack();
    frame.setVisible(true);
  }

}