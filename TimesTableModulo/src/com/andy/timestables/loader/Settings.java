package com.andy.timestables.loader;

import com.saja.seca.error.MessagePopup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JSlider slider, slider_1;
	
	private JLabel lblFps, lblSpeed, lblCurrentIteration, lblChangeColor;
	
	private boolean quitFlag = false;
	private boolean resetFlag = false;
	private ButtonGroup bg;

	private JRadioButton rdbtnBlackBackground, rdbtnWhiteBlackground;
	
	public Settings(int pointCount) {
		setAlwaysOnTop(true);
		setTitle("Times Tables Modulo " + pointCount);
		setBounds(100, 100, 371, 527);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 335, 155);
		contentPane.add(panel);
		panel.setLayout(null);
		
		slider = new JSlider();
		slider.setBounds(10, 80, 212, 35);
		panel.add(slider);
		slider.setMinorTickSpacing(1);
		slider.setMinimum(-1000);
		slider.setMaximum(1000);
		slider.setPaintTicks(true);
		
		
		lblCurrentIteration = new JLabel("Current Multiplier: x");
		lblCurrentIteration.setBounds(10, 113, 315, 24);
		lblCurrentIteration.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		panel.add(lblCurrentIteration);
		
		lblSpeed = new JLabel("Current Speed: x1");
		lblSpeed.setBounds(10, 11, 315, 24);
		panel.add(lblSpeed);
		lblSpeed.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 429, 335, 48);
		contentPane.add(panel_1);
		
		JButton btnHelp = new JButton("Info");
		btnHelp.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		panel_1.add(btnHelp);
		btnHelp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MessagePopup.popupInfo("Times Tables Modulo Help", "Renders a modulus-based times table. The controls can be found in the separate window.\n"
						+ "\nCurrent speed - how much to increase the multiplier by each second\n"
						+ "Current multiplier - the current multiplier for the times table\n"
						+ "Color change speed - how fast the line color will change\n"
						+ "\nRight click: Opens the settings menu if not open yet\n"
						+ "Left click: Opens the settings menu and sets the current speed to 0");
			}
		});
		
		JButton btnAbout = new JButton("About");
		btnAbout.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		panel_1.add(btnAbout);
		btnAbout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MessagePopup.popupInfo("Times Tables Modulo About", "Times Table Modulo by Andy Nguyen\n"
						+ "Copyright 2016 by Andy Nguyen [MIT License]");
			}
		});
		
		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		panel_1.add(btnReset);
		btnReset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reset();
				resetFlag = true;
			}
		});
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		panel_1.add(btnQuit);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 336, 335, 82);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblPoints = new JLabel("Points: " + pointCount);
		lblPoints.setBounds(10, 11, 315, 27);
		panel_2.add(lblPoints);
		lblPoints.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		
		lblFps = new JLabel("FPS: 0");
		lblFps.setBounds(10, 43, 315, 27);
		panel_2.add(lblFps);
		lblFps.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		btnQuit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				quitFlag = true;
			}
		});
		
		bg = new ButtonGroup();
		
		JButton btnSpeedDown = new JButton("<<");
		btnSpeedDown.setBounds(10, 46, 100, 23);
		panel.add(btnSpeedDown);
		btnSpeedDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slider.setValue(slider.getValue() - 1);
			}
		});
		
		
		
		JButton btnSpeedUp = new JButton(">>");
		btnSpeedUp.setBounds(120, 46, 102, 23);
		panel.add(btnSpeedUp);
		btnSpeedUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slider.setValue(slider.getValue() + 1);
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 177, 335, 148);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		lblChangeColor = new JLabel("Color Change Speed: x");
		lblChangeColor.setBounds(10, 11, 319, 27);
		panel_3.add(lblChangeColor);
		lblChangeColor.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		
		slider_1 = new JSlider();
		slider_1.setBounds(10, 83, 212, 26);
		panel_3.add(slider_1);
		slider_1.setMinimum(0);
		slider_1.setMaximum(100);
		
		JButton btnColorUp = new JButton(">>");
		btnColorUp.setBounds(120, 49, 102, 23);
		panel_3.add(btnColorUp);
		
		JButton btnColorDown = new JButton("<<");
		btnColorDown.setBounds(10, 49, 100, 23);
		panel_3.add(btnColorDown);
		
		rdbtnBlackBackground = new JRadioButton("Black Background");
		rdbtnBlackBackground.setBounds(10, 116, 148, 23);
		panel_3.add(rdbtnBlackBackground);
		rdbtnBlackBackground.setSelected(true);
		rdbtnBlackBackground.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		bg.add(rdbtnBlackBackground);
		
		rdbtnWhiteBlackground = new JRadioButton("White Blackground");
		rdbtnWhiteBlackground.setBounds(177, 116, 152, 23);
		panel_3.add(rdbtnWhiteBlackground);
		rdbtnWhiteBlackground.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		bg.add(rdbtnWhiteBlackground);
		btnColorDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slider_1.setValue(slider_1.getValue() - 1);
			}
		});
		btnColorUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slider_1.setValue(slider_1.getValue() + 1);
			}
		});
		
		reset();
	}
	
	public boolean quitFlag() { return quitFlag; }
	public boolean resetFlag() { return resetFlag; }
	public void resetResetFlag() { resetFlag = false; }
	public float getMultiplier() { return slider.getValue() / 100f; }
	public float getColorSpeed() { return slider_1.getValue() / 200f; }
	
	public void reset() {
		slider.setValue(0);
		slider_1.setValue(14);
		update(0,0);
		rdbtnBlackBackground.setSelected(true);
		rdbtnWhiteBlackground.setSelected(false);
	}
	
	public void pause() {
		slider.setValue(0);
	}
	
	public boolean blackBG() {
		return rdbtnBlackBackground.isSelected();
	}
	
	public void update(float currentIteration, int fps) {
		lblFps.setText("FPS: " + fps);
		lblSpeed.setText("Current Speed: x" + getMultiplier());
		lblCurrentIteration.setText("Current Multiplier: x" + currentIteration);
		lblChangeColor.setText("Color Change Speed: x" + getColorSpeed() );
	}
}
