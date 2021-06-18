/**
 * The main part of the calculator doing the calculations.
 *
 * @author David J. Barnes and Michael Kolling
 * @author Anni & Katya
 * @version 18/06/2021
 */
public class CalcEngine {
	// The current value (to be) shown in the display.
	protected String displayValue;

	// This field saves the last used operator
	private char lastOperator;

	// Current (Gregorian) date
	protected GregorianDate date = new GregorianDate();

	// Constructor
	public CalcEngine() {
		clear();
	}

	/**
	 * @return The value that should currently be displayed on the calculator
	 *         display.
	 */
	public String getDisplayString() {
		return displayValue;
	}

	/**
	 * A number or operator button was pressed.
	 * 
	 * @param Input = The number or operator pressed on the calculator.
	 */
	public void buttonPressed(String input) { // number and operator
		displayValue += input;
	}

	/**
	 * Calculate new date after "=" was pressed if "+" is pressed, add number of
	 * days to a date, displaying new date if "-" is pressed, subtract a number of
	 * days from a date, displaying new date or subtract two dates, giving the
	 * number of days between the dates
	 */
	public void output() {
		JulianDate jd = date.calculateJD();

		switch (lastOperator) {
		case '+':
			if (displayValue.matches("\\d+")) {
				jd.plus(Integer.parseInt(displayValue));
				date = jd.calculateGreg();
				displayValue = date.toString();
			}
			break;
		case '-':
			if (displayValue.matches("\\d+")) {
				jd.plus(-1 * Integer.parseInt(displayValue));
				date = jd.calculateGreg();
				displayValue = date.toString();

				// if input is a value in date format, subtract both dates, output number of
				// days between dates
				// Source:
				// https://stackoverflow.com/questions/15491894/regex-to-validate-date-format-dd-mm-yyyy

			} else if (displayValue.matches("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$")) {
				setDate();
				JulianDate jd2 = date.calculateJD();
				displayValue = jd.minus(jd2) + "";
			}
			// If input is not correct
			else
				throw new IllegalArgumentException("Please try again.");
			break;
		}
	}
	
	/**
	 * Gregorian date into input date split date after dot in values for year, month
	 * and day
	 */
	public void setDate() {
		String[] dmy = displayValue.split("\\.");

		date.setYear(Integer.parseInt(dmy[2]));
		date.setMonth(Integer.parseInt(dmy[1]));
		date.setDay(Integer.parseInt(dmy[0]));
	}

	/**
	 * Get Gregorian date
	 * 
	 * @return a String of Gregorian Date in format mm.dd.yyyy
	 */
	public String getDate() {
		if (date.year() == 0 && date.month() == 0 && date.day() == 0)
			return "dd.mm.yyyy";
		else
			return date.toString();
	}

	/**
	 * Get weekday of date
	 */
	public String weekday() {
		setDate();
		JulianDate jd = date.calculateJD();
		return jd.weekday();
	}


	/**
	 * Apply an operator.
	 *
	 * @param operator The operator to apply.
	 */
	public void applyOperator(char operator) {
		lastOperator = operator;
		setDate();
		displayValue = "";
	}

	/**
	 * The 'AC' (clear) button was pressed. Reset everything to a starting state.
	 */
	public void clear() {
		displayValue = "";
		date = new GregorianDate(0, 0, 0);
	}

	/**
	 * @return The title of this calculation engine.
	 */
	public String getTitle() {
		return "Java Calculator 2021";
	}

	/**
	 * @return The author of this engine.
	 */
	public String getAuthor() {
		return "David J. Barnes & Michael";
	}

	/**
	 * @return The version number of this engine.
	 */
	public String getVersion() {
		return "Version 3.0, extended by Anni & Katya";
	}

}