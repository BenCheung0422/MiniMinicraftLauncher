package minilauncher.core;

public class Log extends App {
    public static void debug(String logString) {
        if (isDebug) System.out.println(logString);
    }
}
