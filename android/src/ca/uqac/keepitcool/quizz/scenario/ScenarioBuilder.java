package ca.uqac.keepitcool.quizz.scenario;

import android.content.res.AssetManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.uqac.keepitcool.quizz.utils.FancyColor;
import ca.uqac.keepitcool.quizz.utils.Icon;
import ca.uqac.keepitcool.quizz.utils.Trigger;
import ca.uqac.keepitcool.quizz.utils.UserDecision;

public class ScenarioBuilder {

    private final static JsonParser parser = new JsonParser();

    /**
     * Method allowing to read specific level name and description from a well-formed JSON file
     * @param asset File to load
     * @param assetManager Asset manager used to load the asset
     * @return Description containing level name and description
     */
    public static Description readDescriptionFromFile(String asset, AssetManager assetManager) {
        Description currentLevel = null;

        try(JsonReader reader = new JsonReader(new InputStreamReader(assetManager.open(asset)))) {
            JsonObject json = parser.parse(reader).getAsJsonObject();

            //Read level name and description
            String name = json.getAsJsonObject("description").get("name").getAsString();
            String description = json.getAsJsonObject("description").get("shortDescription").getAsString();
            currentLevel = new Description(name, description);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return currentLevel;
    }

    /**
     * Method allowing to load scenario from a well-formed JSON file
     * @param asset File to load
     * @param assetManager Asset manager used to load the asset
     * @return Complete scenario
     */
    public static Scenario buildFromFile(String asset, AssetManager assetManager) {
        Scenario scenario = new Scenario();

        try(JsonReader reader = new JsonReader(new InputStreamReader(assetManager.open(asset)))) {

            JsonObject level = parser.parse(reader).getAsJsonObject();
            JsonArray array = level.getAsJsonArray("scenario");

            for(JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                Situation s = readSituation(obj);
                int id = obj.get("id").getAsInt();

                // Check if the current situation is the starting point (boolean)
                JsonElement start = obj.get("start");

                // If the current situation is the starting point add it as the starting situation
                // Otherwise, treat it as a standard situation
                if(null != start && start.getAsBoolean()) {
                    scenario.addStartingSituation(id, s);
                } else {
                    scenario.addSituation(id, s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scenario;
    }

    /**
     * Create situation from current JSON element
     * @param elem current JSON object
     * @return Situation with specific type and choices if there's any
     */
    private static Situation readSituation(JsonObject elem) {
        String text = elem.get("text").getAsString();
        Trigger trigger = Trigger.valueOf(elem.get("trigger").getAsString());
        Situation s = new Situation(trigger, text);

        // If the situation is neither a SUCCESS or a FAILURE, then read available choices
        if(Trigger.FAILURE != trigger && Trigger.SUCCESS != trigger) {
            s = readChoices(elem, s);
        }

        return s;
    }

    /**
     * Read choices (range from 1 to 4)
     * @param elem current JSON object
     * @param s current situation
     * @return Situation with all available choices
     */
    private static Situation readChoices(JsonObject elem, Situation s) {
        String text = null;
        Integer followUp = null;
        FancyColor color = null;
        Icon icon = null;

        if(elem.has("firstChoice")) {
            JsonObject firstChoice = elem.get("firstChoice").getAsJsonObject();
            text = firstChoice.get("text").getAsString();
            followUp = firstChoice.get("followUp").getAsInt();
            icon = Icon.valueOf(firstChoice.get("icon").getAsString());
            s.addChoice(UserDecision.FIRST, text, followUp, icon);
        }

        if(elem.has("secondChoice")) {
            JsonObject secondChoice = elem.getAsJsonObject("secondChoice");
            text = secondChoice.get("text").getAsString();
            followUp = secondChoice.get("followUp").getAsInt();
            icon = Icon.valueOf(secondChoice.get("icon").getAsString());
            s.addChoice(UserDecision.SECOND, text, followUp, icon);
        }

        if(elem.has("thirdChoice")) {
            JsonObject thirdChoice = elem.getAsJsonObject("thirdChoice");
            text = thirdChoice.get("text").getAsString();
            followUp = thirdChoice.get("followUp").getAsInt();
            icon = Icon.valueOf(thirdChoice.get("icon").getAsString());
            s.addChoice(UserDecision.THIRD, text, followUp, icon);
        }

        if(elem.has("fourthChoice")) {
            JsonObject fourthChoice = elem.getAsJsonObject("fourthChoice");
            text = fourthChoice.get("text").getAsString();
            followUp = fourthChoice.get("followUp").getAsInt();
            icon = Icon.valueOf(fourthChoice.get("icon").getAsString());
            s.addChoice(UserDecision.FOURTH, text, followUp, icon);
        }

        return s;
    }
}
