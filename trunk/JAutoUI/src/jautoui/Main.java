/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jautoui;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import nu.mumma.jAutoUI.UserInputParser;

/**
 *
 * @author gustafsp
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
               
        UserInputParser k = new UserInputParser(Class.class); //insert starting class here instead of Class.class.
        k.setLayout(new FlowLayout());
        //k.createLables(uratha.Character.class);
        JFrame jf = new JFrame("do");
        jf.add(k);
        jf.setSize(500, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
        jf.setVisible(true);
        jf.paintComponents(jf.getGraphics());
    }

}
