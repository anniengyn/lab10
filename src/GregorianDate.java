public class GregorianDate {

	int day;
	int month;
	int year;

	public GregorianDate() {

	}

	public GregorianDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	// Getters

	public int day() {
		return day;
	}

	public int month() {
		return month;
	}

	public int year() {
		return year;
	}

	// Setters

	public void setDay(int day) {

		this.day = day;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setYear(int year) {
		this.year = year;
	}

	/*
	 * Calculate Julian date
	 */ // Source: https://www.hermetic.ch/cal_stud/jdn.htm

	public JulianDate calculateJD() {
		long jd = (1461 * (year + 4800 + (month - 14) / 12)) / 4 + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12
				- (3 * ((year + 4900 + (year - 14) / 12) / 100)) / 4 + day - 32075 + 1;
		return new JulianDate(jd);
	}

	public String toString() {
		String DAY = (day < 10) ? "0" + day : day + "";
		String MONTH = (month < 10) ? "0" + month : month + "";
		return DAY + "." + MONTH + "." + year;
	}

}