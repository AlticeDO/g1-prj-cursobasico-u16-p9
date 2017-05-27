package com.aneudylabgmail.magiceightball.domain;

import java.util.Random;

public class MagicEightBall {
    private static String[] possibleAnswers;
    private Random rng;

    public MagicEightBall() {
        rng = new Random();
        if (possibleAnswers == null) {
            possibleAnswers = retrieveAnswersMagicallyFromSomewhere();
        }
    }

    public String getAnswer() {
        int rnd = rng.nextInt(possibleAnswers.length-1);
        return possibleAnswers[rnd];
    }
    private String[] retrieveAnswersMagicallyFromSomewhere() {
        return possibleAnswers = new String[] {
                "En mi opinión, sí",
                "Es cierto",
                "Es decididamente así",
                "Probablemente",
                "Buen pronóstico",
                "Todo apunta a que sí",
                "Sin duda",
                "Sí",
                "Sí - definitivamente",
                "Debes confiar en ello",
                "Respuesta vaga, vuelve a intentarlo",
                "Pregunta en otro momento",
                "Será mejor que no te lo diga ahora",
                "Concéntrate y vuelve a preguntar",
                "No cuentes con ello",
                "Mi respuesta es no",
                "Mis fuentes me dicen que no",
                "Las perspectivas no son buenas",
                "Muy dudoso"
        };
    }
}
