package gui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import io.ciera.runtime.application.DefaultLogger;
import io.ciera.runtime.domain.JSONMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import tracking.shared.Indicator;
import tracking.shared.Unit;
import ui.shared.IUI;

public class AsciiWatchGui implements WatchGui {

	private static final int[][] DIGIT_COORDS = { new int[] { 14, 6 }, new int[] { 17, 6 }, new int[] { 21, 6 },
			new int[] { 24, 6 } };

	private static final int DATA_X = 14;
	private static final int DATA_Y = 5;
	private static final int DATA_LEN = 13;

	private static final String[] UNIT_LABELS = new String[] { "km", "meters", "min/km", "km/h", "miles", "yds", "ft",
			"min/mile", "mph", "bpm", "laps" };

	private TextImage[] numerals;
	private TextImage watchFace;

	private Terminal terminal;
	private TextGraphics watchGraphics;

	private Gui app;

	public AsciiWatchGui(Gui gui) {
		// set to headless mode
		System.setProperty("java.awt.headless", "true");

		// set the gui connection
		app = gui;

		// load resources
		numerals = loadNumerals();
		watchFace = loadWatchFace();

		try {
			// create terminal
			terminal = new DefaultTerminalFactory().createTerminal();
			terminal.setCursorVisible(false);
			terminal.clearScreen();

			// draw watch graphics
			watchGraphics = terminal.newTextGraphics();
			watchGraphics.drawImage(TerminalPosition.TOP_LEFT_CORNER, watchFace);

			// set up logger and standard error
			OutputStream out = new TerminalOutputStream(terminal, 0, watchFace.getSize().getRows());
			System.setErr(new PrintStream(out));
			app.setLogger(new DefaultLogger("GPSWatch GUI", null, Level.INFO, out));

		} catch (IOException e) {
			/* do nothing */ }
	}

	@Override
	public void display() {
		setTime(0);
		setData(0, Unit.METERS);
		char command = ' ';
		while (command != 'x') {
			try {
				command = terminal.readInput().getCharacter();
				switch (command) {
				case 's':
					app.sendSignal(new JSONMessage(new IUI.StartStopPressed()));
					app.getLogger().trace("Sending start/stop");
					break;
				case 'r':
					app.sendSignal(new JSONMessage(new IUI.LapResetPressed()));
					app.getLogger().trace("Sending reset/lap");
					break;
				case 'm':
					app.sendSignal(new JSONMessage(new IUI.ModePressed()));
					app.getLogger().trace("Sending mode");
					break;
				}
			} catch (IOException e) {
				command = 'x';
			}
		}
		app.getLogger().trace("Exiting...");

		// clear screen and exit
		try {
			terminal.setCursorPosition(0, 0);
			terminal.setCursorVisible(true);
			terminal.clearScreen();
		} catch (IOException e) {
			/* do nothing */ }
		System.exit(0);
	}

	@Override
	public void setData(double value, Unit unit) {
		String valueString = "";
		String unitString = UNIT_LABELS[unit.ordinal()];
		switch (unit) {
		case KM:
		case MILES:
		case KMPERHOUR:
		case MPH:
			valueString = String.format("%.2f", value);
			break;
		case METERS:
		case YARDS:
		case FEET:
		case BPM:
		case LAPS:
			valueString = String.format("%d", (int) value);
			break;
		case MINPERKM:
		case MINPERMILE:
			valueString = String.format("%d:%02d", (int) value % 60, (int) (60 * value) % 60);
			break;
		default:
			break;
		}
		int padding = DATA_LEN - (valueString.length() + unitString.length());
		if (padding < 1) {
			padding = 1;
		}
		String data = valueString;
		for (int i = 0; i < padding; i++) {
			data += " ";
		}
		data += unitString;
		if (data.length() > DATA_LEN) {
			data = data.substring(0, DATA_LEN);
		}
		while (data.length() < DATA_LEN) {
			data += " ";
		}
		watchGraphics.putString(new TerminalPosition(DATA_X, DATA_Y), data);
	}

	@Override
	public void setTime(int time) {
		int min = (time % 3600) / 60;
		int sec = time % 60;
		watchGraphics.drawImage(new TerminalPosition(DIGIT_COORDS[0][0], DIGIT_COORDS[0][1]),
				numerals[(min / 10) % 10]);
		watchGraphics.drawImage(new TerminalPosition(DIGIT_COORDS[1][0], DIGIT_COORDS[1][1]), numerals[min % 10]);
		watchGraphics.drawImage(new TerminalPosition(DIGIT_COORDS[2][0], DIGIT_COORDS[2][1]),
				numerals[(sec / 10) % 10]);
		watchGraphics.drawImage(new TerminalPosition(DIGIT_COORDS[3][0], DIGIT_COORDS[3][1]), numerals[sec % 10]);
	}

	@Override
	public void setIndicator(Indicator value) {
		// not implemented
	}

	private TextImage[] loadNumerals() {
		List<TextImage> numerals = new ArrayList<>();
		List<String> lines = new ArrayList<>();
		;
		URL txtURL = ClassLoader.getSystemResource("gui/txt/numbers.txt");
		try {
			Scanner sc = new Scanner(txtURL.openStream());
			while (sc.hasNextLine()) {
				if (lines.size() >= 3) {
					numerals.add(fromLines(lines));
					lines = new ArrayList<>();
				}
				lines.add(sc.nextLine());
			}
			numerals.add(fromLines(lines));
			sc.close();
		} catch (IOException e) {
			/* do nothing */ }
		return numerals.toArray(new TextImage[0]);
	}

	private TextImage loadWatchFace() {
		List<String> lines = new ArrayList<>();
		URL txtURL = ClassLoader.getSystemResource("gui/txt/watchface.txt");
		try {
			Scanner sc = new Scanner(txtURL.openStream());
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}
			sc.close();
		} catch (IOException e) {
			/* do nothing */ }
		return fromLines(lines);
	}

	private TextImage fromLines(List<String> lines) {
		int numColumns = lines.stream().max((a, b) -> a.length() - b.length()).get().length();
		int numLines = lines.size();
		TextImage newImage = new BasicTextImage(numColumns, numLines);
		newImage.setAll(TextCharacter.fromCharacter(' ')[0]);
		for (int y = 0; y < lines.size(); y++) {
			for (int x = 0; x < lines.get(y).length(); x++) {
				newImage.setCharacterAt(x, y, TextCharacter.fromCharacter(lines.get(y).charAt(x))[0]);
			}
		}
		return newImage;
	}

	private class TerminalOutputStream extends OutputStream {

		private final int initCursorX;
		private int cursorX;
		private int cursorY;
		private Terminal terminal;
		private StringBuilder buffer;

		public TerminalOutputStream(Terminal t, int x, int y) {
			terminal = t;
			initCursorX = x;
			cursorX = x;
			cursorY = y;
			buffer = new StringBuilder();
		}

		@Override
		public void write(int b) {
			char c = (char) b;
			buffer.append(c);
			if (c == '\n') {
				try {
					flush();
				} catch (IOException e) {
					// do nothing
				}
			}
		}

		@Override
		public void flush() throws IOException {
			TextGraphics tg = terminal.newTextGraphics();
			tg.putCSIStyledString(cursorX, cursorY, buffer.toString());
			if (buffer.indexOf("\n") != -1) {
				cursorX = initCursorX;
				cursorY++;
			} else {
				cursorX += buffer.length();
			}
			buffer = new StringBuilder();
		}

	}

}
