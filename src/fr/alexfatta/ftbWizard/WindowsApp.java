package fr.alexfatta.ftbWizard;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import fr.alexfatta.ftbWizard.customOutPutStream.CustomOutputStream;
import fr.alexfatta.ftbWizard.downloadFileManager.CheckModPackFolder;
import fr.alexfatta.ftbWizard.editProfilFile.EditProfilFile;
import fr.alexfatta.ftbWizard.extractFiles.UnzipFileClass;

import javax.swing.JProgressBar;

public class WindowsApp {

	private JFrame frmModpackInstallationWizard;
	private PrintStream standardOut;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowsApp window = new WindowsApp();
					window.frmModpackInstallationWizard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WindowsApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmModpackInstallationWizard = new JFrame();
		frmModpackInstallationWizard.setResizable(false);
		frmModpackInstallationWizard.setIconImage(Toolkit.getDefaultToolkit().getImage(WindowsApp.class.getResource("/medias/LOGO_FTB_colored.png")));
		frmModpackInstallationWizard.setTitle("Modpack Installation Wizard (MIW)");
		frmModpackInstallationWizard.setBounds(100, 100, 791, 620);
		frmModpackInstallationWizard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmModpackInstallationWizard.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setBounds(173, 183, 559, 323);
		textArea.setEditable(false);
		frmModpackInstallationWizard.getContentPane().add(textArea);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(173, 517, 559, 44);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		frmModpackInstallationWizard.getContentPane().add(progressBar);
		
		/*
		 * Setting the new output
		 */
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		
		standardOut = System.out;
		
		System.setOut(printStream);
		System.setErr(printStream);
		
		JButton btnInstall = new JButton("Install (click once)");
		btnInstall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnInstall.setEnabled(false);
				
				CheckModPackFolder.checkExistingFolder(progressBar);
				
				try {
					UnzipFileClass.extract(new File(CheckModPackFolder.getModpackfolderpath() + "mods.zip"), new File(CheckModPackFolder.getModpackfolderpath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				progressBar.setValue(60);
					
				EditProfilFile.addToProfileList();
				
				progressBar.setValue(100);
				
			}
		});
		btnInstall.setBounds(10, 465, 153, 44);
		frmModpackInstallationWizard.getContentPane().add(btnInstall);
		
		JButton btnNewButton = new JButton("Quit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(10, 517, 153, 44);
		frmModpackInstallationWizard.getContentPane().add(btnNewButton);
		
		JLabel lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon(WindowsApp.class.getResource("/medias/LOGO_FTB_white.png")));
		lblImage.setBounds(-14, 11, 276, 153);
		frmModpackInstallationWizard.getContentPane().add(lblImage);
		
		JTextPane txtpnForgeVersion = new JTextPane();
		txtpnForgeVersion.setEditable(false);
		txtpnForgeVersion.setBackground(SystemColor.menu);
		txtpnForgeVersion.setText("Forge version : 1.7.10");
		txtpnForgeVersion.setBounds(309, 70, 136, 20);
		frmModpackInstallationWizard.getContentPane().add(txtpnForgeVersion);
		
		JTextPane txtpnMiwVersion = new JTextPane();
		txtpnMiwVersion.setEditable(false);
		txtpnMiwVersion.setText("MIW Version : 1.5");
		txtpnMiwVersion.setBackground(SystemColor.menu);
		txtpnMiwVersion.setBounds(309, 115, 136, 20);
		frmModpackInstallationWizard.getContentPane().add(txtpnMiwVersion);
		
		JTextPane txtpnMainOutputConsole = new JTextPane();
		txtpnMainOutputConsole.setEditable(false);
		txtpnMainOutputConsole.setBackground(SystemColor.menu);
		txtpnMainOutputConsole.setText("Main output console :");
		txtpnMainOutputConsole.setBounds(173, 161, 173, 20);
		frmModpackInstallationWizard.getContentPane().add(txtpnMainOutputConsole);
		
		JTextPane txtpnAlexfatta = new JTextPane();
		txtpnAlexfatta.setEditable(false);
		txtpnAlexfatta.setBackground(SystemColor.menu);
		txtpnAlexfatta.setText("\u00A9 AlexFatta - Copyrights 2018");
		txtpnAlexfatta.setBounds(582, 161, 162, 20);
		frmModpackInstallationWizard.getContentPane().add(txtpnAlexfatta);
		
		JTextPane txtpnFeedthewish = new JTextPane();
		txtpnFeedthewish.setEditable(false);
		txtpnFeedthewish.setBackground(SystemColor.menu);
		txtpnFeedthewish.setFont(new Font("Snap ITC", Font.PLAIN, 15));
		txtpnFeedthewish.setText("FeedTheWish - Saison IV");
		txtpnFeedthewish.setBounds(309, 11, 263, 31);
		frmModpackInstallationWizard.getContentPane().add(txtpnFeedthewish);
		
		JTextPane txtpnThanksToRobin = new JTextPane();
		txtpnThanksToRobin.setText("Thanks to robin4002 & ShE3py for the help.");
		txtpnThanksToRobin.setEditable(false);
		txtpnThanksToRobin.setBackground(SystemColor.menu);
		txtpnThanksToRobin.setBounds(480, 115, 263, 20);
		frmModpackInstallationWizard.getContentPane().add(txtpnThanksToRobin);
	}

	public PrintStream getStandardOut() {
		return standardOut;
	}

	public void setStandardOut(PrintStream standardOut) {
		this.standardOut = standardOut;
	}
}
