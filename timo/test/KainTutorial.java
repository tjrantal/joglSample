package timo.test;

import java.awt.BorderLayout;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;		/*GLCanvas*/
import com.jogamp.opengl.util.*;			/*FPSAnimator*/
import javax.media.opengl.glu.*;			/*GL utilities, drawing tools?*/
import javax.swing.JFrame;
import java.io.*;					/*For texture*/
import com.jogamp.opengl.util.texture.*;		/*For texture*/
import java.io.IOException;					/*Error handling*/
import timo.test.utils.*;					/*Quaternions for rotations*/

/**
 * Modified from the: 
 *A minimal JOGL demo.
 * 
 * @author <a href="mailto:kain@land-of-kain.de">Kai Ruhl</a>
 * @since 26 Feb 2009
 
 Added earth texture from 
 http://visibleearth.nasa.gov/view.php?id=73580
 
 Kain suggested
 http://planetpixelemporium.com/earth.html
 which has an elevation map as well (interesting thought...)
 
 */
public class KainTutorial extends GLCanvas implements GLEventListener {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The GL unit (helper class). */
    private GLU glu;

    /** The frames per second setting. */
    private int fps = 60;

    /** The OpenGL animator. */
    private FPSAnimator animator;
    
    /*Texture*/
    private Texture earthTexture;
	private float rotationAngle;
	private Quaternion earthAxis;
	private Quaternion rotationAxis;
    /**
     * A new mini starter.
     * 
     * @param capabilities The GL capabilities.
     * @param width The window width.
     * @param height The window height.
     */
    public KainTutorial(GLCapabilities capabilities, int width, int height) {
    	super(capabilities);
        setSize(width, height);
        addGLEventListener(this);
        rotationAngle = 0;
        /*Init initial Orientation*/
        /*
        earthAxis = new Quaternion(Math.cos(113f/2f),Math.sin(113f/2f),0,0);
        Quaternion tempRotation = new Quaternion(Math.cos(1f/2f),0,Math.sin(1f/2f),0);
        Quaternion tempQ = tempRotation.times(earthAxis);
        rotationAxis = tempQ.times(tempRotation.conjugate()); 
        */
        /*Init rotation Quaternion*/
    }

    /**
     * @return Some standard GL capabilities (with alpha).
     */
    private static GLCapabilities createGLCapabilities() {
        GLProfile glp = GLProfile.getDefault();//(GLProfile.GL2); /*My maching supports up to GL2...*/
        GLCapabilities capabilities = new GLCapabilities(glp);
        return capabilities;
    }

    /**
     * Sets up the screen.
     * 
     * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
     */
    public void init(GLAutoDrawable drawable) {
    
        final GL2 gl = drawable.getGL().getGL2();
        // Enable z- (depth) buffer for hidden surface removal. 
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
		
		
        // Enable smooth shading.
        gl.glShadeModel(gl.GL_SMOOTH);
		
        // Define "clear" color.
        gl.glClearColor(0f, 0f, 0f, 0f);

		
        // We want a nice perspective.
        gl.glHint(gl.GL_PERSPECTIVE_CORRECTION_HINT, gl.GL_NICEST);
		
        // Create GLU.
        glu = new GLU();


	
        // Start animator.
        animator = new FPSAnimator(this, fps);
        animator.start();
        
            	/*Try to load the texture last*/
        // Load earth texture.
        try {
            //InputStream stream = getClass().getResourceAsStream("world.topo.bathy.200401.3x5400x2700.jpg");
            //InputStream stream = new FileInputStream(new String("/home/rande/programming/java/jogl/timoTest/world.topo.bathy.200401.3x5400x2700.jpg"));
            InputStream stream = new FileInputStream(new String("earthmap1k.jpg"));
            //InputStream stream = new FileInputStream(new String("/home/rande/programming/java/jogl/timoTest/earth.jpg"));
            //TextureData data = TextureIO.newTextureData(stream, false, "jpg");
            earthTexture = TextureIO.newTexture(stream, false, "jpg");
        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
        
    }

    /**
     * The only method that you should implement by yourself.
     * 
     * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
     */
    public void display(GLAutoDrawable drawable) {
        if (!animator.isAnimating()) {
            return;
        }
        final GL2 gl = drawable.getGL().getGL2();

        // Clear screen.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Set camera.
        setCamera(gl, glu, 30);
        

        
        /*Lighting*/
        // Prepare light parameters.
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {-30, 0, 30, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Set light parameters.
        gl.glLightfv(gl.GL_LIGHT1, gl.GL_POSITION, lightPos, 0);
        gl.glLightfv(gl.GL_LIGHT1, gl.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(gl.GL_LIGHT1, gl.GL_SPECULAR, lightColorSpecular, 0);

        // Enable lighting in GL.
        gl.glEnable(gl.GL_LIGHT1);
        gl.glEnable(gl.GL_LIGHTING);

        /*Rotate gl to show earth upright*/
        /*Rotations are considered in the  local coordinate system*/
        gl.glRotatef(-90f,1,0,0);	/*Uses quaternion type expression, rotate around the axis*/
        gl.glRotatef(23f,0,1,0);	/*Uses quaternion type expression, rotate around the axis*/
        gl.glRotatef(rotationAngle,0f,0f,1f); /*Uses quaternion type expression, rotate around the axis*/
        rotationAngle +=1f;
        
        // Set material properties.
        //float[] rgba = {0.3f, 0.5f, 1f};
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(gl.GL_FRONT, gl.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(gl.GL_FRONT, gl.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(gl.GL_FRONT, gl.GL_SHININESS, 0.5f);
        
        
		/*
        // Write triangle.
        gl.glColor3f(0.9f, 0.5f, 0.2f);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(-20, -20, 0);
        gl.glVertex3f(+20, -20, 0);
        gl.glVertex3f(0, 20, 0);
        gl.glEnd();
        */
        // Draw sphere (possible styles: FILL, LINE, POINT).
        //gl.glColor3f(0.3f, 0.5f, 1f);
        
        
        // Apply texture.
        earthTexture.enable(gl);
        earthTexture.bind(gl);
        /*Draw sphere*/
        GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricTexture(earth, true);	//Confirm the texture
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final float radius = 6.378f;
        final int slices = 180;
        final int stacks = 180;
        glu.gluSphere(earth, radius, slices, stacks);
        glu.gluDeleteQuadric(earth);

    }

    /**
     * Resizes the screen.
     * 
     * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable,
     *      int, int, int, int)
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL gl = drawable.getGL();
        gl.glViewport(0, 0, width, height);
    }

    /**
     * Changing devices is not supported.
     * 
     * @see javax.media.opengl.GLEventListener#displayChanged(javax.media.opengl.GLAutoDrawable,
     *      boolean, boolean)
     */
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        throw new UnsupportedOperationException("Changing display is not supported.");
    }

    /**
     * @param gl The GL context.
     * @param glu The GL unit.
     * @param distance The distance from the screen.
     */
    private void setCamera(GL2 gl, GLU glu, float distance) {
        // Change to projection matrix.
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(0, 0, distance, 0, 0, 0, 0, 1, 0);

        // Change back to model view matrix.
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
    /*GLEventListener dispose*/
    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    /**
     * Starts the JOGL mini demo.
     * 
     * @param args Command line args.
     */
    public final static void main(String[] args) {
        GLCapabilities capabilities = createGLCapabilities();
        KainTutorial canvas = new KainTutorial(capabilities, 800, 500);
        JFrame frame = new JFrame("Mini JOGL Demo (breed)");
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        canvas.requestFocus();
    }

}
