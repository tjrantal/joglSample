package timo.test;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

public class SimpleScene {
    public static void main(String[] args) {
    	GLProfile.initSingleton();	/*Needed for 3D*/
        GLProfile glp = GLProfile.get(GLProfile.GL2); /*My maching supports up to GL2...*/ //.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);
        Frame frame = new Frame("AWT Window Test");
        frame.setSize(300, 300);
        frame.add(canvas);
        frame.setVisible(true);
        
        // by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}

