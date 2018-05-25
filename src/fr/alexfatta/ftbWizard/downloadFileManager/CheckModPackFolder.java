package fr.alexfatta.ftbWizard.downloadFileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class CheckModPackFolder {

	private static String profileFileName = null;
	private final static String minecraftPath = "C:/Users/" + System.getProperty("user.name") + "/AppData/Roaming/.minecraft/";
	private static File fileProfile = new File(minecraftPath + "launcher_profiles.json");
	private final static String modpackFolderPath = minecraftPath + "Modpack/FeedTheWishS4/";

	public static void checkExistingFolder(JProgressBar progressBar) {
		try {
			try {
				setProfileFileName("launcher_profiles.json");
				setFileProfile(new File(minecraftPath + "launcher_profiles.json"));
				System.out.println("Fichier " + getProfileFileName() + " trouve.");		

			} catch (NullPointerException e) {
				System.out.println("Fichier launcher_profiles.json non trouve ! Impossible de continuer l'installation !");
				JOptionPane.showMessageDialog(null, "Fichier launcher_profiles.json non trouvé ! Impossible de continuer l'installation !\n"
						+ "Merci de vérifier que le .minecraft se situe bien dans " + minecraftPath);
				System.exit(0);
			}
			
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Creation du dossier Modpack...");
			
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Boolean modPackFolder = new File(minecraftPath + "Modpack/FeedTheWishS4").mkdirs();
			if (modPackFolder) {
				System.out.println("Dossiers Modpack et Mods cree avec succes dans .minecraft");
				
				Boolean modsFolder = new File(CheckModPackFolder.getModpackfolderpath() + "mods").mkdirs();
		        
		        if (modsFolder) {}

			} else {
				System.out.println("Impossible de creer le dossier Modpack ! Peut etre existe-t-il deja.");
			}
			
			progressBar.setValue(15);

			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			downloadBuild("hiddenLink", minecraftPath + "/Modpack/FeedTheWishS4/mods.zip", 2048);
			
			progressBar.setValue(30);

			System.out.println("Modpack telecharge avec succes dans " + minecraftPath + "/Modpack/FeedTheWishS4/");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void downloadBuild(String srcURL, String destPath, int bufferSize) throws FileNotFoundException, IOException {

		System.out.println("Debut du telechargement du modpack...");
		
		URL url = new URL(srcURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("GET");
		httpConn.connect();
		InputStream in = httpConn.getInputStream();
		FileOutputStream out = new FileOutputStream(minecraftPath + "/Modpack/FeedTheWishS4/mods.zip");
		
		out.getChannel().transferFrom(Channels.newChannel(new URL("hiddenLink").openStream()), 0, Long.MAX_VALUE);

		try {
			byte buffer[] = new byte[bufferSize];
			int c = 0;
			while ((c = in.read(buffer)) > 0) {
				out.write(buffer, 0, c);
			}
			out.flush();
			System.out.println("Le fichier du modpack a ete telecharge avec succes ici : " + minecraftPath + "Modpack/FeedTheWishS4/mods.zip");
		} catch (IOException e) {
			System.out.println("Impossible de télécharger le fichier :");
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void copyProfileFile(File fileToCopy) {

	}

	public static String getProfileFileName() {
		return profileFileName;
	}

	public static void setProfileFileName(String profileFileName) {
		CheckModPackFolder.profileFileName = profileFileName;
	}

	public static File getFileProfile() {
		return fileProfile;
	}

	public static void setFileProfile(File fileProfile) {
		CheckModPackFolder.fileProfile = fileProfile;
	}

	public static String getModpackfolderpath() {
		return modpackFolderPath;
	}
	
	public static String getMinecraftPath() {
		return minecraftPath;
	}

}
