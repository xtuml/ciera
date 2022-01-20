package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import io.ciera.runtime.application.DefaultLogger;
import io.ciera.runtime.domain.JSONMessage;
import tracking.shared.Indicator;
import tracking.shared.Unit;
import ui.shared.IUI;

public class SwingWatchGui extends JFrame implements WatchGui {

	public static final long serialVersionUID = 0;

	public static final int LARGE_Y = 355;
	public static final int SMALL_Y = 295;
	public static final int INDICATOR_Y = SMALL_Y + ((LARGE_Y - SMALL_Y) / 2);

	public static final String[] UNIT_LABELS = new String[] { "km", "meters", "min/km", "km/h", "miles", "yds", "ft",
			"min/mile", "mph", "bpm", "laps" };

	private Gui app;
	private JPanel holdAll = new JPanel();

	protected ImageIcon watch;
	protected ImageIcon watchIcon;
	protected ImageIcon lightHover;
	protected ImageIcon powerPressed;
	protected ImageIcon displayHover;
	protected ImageIcon displayPressed;
	protected ImageIcon modeHover;
	protected ImageIcon modePressed;
	protected ImageIcon lapResetHover;
	protected ImageIcon lapResetPressed;
	protected ImageIcon startStopHover;
	protected ImageIcon startStopPressed;
	protected ImageIcon smallSeparator;
	protected ImageIcon largeDots;
	protected ImageIcon upArrow;
	protected ImageIcon downArrow;
	protected ImageIcon flat;
	protected ImageIcon blank;
	protected ImageIcon smallDigit[] = new ImageIcon[10];
	protected ImageIcon largeDigit[] = new ImageIcon[10];

	private JLabel watchLabel = new JLabel();
	private JLabel lightLabel = new JLabel();
	private JLabel displayLabel = new JLabel();
	private JLabel modeLabel = new JLabel();
	private JLabel lapResetLabel = new JLabel();
	private JLabel startStopLabel = new JLabel();
	private JLabel smallSeparatorLabel = new JLabel();
	private JLabel largeDotsLabel = new JLabel();
	private JLabel unitsLabel = new JLabel();
	private JLabel indicatorLabel = new JLabel();

	private JLabel[] smallDigitLabel = new JLabel[4];
	private JLabel[] largeDigitLabel = new JLabel[4];

