package cs10proj2;

public class Main {
	public static void main(String[] args) {
		// instantiates a new DigitalClock instance with an initial time of 23:30:00
		DigitalClock dc = new DigitalClock(23, 30, 00);
		// runs the clock until it reads 00:00:00
		System.out.println("Running the clock until it reads 00:00:00");
		dc.run();
		// sets the time to 10:30:00
		System.out.println("Setting clock to 10:30:00...");
		dc.set(10, 30, 0);
		// runs the clock until it reads 11:42:33
		System.out.println("Running the clock until it reaches 11:42:33...");
		dc.run(11, 02, 33);
	}
}
