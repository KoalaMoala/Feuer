package ca.uqac.keepitcool.quizz;

public class ScenarioBuilder {
    public static Scenario buildScenario(int scenarioNumber) {
        Scenario scenario = new Scenario();
        Situation s = null;

        switch (scenarioNumber) {
            default:
                int ending01 = scenario.addSituation(Trigger.SUCCESS, "Ne pas oublier de composer le 911 (US/Canada) ou le 18 (France)");
                int ending02 = scenario.addSituation(Trigger.FAILURE, "Si l'incendie n'est pas maîtisé, le feu se propage vers le haut");
                int ending03 = scenario.addSituation(Trigger.FAILURE, "Les serviettes n'étant pas ignifugées (ou humides), elles ne pourront pas étouffer l'incendie et risquent même de prendre feu");
                int ending04 = scenario.addSituation(Trigger.FAILURE, "Lorsqu'une alarme incendie sonne, il est capital d'évacuer le bâtiment en raison de la propagation du feu et de la fumée");

                s = new Situation(15, "Vous arrivez devant la porte des escaliers, vous vous trouvez actuellement au 1er étage")
                        .setFirstChoice("Monter", ending02, FancyColor.RED, Icon.UP)
                        .setSecondChoice("Descendre", ending01, FancyColor.GREEN, Icon.DOWN);
                int choice01 = scenario.addSituation(s);

                s = new Situation(30, "Le feu ne semble pas se propager rapidement mais de multiples meubles brulent. Vous ne voyez pas de source d'eau à proximité mais apercevez une pile de serviettes pliées")
                        .setFirstChoice("Etouffer le feu", ending03, FancyColor.RED, Icon.HAND)
                        .setSecondChoice("Se diriger vers la sortie", choice01, FancyColor.GREEN, Icon.EXIT);
                int choice02 = scenario.addSituation(s);

                s = new Situation(20, "La fumée obstrue votre vision mais vous arrivez à voir que personne ne se trouve dans la pièce")
                        .setFirstChoice("Evaluer l'incendie", choice02, FancyColor.RED, Icon.EYE)
                        .setSecondChoice("Se diriger vers la sortie", choice01, FancyColor.GREEN, Icon.EXIT);
                int choice03 = scenario.addSituation(s);

                s = new Situation(15, "Vous voyez de la fumée s'échapper sous une porte")
                        .setFirstChoice("Ouvrir la porte", choice03, FancyColor.RED, Icon.ENTER)
                        .setSecondChoice("Se diriger vers la sortie", choice01, FancyColor.GREEN, Icon.EXIT);
                int choice04 = scenario.addSituation(s);

                s = new Situation(15, "Vous êtes réveillé par une alarme incendie au milieu de la nuit")
                        .setFirstChoice("Se rendormir", ending04, FancyColor.RED, Icon.SLEEP)
                        .setSecondChoice("Se lever", choice04, FancyColor.GREEN, Icon.STANDUP);
                scenario.addStartingSituation(s);

                break;
        }

        return scenario;
    }
}