	protected ImageIcon createStandaloneImageIcon(String path) {
		URL imgURL = ClassLoader.getSystemResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			app.getLogger().error("Couldn't find file: " + path);
			return null;
		}
	}

	protected void createImageIcons() {
		watch = createStandaloneImageIcon("gui/img/watch.png");
		watchIcon = createStandaloneImageIcon("gui/img/app_icon.gif");
		lightHover = createStandaloneImageIcon("gui/img/light_hover.png");
		powerPressed = createStandaloneImageIcon("gui/img/light_pressed.png");
		displayHover = createStandaloneImageIcon("gui/img/display_hover.png");
		displayPressed = createStandaloneImageIcon("gui/img/display_pressed.png");
		modeHover = createStandaloneImageIcon("gui/img/mode_hover.png");
		modePressed = createStandaloneImageIcon("gui/img/mode_pressed.png");
		lapResetHover = createStandaloneImageIcon("gui/img/lap_reset_hover.png");
		lapResetPressed = createStandaloneImageIcon("gui/img/lap_reset_pressed.png");
		startStopHover = createStandaloneImageIcon("gui/img/start_stop_hover.png");
		startStopPressed = createStandaloneImageIcon("gui/img/start_stop_pressed.png");
		smallSeparator = createStandaloneImageIcon("gui/img/dots_small.png");
		largeDots = createStandaloneImageIcon("gui/img/dots_large.png");
		upArrow = createStandaloneImageIcon("gui/img/up.png");
		downArrow = createStandaloneImageIcon("gui/img/down.png");
		blank = createStandaloneImageIcon("gui/img/blank.png");
		flat = createStandaloneImageIcon("gui/img/flat.png");
		for (int i = 0; i < largeDigit.length; i++) {
			largeDigit[i] = createStandaloneImageIcon("gui/img/" + i + "_large.png");
			smallDigit[i] = createStandaloneImageIcon("gui/img/" + i + "_small.png");
		}
	}

	public SwingWatchGui(Gui gui) {
		// set signal handler
		app = gui;

		// Create logger
		app.setLogger(new DefaultLogger("GPSWatch GUI"));

		// load images that make up the GUI
		createImageIcons();

		// container holding all button/display images
		JLayeredPane pane = new JLayeredPane();

		// background image
		watchLabel.setIcon(watch);
		watchLabel.setBounds(0, 0, 487, 756);

		lightLabel.setBounds(0, 190, 75, 122);
		lightLabel.addMouseListener(new ButtonListener(lightHover, powerPressed) {
			@Override
			public void buttonPressed() {
				SwingWatchGui.this.sendSignal(new JSONMessage(new IUI.SetTargetPressed()));
			}
		});

		startStopLabel.setBounds(165, 499, 164, 70);
		startStopLabel.addMouseListener(new ButtonListener(startStopHover, startStopPressed) {
			@Override
			public void buttonPressed() {
				SwingWatchGui.this.sendSignal(new JSONMessage(new IUI.StartStopPressed()));
			}
		});

		lapResetLabel.setBounds(420, 434, 83, 121);
		lapResetLabel.addMouseListener(new ButtonListener(lapResetHover, lapResetPressed) {
			@Override
			public void buttonPressed() {
				SwingWatchGui.this.sendSignal(new JSONMessage(new IUI.LapResetPressed()));
			}
		});

		displayLabel.setBounds(412, 190, 75, 122);
		displayLabel.addMouseListener(new ButtonListener(displayHover, displayPressed) {
			@Override
			public void buttonPressed() {
				SwingWatchGui.this.sendSignal(new JSONMessage(new IUI.LightPressed()));
			}
		});

		modeLabel.setBounds(0, 434, 81, 121);
		modeLabel.addMouseListener(new ButtonListener(modeHover, modePressed) {
			@Override
			public void buttonPressed() {
				SwingWatchGui.this.sendSignal(new JSONMessage(new IUI.ModePressed()));
			}
		});

		// configure and position display images
		smallSeparatorLabel.setBounds(210, SMALL_Y + 15, 8, 21);
		smallSeparatorLabel.setIcon(smallSeparator);

		largeDotsLabel.setBounds(242, LARGE_Y + 28, 13, 35);
		largeDotsLabel.setIcon(largeDots);

		indicatorLabel.setBounds(120, INDICATOR_Y, 26, 51);
		indicatorLabel.setIcon(blank);

		unitsLabel.setText("");
		unitsLabel.setBounds(275, SMALL_Y + 28, 100, 25);
		unitsLabel.setForeground(Color.DARK_GRAY);
		unitsLabel.setFont(new Font("verdana", 0, 25));

		// add button/display images to a layer where they are visible
		pane.add(watchLabel, JLayeredPane.PALETTE_LAYER);
		pane.add(lightLabel, JLayeredPane.POPUP_LAYER);
		pane.add(displayLabel, JLayeredPane.POPUP_LAYER);
		pane.add(modeLabel, JLayeredPane.POPUP_LAYER);
		pane.add(lapResetLabel, JLayeredPane.POPUP_LAYER);
		pane.add(startStopLabel, JLayeredPane.POPUP_LAYER);
		pane.add(smallSeparatorLabel, JLayeredPane.POPUP_LAYER);
		pane.add(largeDotsLabel, JLayeredPane.POPUP_LAYER);
		pane.add(unitsLabel, JLayeredPane.POPUP_LAYER);
		pane.add(indicatorLabel, JLayeredPane.POPUP_LAYER);

		for (int i = 0; i < largeDigitLabel.length; i++) {
			smallDigitLabel[i] = new JLabel();
			setSmallDigit(i, 0);
			smallDigitLabel[i].setBounds(160 + i * 26 + (i > 1 ? 5 : 0), SMALL_Y, 26, 51);
			pane.add(smallDigitLabel[i], JLayeredPane.POPUP_LAYER);
			largeDigitLabel[i] = new JLabel();
			setLargeDigit(i, 0);
			largeDigitLabel[i].setBounds(150 + i * 49 + (i > 1 ? 8 : 0), LARGE_Y, 43, 94);
			pane.add(largeDigitLabel[i], JLayeredPane.POPUP_LAYER);
		}

		holdAll.setLayout(new BorderLayout());
		holdAll.add(pane, BorderLayout.CENTER);

		getContentPane().add(pane, BorderLayout.CENTER);

		setLocation(10, 10);
		setSize(496, 790);
		setAlwaysOnTop(true);
		setIconImage(watchIcon.getImage());
		setTitle("Gps Watch");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void setSmallDigit(int index, int value) {
		smallDigitLabel[index].setIcon(smallDigit[value]);
	}

	private void setLargeDigit(int index, int value) {
		largeDigitLabel[index].setIcon(largeDigit[value]);
	}

	private void setUnit(String unit) {
		unitsLabel.setText(unit);
	}

	private void showSeparator(boolean show) {
		if (show) {
			smallSeparatorLabel.setIcon(smallSeparator);
		} else {
			smallSeparatorLabel.setIcon(null);
		}
	}

	@Override
	public void setTime(int time) {
		int min = (time % 3600) / 60;
		int sec = time % 60;
		setLargeDigit(0, min / 10);
		setLargeDigit(1, min % 10);
		setLargeDigit(2, sec / 10);
		setLargeDigit(3, sec % 10);
	}

	private void setFloatingPointValue(double value) {
		setSmallDigit(0, (int) (value / 10f) % 10);
		setSmallDigit(1, (int) (value) % 10);
		setSmallDigit(2, (int) (value * 10f) % 10);
		setSmallDigit(3, (int) (value * 100f) % 10);
	}

	private void setDiscreteValue(double value) {
		setSmallDigit(0, (int) (value / 1000f) % 10);
		setSmallDigit(1, (int) (value / 100f) % 10);
		setSmallDigit(2, (int) (value / 10f) % 10);
		setSmallDigit(3, (int) (value) % 10);
	}

	private void setTimex(double value) {
		int min = (int) value % 60; // this will truncate the hour value
		int sec = (int) (60 * value) % 60;
		setSmallDigit(0, min / 10);
		setSmallDigit(1, min % 10);
		setSmallDigit(2, sec / 10);
		setSmallDigit(3, sec % 10);
	}

	@Override
	public void setData(double value, Unit unit) {
		switch (unit) {

		case KM:
		case MILES:
		case KMPERHOUR:
		case MPH:
			setFloatingPointValue(value);
			showSeparator(true);
			break;

		case METERS:
		case YARDS:
		case FEET:
		case BPM:
		case LAPS:
			setDiscreteValue(value);
			showSeparator(false);
			break;

		case MINPERKM:
		case MINPERMILE:
			setTimex(value);
			showSeparator(true);
			break;
		default:
			break;
		}
		setUnit(UNIT_LABELS[unit.ordinal()]);
	}

	@Override
	public void setIndicator(Indicator value) {
		switch (value) {
		case DOWN:
			indicatorLabel.setIcon(downArrow);
			break;
		case FLAT:
			indicatorLabel.setIcon(flat);
			break;
		case UP:
			indicatorLabel.setIcon(upArrow);
			break;
		case BLANK:
			indicatorLabel.setIcon(blank);
		default:
			break;
		}
	}

	/**
	 * A generic button listener that will switch images when buttons are
	 * hovered/pressed/released
	 */
	public abstract class ButtonListener implements MouseListener {
		private ImageIcon hover;
		private ImageIcon pressed;
		private ImageIcon cached;
		private boolean inside = false;

		public ButtonListener(ImageIcon hover, ImageIcon pressed) {
			this.hover = hover;
			this.pressed = pressed;
			cached = hover;
		}

		@Override
		public void mouseExited(MouseEvent me) {
			((JLabel) me.getSource()).setIcon(null);
			inside = false;
		}

		@Override
		public void mousePressed(MouseEvent me) {
			cached = pressed;
			((JLabel) me.getSource()).setIcon(pressed);
		}

		@Override
		public void mouseEntered(MouseEvent me) {
			((JLabel) me.getSource()).setIcon(cached);
			inside = true;
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			cached = hover;
			if (inside) {
				((JLabel) me.getSource()).setIcon(cached);
				buttonPressed();
			} else {
				((JLabel) me.getSource()).setIcon(null);
			}
		}

		@Override
		public void mouseClicked(MouseEvent me) {
		}

		public abstract void buttonPressed();
	}

	private void sendSignal(JSONMessage message) {
		app.sendSignal(message);
	}

	@Override
	public void display() {
		setVisible(true);
	}
}
