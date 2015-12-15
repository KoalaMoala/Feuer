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

    public static Scenario buildDemoScenario() {
        Scenario scenario = new Scenario();

        scenario.addSituation(1, Trigger.SUCCESS, "Ne pas oublier de composer le 911 (US/Canada) ou le 18 (France)")
                .addSituation(2, Trigger.FAILURE, "Si l'incendie n'est pas maîtisé, le feu se propage vers le haut")
                .addSituation(3, Trigger.FAILURE, "Les serviettes n'étant pas ignifugées (ou humides), elles ne pourront pas étouffer l'incendie et risquent même de prendre feu")
                .addSituation(4, Trigger.FAILURE, "Lorsqu'une alarme incendie sonne, il est capital d'évacuer le bâtiment en raison de la propagation du feu et de la fumée");

        scenario.addSituation(5, Trigger.MEDIUM, "Vous arrivez devant la porte des escaliers, vous vous trouvez actuellement au 1er étage")
                .addChoiceToSituation(5, UserDecision.FIRST, "Monter", 2, Icon.UP)
                .addChoiceToSituation(5, UserDecision.SECOND, "Descendre", 1, Icon.DOWN);

        scenario.addSituation(6, Trigger.LONG, "Le feu ne semble pas se propager rapidement mais de multiples meubles brûlent. Vous ne voyez pas de source d'eau à proximité mais apercevez une pile de serviettes pliées")
                .addChoiceToSituation(6, UserDecision.FIRST, "Etouffer le feu", 3, Icon.HAND)
                .addChoiceToSituation(6, UserDecision.SECOND, "Se diriger vers la sortie", 5, Icon.EXIT);

        scenario.addSituation(7, Trigger.MEDIUM, "La fumée obstrue votre vision mais vous arrivez à voir que personne ne se trouve dans la pièce")
                .addChoiceToSituation(7, UserDecision.FIRST, "Evaluer l'incendie", 6, Icon.EYE)
                .addChoiceToSituation(7, UserDecision.SECOND, "Se diriger vers la sortie", 5, Icon.EXIT);

        scenario.addSituation(8, Trigger.SHORT, "Vous progressez dans l'étage et au détour d'un couloir, vous voyez de la fumée s'échapper de sous une porte")
                .addChoiceToSituation(8, UserDecision.FIRST, "Ouvrir la porte", 7, Icon.ENTER)
                .addChoiceToSituation(8, UserDecision.SECOND, "Se diriger vers la sortie", 5, Icon.EXIT);

        scenario.addSituation(9, Trigger.MEDIUM, "Vous êtes dans le couloir et ne voyez rien d'alarmant")
                .addChoiceToSituation(9, UserDecision.FIRST, "Investiger", 8, Icon.EYE)
                .addChoiceToSituation(9, UserDecision.SECOND, "Se diriger vers la sortie", 5, Icon.EXIT);

        scenario.addStartingSituation(10, Trigger.SHORT, "Vous êtes réveillé par une alarme incendie au milieu de la nuit")
                .addChoiceToSituation(10, UserDecision.FIRST, "Se rendormir", 4, Icon.SLEEP)
                .addChoiceToSituation(10, UserDecision.SECOND, "Se lever", 9, Icon.STANDUP);

        return scenario;
    }

    public static Scenario buildFromFile(String asset, AssetManager assetManager) {
        Scenario scenario = new Scenario();

        try(JsonReader reader = new JsonReader(new InputStreamReader(assetManager.open(asset)))) {
            JsonParser parser = new JsonParser();

            JsonObject level = parser.parse(reader).getAsJsonObject();
            JsonArray array = level.getAsJsonArray("scenario");

            for(JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                Situation s = readSituation(obj);
                JsonElement start = obj.get("start");
                int id = obj.get("id").getAsInt();
                if(null != start && start.getAsBoolean()) {
                    scenario.addStartingSituation(id, s);
                } else {
                    scenario.addSituation(id, s);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scenario;
    }

    private static Situation readSituation(JsonObject elem) {
        String text = elem.get("text").getAsString();
        Trigger trigger = Trigger.valueOf(elem.get("trigger").getAsString());
        Situation s = new Situation(trigger, text);

        if(Trigger.FAILURE != trigger && Trigger.SUCCESS != trigger) {
            s = readChoices(elem, s);
        }

        return s;
    }

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
