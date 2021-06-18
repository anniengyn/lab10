import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class JulianDate {


	int year;
	int month;
	int day;
	long julianDate;
	public static int JGREG;
	public static double HALFSECOND;

	public JulianDate(Calendar c) {
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DAY_OF_MONTH);
		julianDate = calculateJD();
		JGREG = 15 + 31 * (10 + 12 * 1582);
		HALFSECOND = 0.5;
	}

	public JulianDate(long jd) {
		julianDate = jd;
	}
	
	public static void main(String[] args) {

		System.out.println("Please enter your birthday (dd.mm.yyyy):");
		Scanner s = new Scanner(System.in);
		String input = s.nextLine();

		Calendar c = Calendar.getInstance();
		Calendar t = Calendar.getInstance();

		// https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/text/SimpleDateFormat.html
		try {
			c.setTime(new SimpleDateFormat("dd.MM.yyyy").parse(input));
		} catch (ParseException e1) {
			System.out.println("Error: Wrong format. Please try again.");
		}

		JulianDate jd = new JulianDate(c);

		JulianDate today = new JulianDate(t);

		if (t.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH)
				&& t.get(Calendar.MONTH) + 1 == c.get(Calendar.MONTH) + 1) {
			System.out.printf("\nHappy Birthday! It seems like you survived another year.");
		}

		System.out.printf("\nYour Julian date is : " + jd.calculateJD() + ".");

		System.out.printf("\nYou were born on a beautiful %s.", jd.weekday());

		System.out.printf("\nAnd guess what... you are %d days old. :)", (today.calculateJD() - jd.calculateJD()));

	}
	
	/*
	 * Calculate Julian date
	 */ // Source: https://www.hermetic.ch/cal_stud/jdn.htm

	public int calculateJD() {
		return (1461 * (year + 4800 + (month - 14) / 12)) / 4 + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12
				- (3 * ((year + 4900 + (year - 14) / 12) / 100)) / 4 + day - 32075 + 1;
	}
	


	/**
	 * Converts Julian date into Gregorian
	 * // https://stackoverflow.com/questions/3017954/convert-a-julian-date-to-regular-calendar-date
	 */
	public GregorianDate calculateGreg() {

		int jalpha,jb,jc,JD,je,year,month,day;
		double julian = julianDate + HALFSECOND / 86400.0;
		int ja = (int) julianDate;
		if (ja>= JGREG) {
			jalpha = (int) (((ja - 1867216) - 0.25) / 36524.25);
			ja = ja + 1 + jalpha - jalpha / 4;
		}

		jb = ja + 1524;
		jc = (int) (6680.0 + ((jb - 2439870) - 122.1) / 365.25);
		JD = 365 * jc + jc / 4;
		je = (int) ((jb - JD) / 30.6001);
		day = jb - JD - (int) (30.6001 * je);
		month = je - 1;
		if (month > 12) month = month - 12;
		year = jc - 4715;
		if (month > 2) year--;
		if (year <= 0) year--;

		return new GregorianDate(year, month, day);
	}

	
	/*
	 * Get weekday, return "nice day" if it can not find weekday.
	 */// https://java.meritcampus.com/core-java-questions/Print-week-days-using-switch-statement

	public String weekday() {
		int w = (int) ((julianDate % 7) + 1);
		switch (w) {
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thursday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";
		case 7:
			return "Sunday";
		default:
			return "nice day";
		}
	}
	
	public long getJD() {
		return julianDate;
	}

	public void plus(int day) {
		julianDate += day;
	}

	public int minus(JulianDate minus) {
		return (int) Math.abs(julianDate - minus.getJD());
	}

	public String toString() {
		return julianDate + "";
	}

}