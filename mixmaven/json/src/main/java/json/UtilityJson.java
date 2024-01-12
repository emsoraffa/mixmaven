package json;

import core.MixMavenModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for handeling JSON seralization and deseralization.
 */
public class UtilityJson {

	/**
     * Writes the obj to file in a pretty format.
     * @param obj  The object to be saved to the file.
     * @param file The file to save the object to.
     */
	public static void saveObjectToJsonFile(Object obj, File file) {
		try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(obj, fileWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
     * Reads a MixMavenModel object from a JSON file.
     * @param file The file to read the object from.
     * @return A MixMavenModel object read from persistence.
     * @throws IOException If an I/O error occurs while reading the file.
     */
	public static MixMavenModel loadObjectFromJson(File file) {
		try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
			Gson gson = new Gson();

            return gson.fromJson(reader, MixMavenModel.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
