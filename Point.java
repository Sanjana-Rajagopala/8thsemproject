package dbscan_one;

public class Point {
	
		 private float X;
		 private float Y;
		 boolean isCore;
		 boolean isBound;
		 boolean isOutlier;
		 
		 Point()
		 {
		  X=0; Y=0;
		 }
		 Point(float x,float y)
		 {
		  X=x;
		  Y=y;
		 }
		 
		 public float getX() {
		  return X;
		 }
		 public void setX(float x) {
		  X = x;
		 }
		 public float getY() {
		  return Y;
		 }
		 public void setY(float y) {
		  Y = y;
		 }
		 
		 public String toString()
		 {
		  return "("+this.X+","+this.Y+")";
		 }
		}



