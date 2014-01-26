package timo.test;
import timo.test.utils.*;

public class EarthRotation{

	public EarthRotation(){
		Quaternion earthAxis = new Quaternion(Math.cos(113f/2f),Math.sin(113f/2f),0,0);
		double[] eAxis = getNormAxis(earthAxis);
        Quaternion tempRotation = new Quaternion(Math.cos(1f/2f),0,Math.sin(1f/2f),0);
        Quaternion tempQ = tempRotation.times(earthAxis);
        Quaternion rotationAxis = tempQ.times(tempRotation.conjugate());
        System.out.println("rAxis = " + rotationAxis);
        double[] normAxis = getNormAxis(rotationAxis);
		printAxis(eAxis);
		printAxis(normAxis);
	}

	public static void main(String[] arg){
		EarthRotation er = new EarthRotation();
	}
	
	private double[] getNormAxis(Quaternion q){
		double[] axis = q.getAxis();
		double norm = Math.sqrt(axis[0]*axis[0]+axis[1]*axis[1]+axis[2]*axis[2]);
		double[] normAxis = new double[axis.length];
        for (int i = 0; i< normAxis.length;++i){
        	normAxis[i] = Math.sin(axis[i]/norm);
        } 
		return normAxis;
	}
	
	private void printAxis(double[] axis){
        System.out.print("Axis\t");
        for (int i = 0; i< axis.length;++i){
        	System.out.print(axis[i]+"\t");
        } 
        System.out.println();
	}
}
