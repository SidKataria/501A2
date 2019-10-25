/**
 * Inspector Class for Refection
 * @author sid
 *
 */
public class Inspector {
	
	public Inspector() {
	}

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        System.out.println("");
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
    	Class superC = null;
    	Class[] interfaces = null;
    	int counter1 = 0;
    	int counter2 = 0;

        //1. Getting the name of the declaring class
        System.out.println("Declaring class: " + c.getName());
        
        //2. Getting the name of the immediate superclass
        if (c != null && c.getName() != "java.lang.Object") {
        	superC = c.getSuperclass();
        	System.out.println("	SuperClass: " + superC.getName());
        }
        
        //2a. Getting superclasses if more
        while (superC != null && superC.getName() != "java.lang.Object") {
        	counter1++;
        	superC = superC.getSuperclass();
        	for (int i = -1; i <= counter1; i++)
        		System.out.print("	");
        	System.out.println("SuperClass: " + superC.getName());
        }
        
        //3. Getting the name of interfaces implemented
        interfaces = c.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
        	counter2++;
        	System.out.println("	Interface: " + interfaces[i]);
        }
    }

}
