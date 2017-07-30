package util.generator;

import java.awt.FlowLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Tester for WebImageCollector, displays
 * search on JFrame.
 * 
 * @author maddiebriere
 *
 */

public class WebImageCollectorTest {
	
    public static void main(String avg[]) throws IOException
    {
        new WebImageCollectorTest();
    }

    public WebImageCollectorTest() throws IOException
    {
        ImageInfo img= WebImageCollector.findAndSaveRandomIcon
        		(new Random(), "bomb", new ArrayList<String>(), new ArrayList<Integer>());
        ImageIcon icon=new ImageIcon(img.getMyImage());
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(200,300);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
