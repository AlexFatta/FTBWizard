package fr.alexfatta.ftbWizard.editProfilFile;

import java.io.BufferedWriter;
import java.io.File;
import java.util.HashMap;

import argo.format.JsonFormatter;
import argo.format.PrettyJsonFormatter;
import argo.jdom.JdomParser;
import argo.jdom.JsonField;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeFactories;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import fr.alexfatta.ftbWizard.downloadFileManager.CheckModPackFolder;

public class EditProfilFile {

	private static final JsonFormatter JSON_FORMATTER = new PrettyJsonFormatter();


	/**
	 * determine if the profile exist and if it is valid in the file launcher_profiles.json
	 * 
	 * @param profiles
	 *            JsonRootNode of the file launcher_profiles.json
	 * @param modpackName
	 *            name of the modpack
	 * @param displayName
	 *            display name of the modpack
	 * @return true if the profile exist and is valid
	 */
	public static boolean isProfileValid(JsonRootNode profiles, String modpackName, String displayName)
	{
		if(profiles.isObjectNode("profiles", displayName))
		{
			if(profiles.isStringValue("profiles", displayName, "name") && profiles.getStringValue("profiles", displayName, "name").equals(displayName))
			{
				return profiles.getStringValue("profiles", displayName, "lastVersionId").equals(modpackName);
			}
		}
		return false;
	}

	public static void addToProfileList()
	{
		String modpackName = "FeedTheWishS4";
		String displayName = "FeedTheWishS4";
		File launcherProfiles = new File(CheckModPackFolder.getMinecraftPath(), "launcher_profiles.json");
		JdomParser parser = new JdomParser();
		JsonRootNode jsonProfileData = null;
		try
		{
			jsonProfileData = parser.parse(Files.newReader(launcherProfiles, Charsets.UTF_8));
		}
		catch(InvalidSyntaxException e)
		{
			System.out.println(e);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		if(!isProfileValid(jsonProfileData, modpackName, displayName))
		{
			JsonField[] fields = new JsonField[6];
			fields[0] = JsonNodeFactories.field("name", JsonNodeFactories.string(displayName));
			fields[1] = JsonNodeFactories.field("lastVersionId", JsonNodeFactories.string("1.7.10-Forge10.13.4.1614-1.7.10"));
			fields[2] = JsonNodeFactories.field("type", JsonNodeFactories.string("custom"));
			fields[3] = JsonNodeFactories.field("gameDir", JsonNodeFactories.string(CheckModPackFolder.getModpackfolderpath().toString()));
			fields[4] = JsonNodeFactories.field("icon", JsonNodeFactories.string("Redstone_Block"));
			fields[5] = JsonNodeFactories.field("javaArgs", JsonNodeFactories.string("-Xmx4096M -Xms4096M -Xmn768m -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+UseNUMA -XX:+CMSParallelRemarkEnabled -XX:MaxTenuringThreshold=15 -XX:MaxGCPauseMillis=30 -XX:GCPauseIntervalMillis=150 -XX:+UseAdaptiveGCBoundary -XX:-UseGCOverheadLimit -XX:+UseBiasedLocking -XX:SurvivorRatio=8 -XX:TargetSurvivorRatio=90 -XX:MaxTenuringThreshold=15 -Dfml.ignorePatchDiscrepancies=true -Dfml.ignoreInvalidMinecraftCertificates=true -XX:+UseFastAccessorMethods -XX:+UseCompressedOops -XX:+OptimizeStringConcat -XX:+AggressiveOpts -XX:ReservedCodeCacheSize=2048m -XX:+UseCodeCacheFlushing -XX:SoftRefLRUPolicyMSPerMB=10000 -XX:ParallelGCThreads=10"));

			HashMap<JsonStringNode, JsonNode> profileCopy = Maps.newHashMap(jsonProfileData.getNode("profiles").getFields());
			HashMap<JsonStringNode, JsonNode> rootCopy = Maps.newHashMap(jsonProfileData.getFields());
			profileCopy.put(JsonNodeFactories.string(displayName), JsonNodeFactories.object(fields));
			JsonRootNode profileJsonCopy = JsonNodeFactories.object(profileCopy);

			rootCopy.put(JsonNodeFactories.string("profiles"), profileJsonCopy);

			jsonProfileData = JsonNodeFactories.object(rootCopy);

			try
			{
				BufferedWriter newWriter = Files.newWriter(launcherProfiles, Charsets.UTF_8);
				JSON_FORMATTER.format(jsonProfileData, newWriter);
				newWriter.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		System.out.println("Profil cree avec succes !");
	}

}
