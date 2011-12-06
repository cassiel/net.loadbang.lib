//	$Source$
//	$Id$

package net.loadbang.mxj.oldosc;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/** Inspect the argument object (an OSCHandler) and build a dispatch table
 	of its methods and sub-handlers.

	@author Nick Rothwell, nick@cassiel.com
 */

public class OscHandlerLoader {
    private OscHandler itsHandler;
    
    public OscHandlerLoader(OscHandler handler) {
        itsHandler = handler;
    }
    
    private void dump1(String indent, String name, OscHandler obj) {
        if (obj instanceof OscHandler) {
	        Class cl = obj.getClass();
	        Method[] methods = cl.getDeclaredMethods();
	        
	        for (int i = 0; i < methods.length; i++) {
	            Method m = methods[i];
	            
	            if (   m.getName().startsWith("get")
	                && m.getParameterTypes().length == 0
	                && m.getReturnType().equals(OscHandler.class)
	               ) {
	                //	Looks like a sub-object to me...
	                System.out.println(indent + "sub-handler: " + m.getName());
	                //	We actually have to invoke the getter to find its methods:
	                try {
	                    OscHandler handler = (OscHandler) m.invoke(obj, null);
	                    dump1(indent + "    ", m.getName(), handler);
	                } catch (IllegalAccessException exn) {
	                    System.err.println("IllegalAccessException: " + exn);
	                } catch (InvocationTargetException exn) {
	                    System.err.println("InvocationTargetException: " + exn);
	                }
	            } else if (   m.getName().startsWith("set")
	                       && m.getReturnType().equals(void.class)
	                      ) {
	                System.out.println(indent + "setter: " + m.getName());
	                Class[] argTypes = m.getParameterTypes();
	                for (int j = 0; j < argTypes.length; j++) {
	                    Class c = argTypes[j];
	                    if (c.isArray()) {
	                        System.out.println(indent + "  array of >>>" + c.getComponentType() + "<<<");
	                    } else {
	                        System.out.println(indent + "  >>>" + c + "<<<");
	                    }
	                }
	            } else {
	                System.out.println(indent + "other method: " + m.getName());
	            }
	        }
        } else {
            System.out.println(indent + name + " is not an instance of OSCHandler");
        }
    }
    
    public void dump() {
        dump1("", "(root)", itsHandler);
    }
}
