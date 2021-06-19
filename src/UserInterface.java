import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A graphical user interface for the calculator. No calculation is being done
 * here. This class is responsible just for putting up the display on screen. It
 * then refers to the "CalcEngine" to do all the real work.
 *
 * @author David J. Barnes and Michael Kolling
 * @author Anni & Katya
 * @version 18/06/2021
 */
public class UserInterface implements ActionListener {
	protected CalcEngine calc;
	protected boolean showingAuthor;

	protected JFrame frame;
	protected JTextField display;
	protected JTextField display2;
	private JLabel status;

	private JLabel currentDate;
	private JLabel weekday;

	/**
	 * Create a user interface.
	 * @param engine The calculator engine.
	 */
	public UserInterface(CalcEngine engine) {
		calc = engine;
		showingAuthor = true;
		makeFrame();
		frame.setVisible(true);
	}

	/**
	 * Set the visibility of the interface.
	 * @param visible true if the interface is to be made visible, false otherwise.
	 */
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	/**
	 * Make the frame for the user interface.
	 */
	protected void makeFrame() {
		frame = new JFrame(calc.getTitle());

		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		display = new JTextField();
		// Message shown in the beginning
		display.setText(" Insert date in format dd.mm.yyyy");
		
		display2 = new JTextField();

		contentPane.add(display);

		JPanel buttonPanel = new JPanel(new GridLayout(5, 4));
		addButton(buttonPanel, "7");
		addButton(buttonPanel, "8");
		addButton(buttonPanel, "9");
		addButton(buttonPanel, "AC");

		addButton(buttonPanel, "4");
		addButton(buttonPanel, "5");
		addButton(buttonPanel, "6");
		addButton(buttonPanel, "+");

		addButton(buttonPanel, "1");
		addButton(buttonPanel, "2");
		addButton(buttonPanel, "3");
		addButton(buttonPanel, "-");

		addButton(buttonPanel, "?");
		addButton(buttonPanel, "0");
		addButton(buttonPanel, ".");
		addButton(buttonPanel, "WEEKDAY");

		buttonPanel.add(new JLabel());
		addButton(buttonPanel, "=");

		buttonPanel.add(weekday = new JLabel("Date:  ", SwingConstants.RIGHT));
		buttonPanel.add(currentDate = new JLabel("dd.mm.yy"));
		

		contentPane.add(buttonPanel);
		contentPane.add(display2);

		status = new JLabel(calc.getAuthor(), SwingConstants.RIGHT);
		status.setAlignmentX(Component.RIGHT_ALIGNMENT);
		contentPane.add(status);

		frame.pack();
	}

	/**
	 * Add a button to the button panel.
	 * 
	 * @param panel      The panel to receive the button.
	 * @param buttonText The text for the button.
	 */
	protected void addButton(Container panel, String buttonText) {
		JButton button = new JButton(buttonText);
		button.addActionListener(this);
		panel.add(button);
	}

	/**
	 * An interface action has been performed. Find out what it was and handle it.
	 * 
	 * @param event The event that has occured.
	 */
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		switch (command) {

		case "+":
			calc.applyOperator('+');
			break;
		case "-":
			calc.applyOperator('-');
			break;
		case "WEEKDAY":
			weekday.setText(calc.weekday() + ", ");
			break;
		case "AC":
			calc.clear();
			weekday.setText("Date: ");
			display2.setText("");
			break;
		case "?":
			showInfo();
			break;
		case "=":
			calc.output();
			display2.setText(calc.getDate().calculateJD().toString());
			break;
		default:
			calc.buttonPressed(command);
		}
		redisplay();
	}

	/**
	 * Update the interface display to show the current date of the calculator.
	 */
	protected void redisplay() {
		display.setText(calc.getDisplayString());
		currentDate.setText(calc.getDateString());
	}

	/**
	 * Toggle the info display in the calculator's status area between the author
	 * and version information.
	 */
	private void showInfo() {
		if (showingAuthor)
			status.setText(calc.getVersion());
		else
			status.setText(calc.getAuthor());

		showingAuthor = !showingAuthor;
	}
}