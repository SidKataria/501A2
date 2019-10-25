/**
 * Inspector Class for Refection
 * @author sid
 *
 */
public class Inspector {
	
	private Class superC = null;
	private Class[] interfaces = null;
	private int counter1 = 0;
	private int counter2 = 0;
	
	public Inspector() {
	}

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        System.out.println("");
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
        //1. Getting the name of the declaring class
        getObjClass(c, recursive);
        
        //2. Getting the name of the immediate superclass and parent classes if any
        getObjSuperClass(c, recursive);
        
        //3. Getting the name of interfaces implemented
        //getInterfaces(c, recursive);
    }
    
    //Method to get object's declaring class's name
    private void getObjClass(Class c, boolean recursive) {
    	System.out.println("Declaring Class: " + c.getName());
    	getInterfaces(c, recursive, 0);
    }
    
    //Method to get object's superclass
    private void getObjSuperClass(Class c, boolean recursive) {
    	if (c != null && c.getName() != "java.lang.Object") {
    		counter2++;
        	superC = c.getSuperclass();
        	System.out.println("	SuperClass: " + superC.getName());
        	getInterfaces(superC, recursive, counter2);
        }
    	getSuperClasses(superC, recursive);
    }
    
    //Method to get parent classes to SuperClass if any
    private void getSuperClasses(Class superC, boolean recursive) {
    	while (superC != null && superC.getName() != "java.lang.Object") {
        	counter1++;
        	superC = superC.getSuperclass();
        	for (int i = -1; i <= counter1; i++)
        		System.out.print("	");
        	System.out.println("SuperClass: " + superC.getName());
        	getInterfaces(superC, recursive, counter1);
        }
    }
    
    //Method to get the object's implemented interfaces
    private void getInterfaces(Class c, boolean recursive, int counter) {
    	interfaces = c.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
        	for (int j = 0; j <= counter; j++)
        		System.out.print("	");
        	System.out.println("Interface: " + interfaces[i]);
        }
    }
    

}
