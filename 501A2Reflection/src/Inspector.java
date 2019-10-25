/**
 * CPSC 501 Assignment 2
 * Inspector Class for Refection
 * @author Siddharth Kataria
 * UCID: 30000880
 * Fall 2019
 */
import java.lang.reflect.*;

public class Inspector {
	
	private Class superC;
	private Class[] interfaces;
	private Constructor[] classConstructors;
	private int modifier;
	private String mod;
	private Class[] paramTypes;
	private Method[] methods;
	private int counter1;
	private int counter2;
	
	public Inspector() {
		counter1 = 0;
		counter2= 0;
		classConstructors = null;
		interfaces = null;
		superC = null;
		paramTypes = null;
		modifier = -1;
		mod = null;
		methods = null;
	}

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        System.out.println("");
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
        //Getting the name of the declaring class
        getObjClass(c, recursive);
        
        //Getting the name of the immediate superclass and parent classes if any and implemented interfaces within
        getObjSuperClass(c, recursive);
        
        //Getting the constructors
    }
    
    //Method to get object's declaring class's name
    private void getObjClass(Class c, boolean recursive) {
    	System.out.println("Declaring Class-> \n" + c + "\n name:" + c.getName());
    	getClassConstructors(c, 1, recursive);
    	getInterfaces(c, recursive, 0);
    }
    
    //Method to get a class's declared methods
    private void getDecMethods(Class c, int counter, boolean recursive) {
    	
    }
    
    //Method to get Class Constructors
    private void getClassConstructors(Class c, int counter, boolean recursive) {
    	classConstructors = c.getConstructors();
    	for (Constructor cc : classConstructors) {
    		properPrint(counter, "Constructor->");
    		System.out.println(cc);
    		properPrint(counter, "none");
    		System.out.println(" name: " + cc.getName());
    		getParamTypes(cc, recursive, counter);
    		mod = getModString(cc, recursive);
    		properPrint(counter+1, "Modifier->");
    		properPrint(counter-1, "none");
    		System.out.println(" type: " + mod);
    	}
    }
    
    //Method to get Constructor's modifiers
    private void getParamTypes(Constructor c, boolean recursive, int counter) {
    	paramTypes = c.getParameterTypes();
		for (int i = 0; i < paramTypes.length; i++) {
			properPrint(counter+1, "ParameterType->");
			properPrint(counter-1, "none");
			System.out.println(" param" + "["+i+"]: " + paramTypes[i].getName());
		}
    }
    
    //Method to get object's superclass
    private void getObjSuperClass(Class c, boolean recursive) {
    	if (c != null && c.getName() != "java.lang.Object") {
    		counter2++;
        	superC = c.getSuperclass();
        	properPrint(counter2, "SuperClass->");
        	System.out.println(superC);
        	properPrint(counter2, "none");
        	System.out.println(" name: " + superC.getName());
        	getClassConstructors(superC, counter2+1, recursive);
        	getInterfaces(superC, recursive, counter2);
        }
    	getSuperClasses(superC, recursive);
    }
    
    //Method to get parent classes to SuperClass if any
    private void getSuperClasses(Class superC, boolean recursive) {
    	while (superC != null && superC.getName() != "java.lang.Object") {
        	counter1++;
        	superC = superC.getSuperclass();
        	properPrint((counter1 + 1), "SuperClass->");
        	System.out.println(superC);
        	properPrint(counter1, "none");
        	System.out.println(" name: " + superC.getName());
        	getClassConstructors(superC, counter1, recursive);
        	getInterfaces(superC, recursive, counter1);
        }
    }
    
    //Method to get the object's implemented interfaces
    private void getInterfaces(Class c, boolean recursive, int counter) {
    	interfaces = c.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
        	properPrint(counter + 1, "Interface->");
        	System.out.println(interfaces[i]);
        	properPrint(counter + 1, "none");
        	System.out.println(" name: " + interfaces[i].getName());
        	getClassConstructors(interfaces[i], counter+1, recursive);
        }
    }
    
    //Method to print tabs accordingly
    private void properPrint(int counter, String info) {
    	if (!info.equals("none")) {
	    	for (int i = 0; i < counter; i++)
	    		System.out.print("	");
	    	System.out.println(info + " ");
    	}
    	for (int i = 0; i < counter; i++)
    		System.out.print("	");
    	System.out.print(" ");
    }
    
    //Converting int value of modifier to String without toString method
    private String getModString(Constructor cc, boolean recursive) {
    	modifier = cc.getModifiers();
    	if (modifier == 1024)
    		return "abstract";
    	else if (modifier == 16)
    		return "final";
    	else if (modifier == 512)
    		return "interface";
    	else if (modifier == 256)
    		return "native";
    	else if (modifier == 2)
    		return "private";
    	else if (modifier == 4)
    		return "protected";
    	else if (modifier == 1)
    		return "public";
    	else if (modifier == 8)
    		return "static";
    	else if (modifier == 2048)
    		return "strict";
    	else if (modifier == 32)
    		return "synchronized";
    	else if (modifier == 128)
    		return "transient";
    	else if (modifier == 16)
    		return "volatile";
    	return "none";
    }
    
}
