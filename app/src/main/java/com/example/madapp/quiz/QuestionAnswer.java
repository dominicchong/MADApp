package com.example.madapp.quiz;

public class QuestionAnswer {

    public static String question[] = {
            "What is the word that describes replanting an area with trees?",
            "What is the best course of action in preserving the environment?"
    };

    public static String choices[][] = {
            {"Deforestation", "Forest", "Reforestation", "Planting"},
            {"Throwing rubbish on the road", "Building a house", "Partying", "Scatter seed on fertile field"}
    };

    public static String correctAnswers[] = {
            choices[0][2],
            choices[1][3]
    };
}
