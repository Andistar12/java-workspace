package com.saja.seca.error;

import javax.swing.*;

/**
 * @author Andy Nguyen
 *
 * Makes a popup window using JOptionFrame Useful for quick
 * notifications, such as game instructions, about/legal, etc Can also
 * report errors efficiently, and always remains on top of everything
 * Info, Warning, and Error popups can be made
 */

// TODO more
public class MessagePopup {

	public static final String default_error_title = "A fatal error has occured";

	public static void popupInfo(String title, String message) {
		createWindow(title, message, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void popupWarning(String title, String message) {
		createWindow(title, message, JOptionPane.WARNING_MESSAGE);
	}

	public static void popupError(String title, String message) {
		createWindow(title, message, JOptionPane.ERROR_MESSAGE);
	}

	private static void createWindow(String title, String message, int type) {
		JOptionPane optionPane = new JOptionPane(message, type);
		JDialog dialog = optionPane.createDialog(title);
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}

	public static void popupError(Exception e) {
		popupError(default_error_title, e.getMessage());
	}

	public static void popupError(Error e) {
		popupError(default_error_title, e.getMessage());
	}

}