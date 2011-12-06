//	$Id$
//	$Source$

package net.loadbang.reflect;

import net.loadbang.reflect.exn.MethodNotFound;
import net.loadbang.reflect.exn.NoMethodMatch;
import net.loadbang.reflect.exn.AmbiguousMethodMatch;
import net.loadbang.reflect.exn.Dispatch;
import net.loadbang.util.Pair;

import com.cassiel.util.INTERNAL;
import com.cycling74.max.Atom;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


/**	Dispatcher: allows an object to be registered, and performs the dispatches to it given a
 	method name and an array of Atoms.
 	
 	@author Nick Rothwell, nick@cassiel.com.
 */

public class Dispatcher {
	/**	The actual object whose methods we're dispatching. */
	private Object itsObject;
	
	/**	A map from a method name to information about its overloadings. Each
	 	overloading has an array of formal types (classes) and a method. */
	private HashMap<String, List<Pair<MethodArgInfo, Method>>> itsMethods;
	
	/**	Scoring object. (Stateless.) */
	private static ScoreTypes theScoreTypes00;
	
	private synchronized static ScoreTypes getScoreTypes() {
		if (theScoreTypes00 == null) {
			theScoreTypes00 = new ScoreTypes();
		}
		
		return theScoreTypes00;
	}

	/**	Build a dispatcher for a specific object. */
	public Dispatcher(Object obj) {
		itsObject = obj;
		theScoreTypes00 = null;

        Class cl = obj.getClass();
        Method[] methods = cl.getDeclaredMethods();
        itsMethods = new HashMap<String, List<Pair<MethodArgInfo, Method>>>();
        
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            String name = m.getName();
            Class[] types = m.getParameterTypes();
            Class returnType = m.getReturnType();
            MethodArgInfo argInfo;
            
            if (returnType == void.class) {		//	Ignore methods not returning void.
            		List<Pair<MethodArgInfo, Method>> entries00 = itsMethods.get(name);
            		
            		//	See whether the method takes a single array argument. (If not,
            		//	we encode as discrete types, even if there are arrays there -
            		//	they'll never be matched.)
            		
            		if (types.length == 1 && types[0].isArray()) {
            			argInfo = new ArrayArgInfo(types[0].getComponentType());
            		} else {
            			argInfo = new DiscreteArgInfo(types);
            		}

            		Pair<MethodArgInfo, Method> p = new Pair<MethodArgInfo, Method>(argInfo, m);
            		
            		//System.out.println(name + " -> " + p);
            		
            		if (entries00 == null) {
            			ArrayList<Pair<MethodArgInfo, Method>> l =
            				new ArrayList<Pair<MethodArgInfo, Method>>();

            			l.add(p);
            			itsMethods.put(name, l);
            		} else {
            			entries00.add(p);
            		}
            }
        }
	}
	
	/**	Return the Java class corresponding to an atom, or null if no match. */
	private Class buildActualType(Atom a) {
		if (a.isInt()) {
			return Integer.TYPE;
		} else if (a.isFloat()) {
			return Float.TYPE;
		} else if (a.isString()) {
			return String.class;
		} else {
			throw new INTERNAL("Dispatcher.buildActualType: " + a);
		}
	}
	
	/**	Build an array of class objects representing the types of args. */
	private Class[] buildActualTypes(Atom args[]) {
		Class[] result = new Class[args.length];
		
		for (int i = 0; i < args.length; i++) {
			result[i] = buildActualType(args[i]);
		}
		
		return result;
	}
	
	/**	Do a dispatch: try to match this method name and Atom list to a
 		maximally specific method. */
	public void dispatch(String methodName, Atom args[])
		throws Dispatch
	{
		try {
			ScoreTypes scorer = getScoreTypes();
	
			Class[] actuals = buildActualTypes(args);
			List<Pair<MethodArgInfo, Method>> entries00 = itsMethods.get(methodName);
			
			if (entries00 == null) {
				throw new MethodNotFound(methodName);
			} else {
				int bestScore = ScoreTypes.MISMATCH;
				Method method00 = null;
				MethodArgInfo argInfo00 = null;
				int count = 0;
				
				for (Pair<MethodArgInfo, Method> p: entries00) {
					MethodArgInfo argInfo = p.getFst();
					int score = argInfo.scoreActualArgs(scorer, actuals);
					
					//System.out.println("score for " + argInfo + " is " + score);
					
					if (score > bestScore) {
						method00 = p.getSnd();
						argInfo00 = argInfo;
						count = 1;
						bestScore = score;
					} else if (score == bestScore) {
						count++;
					}
				}
				
				if (bestScore > ScoreTypes.MISMATCH) {
					if (count == 1) {		//	We have a best score with only one match.
						method00.invoke(itsObject, argInfo00.buildActualValues(args));
					} else {
						throw new AmbiguousMethodMatch(methodName);
					}
				} else {
					throw new NoMethodMatch(methodName);
				}
			}
		} catch (MethodNotFound exn) {
			throw new Dispatch("method not found", exn);
		} catch (NoMethodMatch exn) {
			throw new Dispatch("no matching method", exn);
		} catch (AmbiguousMethodMatch exn) {
			throw new Dispatch("ambiguous method", exn);
		} catch (IllegalAccessException exn) {
			throw new Dispatch("illegal access", exn);
		} catch (java.lang.reflect.InvocationTargetException exn) {
			throw new Dispatch("invocation target", exn);
		}
	}
}
