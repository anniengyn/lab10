public class Calculator {
	private CalcEngine engine;
	private UserInterface gui;

	/**
	 * Create a new calculator and show it.
	 */
	public Calculator() {
		engine = new CalcEngine();
		gui = new UserInterface(engine);
	}

	public static void main(String[] args) {
		Calculator c = new Calculator();
	}

	/**
	 * In case the window was closed, show it again.
	 */
	public void show() {
		gui.setVisible(true);

	}
}
