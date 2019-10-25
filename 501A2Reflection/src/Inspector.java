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
	private Class[] exps;
	private Field[] fields;
	private Class returnType;
	private Object value;
	private Object obj;
	private int finalCount;
	
	public Inspector() {
		finalCount=0;
		classConstructors = null;
		interfaces = null;
		superC = null;
		paramTypes = null;
		modifier = -1;
		mod = null;
		methods = null;
		returnType = null;
		exps = null;
		fields = null;
		value = null;
	}

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        System.out.println("");
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
        //Getting the name of the declaring class
        getObjClass(c, recursive, obj);
        
        //Getting the name of the immediate superclass and parent classes if any and implemented interfaces within
        getObjSuperClass(c, recursive, obj);
        
    }
    
    //Method to get object's declaring class's name
    private void getObjClass(Class c, boolean recursive, Object obj) {
    	System.out.println("Declaring Class-> \n" + c + "\n name:" + c.getName());
    	finalCount++;
    	checkArray(c, finalCount);
    	getClassConstructors(c, finalCount, recursive);
    	getDecMethods(c, finalCount, recursive);
    	getInterfaces(c, recursive, finalCount);
    	getClassFields(c, recursive, finalCount, obj);
    }
    
    //Method to check if class is an array
    private void checkArray(Class c, int counter) {
    	
    }
    //Method to get a class's declared methods
    private void getDecMethods(Class c, int counter, boolean recursive) {
    	methods = c.getDeclaredMethods();
    	for (Method m : methods) {
    		properPrint(counter, "Method->");
    		properPrint(counter-1, "none");
    		System.out.println(" name: " + m.getName());
    		getParamTypes(m, recursive, counter);
    		getExps(m, recursive, counter);
    		getMods(m, recursive, counter);
    		getReturn(m, recursive, counter);
    	}
    }
    //Method to get a method's return type
    private void getReturn(Method m, boolean recursive, int counter) {
    	returnType = m.getReturnType();
    	properPrint(counter, "Return->");
		properPrint(counter, "none");
		System.out.println(" type: " + returnType.getName());
    }
    //Method to get Method's Exceptions 
    private void getExps(Method m, boolean recursive, int counter) {
    	exps = m.getExceptionTypes();
		for (Class e : exps) {
			properPrint(counter, "Exception->");
			properPrint(counter-1, "none");
			System.out.println(" type: " + e.getName());
		}
    }
    //Method to get a Class's fields
    private void getClassFields(Class c, boolean recursive, int counter, Object obj) {
    	fields = c.getDeclaredFields();
    	for (Field f : fields) {
    		f.setAccessible(true);
    		properPrint(counter, "Field->");
			properPrint(counter-1, "none");
			System.out.println(" name: " + f.getName());
			properPrint(counter, "none");
			System.out.println(" type: " + f.getType().getName());
			properPrint(counter, "none");
			int mInt = f.getModifiers();
	    	mod = getModString(mInt, recursive);
			System.out.println(" modifier: " + mod);
			properPrint(counter, "none");
			getValue(c, f, counter-1, recursive, obj);
    	}
    }
    //Method to get field's current value
    private void getValue(Class c, Field f, int counter, boolean recursive, Object obj) {
    	try {
			value = f.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if (!f.getType().isPrimitive()) {
    		if (recursive == false) {
        		properPrint(counter-1, "none");
        		System.out.println(" value: " + f.getClass().getName() + "@" + f.hashCode());
    		}
    	}
    	else {
    		properPrint(counter, "none");
    		System.out.println(" value: " + value);
    	}
    }
    //Method to get Class Constructors
    private void getClassConstructors(Class c, int counter, boolean recursive) {
    	classConstructors = c.getConstructors();
    	for (Constructor cc : classConstructors) {
    		properPrint(counter, "Constructor->");
    		System.out.println(cc);
    		properPrint(counter, "none");
    		System.out.println("  name: " + cc.getName());
    		getParamTypes(cc, recursive, counter);
    		getMods(cc, recursive, counter);
    	}
    }
    //Method to get Constructor's modifiers
    private void getMods(Constructor cc, boolean recursive, int counter) {
    	int mInt = cc.getModifiers();
    	mod = getModString(mInt, recursive);
		properPrint(counter, "Modifier->");
		properPrint(counter-1, "none");
		System.out.println(" type: " + mod);
    }
    //Method to get Method's modifiers
    private void getMods(Method m, boolean recursive, int counter) {
    	int mInt = m.getModifiers();
    	mod = getModString(mInt, recursive);
		properPrint(counter, "Modifier->");
		properPrint(counter-1, "none");
		System.out.println(" type: " + mod);
    }
    //Method to get Constructor's parameters
    private void getParamTypes(Constructor c, boolean recursive, int counter) {
    	paramTypes = c.getParameterTypes();
		for (int i = 0; i < paramTypes.length; i++) {
			properPrint(counter, "ParameterType->");
			properPrint(counter-1, "none");
			System.out.println(" param" + "["+i+"]: " + paramTypes[i].getName());
		}
    }
    //Method to get method's parameters
    private void getParamTypes(Method m, boolean recursive, int counter) {
    	paramTypes = m.getParameterTypes();
		for (int i = 0; i < paramTypes.length; i++) {
			properPrint(counter, "ParameterType->");
			properPrint(counter-1, "none");
			System.out.println(" param" + "["+i+"]: " + paramTypes[i].getName());
		}
    }
    
    //Method to get object's superclass
    private void getObjSuperClass(Class c, boolean recursive, Object obj) {
    	if (c != null && c.getName() != "java.lang.Object") {
    		finalCount++;
        	superC = c.getSuperclass();
        	checkArray(superC, finalCount);
        	properPrint(finalCount, "SuperClass->");
        	System.out.println(superC);
        	properPrint(finalCount-1, "none");
        	System.out.println(" name: " + superC.getName());
        	getClassConstructors(superC, finalCount, recursive);
        	getDecMethods(superC, finalCount, recursive);
        	getInterfaces(superC, recursive, finalCount);
        	getClassFields(superC, recursive, finalCount, obj);
        }
    	getSuperClasses(superC, recursive, obj);
    }
    
    //Method to get parent classes to SuperClass if any
    private void getSuperClasses(Class superC, boolean recursive, Object obj) {
    	while (superC != null && superC.getName() != "java.lang.Object") {
        	finalCount++;
        	superC = superC.getSuperclass();
        	checkArray(superC, finalCount);
        	properPrint(finalCount, "SuperClass->");
        	System.out.println(superC);
        	properPrint(finalCount-1, "none");
        	System.out.println(" name: " + superC.getName());
        	getClassConstructors(superC, finalCount, recursive);
        	getDecMethods(superC, finalCount, recursive);
        	getInterfaces(superC, recursive, finalCount);
        	getClassFields(superC, recursive, finalCount, obj);
        }
    }
    
    //Method to get the object's implemented interfaces
    private void getInterfaces(Class c, boolean recursive, int counter) {
    	interfaces = c.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
        	properPrint(counter, "Interface->");
        	System.out.println(interfaces[i]);
        	properPrint(counter+1, "none");
        	System.out.println(" name: " + interfaces[i].getName());
        	getClassConstructors(interfaces[i], counter+1, recursive);
        	getDecMethods(interfaces[i], counter+1, recursive);
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
    
    //Converting integer value of modifier to String without toString method
    private String getModString(int modifier, boolean recursive) {
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
