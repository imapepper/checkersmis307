package utils;

/**
 * 
 * Designed to track the time elapsed during a game.
 * 
 * @author Anthony Tuel
 * @author Chase Erickson
 * @author Joey Hage
 *
 */

public class TurnTime {
	
	private static long startTime;
	private static long endTime;
	private static long elapsedTime;
	private static long elapsedSeconds;

	public static long startTimer() {
		startTime = System.currentTimeMillis();
		return startTime;
	}
	
	public static long endTimer() {
		endTime = System.currentTimeMillis();
		return endTime;
	}
	
	public static long elapsedTime() {
		elapsedTime = endTime - startTime;
		elapsedSeconds = elapsedTime / 1000;
		return elapsedTime;
	}
	
	public static void displayTime() {
		elapsedTime();
		System.out.println("Elapsed seconds: " + elapsedSeconds);
		System.out.println("Time: " + elapsedSeconds / 60 + ":" + elapsedSeconds % 60);	
	}
}
