package ca.uqac.keepitcool.quizz.scenario;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;

import ca.uqac.keepitcool.quizz.UserChoice;

public class ScenarioBuilder {
    public static Scenario buildScenario(int scenarioNumber) {
        Scenario scenario = new Scenario();
        Situation s = null;

        switch (scenarioNumber) {
            default:
                scenario.addSituation(1, Trigger.SUCCESS, "Ne pas oublier de composer le 911 (US/Canada) ou le 18 (France)")
                        .addSituation(2, Trigger.FAILURE, "Si l'incendie n'est pas maîtisé, le feu se propage vers le haut")
                        .addSituation(3, Trigger.FAILURE, "Les serviettes n'étant pas ignifugées (ou humides), elles ne pourront pas étouffer l'incendie et risquent même de prendre feu")
                        .addSituation(4, Trigger.FAILURE, "Lorsqu'une alarme incendie sonne, il est capital d'évacuer le bâtiment en raison de la propagation du feu et de la fumée");

                scenario.addSituation(5, Trigger.LONG, "Vous arrivez devant la porte des escaliers, vous vous trouvez actuellement au 1er étage")
                        .addChoiceToSituation(5, UserChoice.FIRST, "Monter", 2, FancyColor.RED, Icon.UP)
                        .addChoiceToSituation(5, UserChoice.SECOND, "Descendre", 1, FancyColor.GREEN, Icon.DOWN);

                scenario.addSituation(6, Trigger.LONG, "Le feu ne semble pas se propager rapidement mais de multiples meubles brûlent. Vous ne voyez pas de source d'eau à proximité mais apercevez une pile de serviettes pliées")
                        .addChoiceToSituation(6, UserChoice.FIRST, "Etouffer le feu", 3, FancyColor.RED, Icon.HAND)
                        .addChoiceToSituation(6, UserChoice.SECOND, "Se diriger vers la sortie", 5, FancyColor.GREEN, Icon.EXIT);

                scenario.addSituation(7, Trigger.MEDIUM, "La fumée obstrue votre vision mais vous arrivez à voir que personne ne se trouve dans la pièce")
                        .addChoiceToSituation(7, UserChoice.FIRST, "Evaluer l'incendie", 6, FancyColor.RED, Icon.EYE)
                        .addChoiceToSituation(7, UserChoice.SECOND, "Se diriger vers la sortie", 5, FancyColor.GREEN, Icon.EXIT);

                scenario.addSituation(8, Trigger.SHORT, "Vous voyez de la fumée s'échapper sous une porte")
                        .addChoiceToSituation(8, UserChoice.FIRST, "Ouvrir la porte", 7, FancyColor.RED, Icon.ENTER)
                        .addChoiceToSituation(8, UserChoice.SECOND, "Se diriger vers la sortie", 5, FancyColor.GREEN, Icon.EXIT);

                scenario.addStartingSituation(9, Trigger.SHORT, "Vous êtes réveillé par une alarme incendie au milieu de la nuit")
                        .addChoiceToSituation(9, UserChoice.FIRST, "Se rendormir", 4, FancyColor.RED, Icon.SLEEP)
                        .addChoiceToSituation(9, UserChoice.SECOND, "Se lever", 5, FancyColor.GREEN, Icon.STANDUP);

                break;
        }

        return scenario;
    }
}
