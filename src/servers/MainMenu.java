/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Christopher
 */
public class MainMenu
  {

    private static String username = "";
    private static String ip = "";

    private static ClientUI C;
    
    
    public static void main(String[] args)
      {
        
        try
          {
            if (args.length == 1 && args[0].equals("-S"))
              {
                new ChatServer().startServer();
              } else
              {
                loadConfig();
                if(username.length() == 0)
                  {
                    username = JOptionPane.showInputDialog(
                    null,
                    "Choose a screen name:",
                    "Screen name selection",
                    JOptionPane.PLAIN_MESSAGE);
                  }
                
                if(ip.length() == 0)
                  {
                    ip = JOptionPane.showInputDialog(
                    null,
                    "Enter the server IP:",
                    "Server selection",
                    JOptionPane.PLAIN_MESSAGE);
                  }
                C = new ClientUI(new String [] {ip, username});
              }
          } catch (Exception ex)
          {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
          }
      }

    public static void reloadClientUsername() throws IOException
      {
        C = new ClientUI(new String [] {ip, JOptionPane.showInputDialog("Please enter new username")});
      }
    
    public static void reloadClientServerIP() throws IOException
      {
        //C = new ClientUI(new String [] {JOptionPane.showInputDialog("Please enter new IP address"), username});
        C.dispose();
        C = new ClientUI(new String [] {"localhost", username});
      }

    public static void loadConfig()
      {
        try
          {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("Config.xml"));

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            ip = doc.getElementsByTagName("ServerIP").item(0).getTextContent();
            username = doc.getElementsByTagName("DefaultName").item(0).getTextContent();

          } catch (IOException | ParserConfigurationException | DOMException | SAXException | NullPointerException e)
          {
            System.out.println("Error: " + e);
          }
      }
  }
