/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servers;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Christopher
 */
public class ClientUI extends JFrame
  {

    private static BufferedReader in;
    private static PrintWriter out;
    private static String name = "";
    private static String serverIP;
    private static ClientUI mainWindow;
    private static boolean running = true;

    public ClientUI(String[] IPandUser) throws IOException
      {
        initComponents();

        // Add Listeners
        txfInput.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
              {
                out.println(txfInput.getText());
                txfInput.setText("");
              }
          });
        start(IPandUser);
      }

    private String getUserName()
      {

        name = JOptionPane.showInputDialog(
                this,
                "Choose a screen name:",
                "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);
        System.out.println("Connected as: " + name);
        return name;
      }

    private void start(String[] IPandUser) throws IOException
      {
        
            serverIP = IPandUser[0];
            name = IPandUser[1];
            this.setVisible(true);

            //Socket socket = new Socket("45.222.15.224", 9001);
            Socket socket = new Socket(serverIP, 9001);

            System.out.println("Connecting to: " + serverIP);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Process all messages from server, according to the protocol.
            int counter = 0;
            try
              {
                while (running)
                  {
                    String line = in.readLine();
                    if (line.startsWith("SUBMITNAME") && running)
                      {
                        if (counter == 0)
                          {
                            out.println(name);
                            counter++;
                          } else
                          {
                            out.println(getUserName());
                          }

                      } else if (line.startsWith("NAMEACCEPTED") && running)
                      {
                        counter = 0;
                        txfInput.setEditable(true);
                        txfInput.requestFocus();
                        this.setTitle("Sup Bitch. Speaking as: " + name + "@" + serverIP);
                      } else if (line.startsWith("MESSAGE") && running)
                      {
                        
                        line = emojiFilter(line);
                        
                        if (!line.substring(line.lastIndexOf("@") + 1, line.length()).equals(name))
                          {
                            Toolkit.getDefaultToolkit().beep();
                            txaMain.setText(txaMain.getText() + line.substring(line.lastIndexOf("@") + 1, line.length()) + ": " + line.substring(8, line.lastIndexOf("@")) + "\n");
                          } else
                          {
                            txaMain.setText(txaMain.getText() + "Me: " + line.substring(8, line.lastIndexOf("@")) + "\n");
                          }
                      }
                  }
              } catch (NullPointerException e)
              {
                System.out.println(e);
              }


      }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        txaMain = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        txfInput = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        btnEmojiHelp = new javax.swing.JMenuItem();
        btnChangeServer = new javax.swing.JMenuItem();
        btnChangeName = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txaMain.setEditable(false);
        txaMain.setColumns(20);
        txaMain.setRows(5);
        jScrollPane1.setViewportView(txaMain);

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        txfInput.setEditable(false);

        jMenu1.setText("Options");

        btnEmojiHelp.setText("Emoji Help");
        btnEmojiHelp.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnEmojiHelpActionPerformed(evt);
            }
        });
        jMenu1.add(btnEmojiHelp);

        btnChangeServer.setText("Change Server");
        btnChangeServer.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnChangeServerActionPerformed(evt);
            }
        });
        jMenu1.add(btnChangeServer);

        btnChangeName.setText("Change Name");
        btnChangeName.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnChangeNameActionPerformed(evt);
            }
        });
        jMenu1.add(btnChangeName);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txfInput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfInput, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        out.println(txfInput.getText());
        txfInput.setText("");
        txfInput.requestFocus();
// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnChangeNameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnChangeNameActionPerformed
    {//GEN-HEADEREND:event_btnChangeNameActionPerformed
    }//GEN-LAST:event_btnChangeNameActionPerformed

    private void btnChangeServerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnChangeServerActionPerformed
    {//GEN-HEADEREND:event_btnChangeServerActionPerformed

    }//GEN-LAST:event_btnChangeServerActionPerformed

    private void btnEmojiHelpActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnEmojiHelpActionPerformed
    {//GEN-HEADEREND:event_btnEmojiHelpActionPerformed
        EmojiHelpUI.startUI();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEmojiHelpActionPerformed

    
    public static String emojiFilter(String line)
      {
        String s = line.toLowerCase();
        
        String Ejoy = "😂";
        String EmidFing = "🖕";
        String ErollEyes = "🙄";
        String Efire = "🔥";
        
        String joy = ":joy:";
        String midFing = ":midfing:";
        String rollEyes = ":rolleyes:";
        String fire = ":fire:";
        
        if(s.contains(joy))
            line = line.substring(0, s.indexOf(joy)) + Ejoy + line.substring(s.indexOf(joy)+joy.length(), s.length());
        if(s.contains(midFing))
            line = line.substring(0, s.indexOf(midFing)) + EmidFing + line.substring(s.indexOf(midFing)+midFing.length(), s.length());
        if(s.contains(rollEyes))
            line = line.substring(0, s.indexOf(rollEyes)) + ErollEyes + line.substring(s.indexOf(rollEyes)+rollEyes.length(), s.length());
        if(s.contains(fire))
            line = line.substring(0, s.indexOf(fire)) + Efire + line.substring(s.indexOf(fire)+fire.length(), s.length());
        
        return line;
      }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnChangeName;
    private javax.swing.JMenuItem btnChangeServer;
    private javax.swing.JMenuItem btnEmojiHelp;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txaMain;
    protected javax.swing.JTextField txfInput;
    // End of variables declaration//GEN-END:variables
  }
