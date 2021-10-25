package io.github.rosemoe.editor.core.util;

import java.util.Map;
import java.util.Set;

import static java.lang.Thread.currentThread;

/**
 * Retrieve a proper caller name from the callstack (useful for debugging).
 */
public class CallStack {

    // Ignore theses classname tools
    public static String [] classnameMustNotContains = new String[] {
            "PerformanceReporter", ".util.Logger", ".util.CallStack", "dump"
    };

    public static String getLastCaller() {
        return getLastCaller(Thread.getAllStackTraces().entrySet());
    }

    public static String getLastCaller(Integer off) {
        return getLastCaller(Thread.getAllStackTraces().entrySet());
    }

    private static String getLastCaller(Set<Map.Entry<Thread, StackTraceElement[]>> set) {
        String c = "§";
        for (Map.Entry<Thread, StackTraceElement[]> entry : set) {
            if ( entry.getKey() == currentThread() ) {
                for(StackTraceElement ste : entry.getValue()) {
                    String classname = ste.getClassName();
                    String methodname = ste.getMethodName();

                    if (    (classname.lastIndexOf("io.github.rosemoe.editor") != -1 || classname.lastIndexOf("org.antlr.v4") != -1) ) {
                        boolean skip = false;
                        for(String ex : classnameMustNotContains) {
                            if ( methodname.lastIndexOf(ex) != -1 ) {
                                skip = true;
                                break;
                            }
                        }
                        if ( skip ) {

                        } else {
                            return clearName(classname) + c + clearName(methodname + c + ste.getLineNumber());
                        }
                    }
                }
            }
        }
        return null;
    }

    private static String clearName(String name) {
        int i = name.lastIndexOf(".");
        if (i == -1) {
            return name;
        }
        return name.substring(i+1);
    }
    public static void printStackTrace() {
        printStackTrace(Thread.getAllStackTraces().entrySet());
    }
    public static void printStackTrace(Set<Map.Entry<Thread, StackTraceElement[]>> set) {
        for (Map.Entry<Thread, StackTraceElement[]> entry : set) {
            if (entry.getKey() == currentThread()) {
                for (StackTraceElement ste : entry.getValue()) {
                    Logger.debug("                  ",ste.getClassName(),"§§",ste.getMethodName(),"§§",ste.getLineNumber());
                }
            }
        }
    }
    public static void printAllStackTrace() {
        printAllStackTrace(Thread.getAllStackTraces().entrySet());
    }
    public static void printAllStackTrace(Set<Map.Entry<Thread, StackTraceElement[]>> set) {
        for (Map.Entry<Thread, StackTraceElement[]> entry : set) {
            for (StackTraceElement ste : entry.getValue()) {
                Logger.debug("                  ",ste.getClassName(),"§§",ste.getMethodName(),"§§",ste.getLineNumber());

            }
        }
    }
}