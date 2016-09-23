package cs10proj2;

import java.text.DecimalFormat;

public class DigitalClock {
	private Counter hours, minutes, seconds;

	/**
	 * Initializes a DigitalClock that starts at 00:00:00
	 */
	public DigitalClock() {
		hours = new Counter(24);
		minutes = new Counter(60);
		seconds = new Counter(60);
	}

	/**
	 * Initializes a DigitalClock with specified starting hour, minute, and second
	 * @param hour - initial hour
	 * @param minute - initial minute
	 * @param second - initial second
	 */
	public DigitalClock(int hour, int minute, int second) {
		hours = new Counter(hour, 24);
		minutes = new Counter(minute, 60);
		seconds = new Counter(second, 60);
	}

	/**
	 * Sets the clock to the specified time
	 * @param hour - hour to set the clock to
	 * @param minute - minute to set the clock to
	 * @param second - second to set the clock to
	 */
	public void set(int hour, int minute, int second) {
		hours.set(hour);
		minutes.set(minute);
		seconds.set(second);
	}

	/**
	 * Moves the clock forward by 1 second
	 */
	public void tick() {
		if (seconds.tick()) // if the seconds have wrapped (val > 60) then tick minutes
			if (minutes.tick()) // if the minutes have wrapped (val > 60) then tick hours
				hours.tick();
	}

	/**
	 * Ticks the clock until it reads 00:00:00 and prints every second until then
	 */
	public void run() {
		run(0, 0, 0);
	}

	/**
	 * Ticks the clock until it reads the specified time and prints every second until then
	 * @param hour - hour to run the clock until
	 * @param minute - minute to run the clock until
	 * @param second - second to run the clock until
	 */
	public void run(int hour, int minute, int second) {
		// run the clock until hours, minutes, and seconds match the target time
		while (hour != hours.getVal() || minute != minutes.getVal() || second != seconds.getVal()) {
			tick(); // tick the clock forward one second
			System.out.println(toString()); // print out the current time in HH:MM:SS format
		}
	}

	/**
	 * Returns the current time in HH:MM:SS format
	 */
	public String toString() {
		// format the String so that hours, minutes, and seconds are always two digits
		DecimalFormat time = new DecimalFormat("00");
		return time.format(hours.getVal()) + ":" + time.format(minutes.getVal()) + ":" + time.format(seconds.getVal());
	}

}
