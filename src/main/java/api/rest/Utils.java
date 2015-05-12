
package api.rest;

public class Utils {
	
	public Utils() {
	
		// TODO Auto-generated constructor stub
	}
	
	public static void checkNulls(Object... objects) {
	
		for (Object object : objects) {
			
			if (object == null) {
				
				throw new NullPointerException();
				
			}
			
		}
		
	}
	
}
