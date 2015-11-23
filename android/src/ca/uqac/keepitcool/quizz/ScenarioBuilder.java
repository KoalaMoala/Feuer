package ca.uqac.keepitcool.quizz;

public class ScenarioBuilder {
    public static Scenario buildScenario(int scenarioNumber) {
        Scenario s = new Scenario();

        switch (scenarioNumber) {
            default:
                int ending01 = s.addSituation(Trigger.SUCCESS, "Ne pas oublier de composer le 911 (US/Canada) ou le 18 (France)");
                int ending02 = s.addSituation(Trigger.FAILURE, "Si l'incendie n'est pas maîtisé, le feu se propage vers le haut");
                int ending03 = s.addSituation(Trigger.FAILURE, "Les serviettes n'étant pas ignifugées (ou humides), elles ne pourront pas étouffer l'incendie et risquent même de prendre feu");
                int ending04 = s.addSituation(Trigger.FAILURE, "Lorsqu'une alarme incendie sonne, il est capital d'évacuer le bâtiment en raison de la propagation du feu et de la fumée");
                int choice01 = s.addSituation(15, "Vous arrivez devant la porte des escaliers, vous vous trouvez actuellement au 1er étage", "Monter", ending02, "Descendre", ending01);
                int choice02 = s.addSituation(30, "Le feu ne semble pas se propager rapidement mais de multiples meubles brulent. Vous ne voyez pas de source d'eau à proximité mais apercevez une pile de serviettes pliées", "Etouffer le feu", ending03, "Se diriger vers la sortie", choice01);
                int choice03 = s.addSituation(20, "La fumée obstrue votre vision mais vous arrivez à voir que personne ne se trouve dans la pièce", "Evaluer l'incendie", choice02, "Se diriger vers la sortie", choice01);
                int choice04 = s.addSituation(15, "Vous voyez de la fumée s'échapper sous une porte", "Ouvrir la porte", choice03, "Se diriger vers la sortie", choice01);
                s.addStartingSituation(15, "Vous êtes réveillé par une alarme incendie au milieu de la nuit", "Se rendormir", ending04, "Se lever", choice04);
                break;
        }

        return s;
    }
}
