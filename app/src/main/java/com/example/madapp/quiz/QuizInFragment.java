package com.example.madapp.quiz;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.madapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // Question variables
    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    TextView TVTotalQuestion;
    TextView TVQuestion;
    TextView TVQuestionNo;
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    Button BtnSubmitAns;

    private ScoreViewModel scoreViewModel;


    public QuizInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizInFragment newInstance(String param1, String param2) {
        QuizInFragment fragment = new QuizInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TVTotalQuestion = view.findViewById(R.id.TVTotalQuestion);
        TVQuestion = view.findViewById(R.id.TVQuestion);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        option4 = view.findViewById(R.id.option4);
        BtnSubmitAns = view.findViewById(R.id.BtnSubmitAns);
        TVQuestionNo = view.findViewById(R.id.TVQuestionNo);


        option1.setOnClickListener(this::onClick);
        option2.setOnClickListener(this::onClick);
        option3.setOnClickListener(this::onClick);
        option4.setOnClickListener(this::onClick);
        BtnSubmitAns.setOnClickListener(this::onClick);

        TVTotalQuestion.setText("Total Questions: " + totalQuestion);

        loadNewQuestion();
    }

    public void onClick(View view) {

        option1.setBackgroundColor(Color.BLACK);
        option2.setBackgroundColor(Color.BLACK);
        option3.setBackgroundColor(Color.BLACK);
        option4.setBackgroundColor(Color.BLACK);

        Button clickedButton = (Button) view;
        if(clickedButton.getId() == R.id.BtnSubmitAns) {
            // Score increases by 1 if selected ans is equal to correct ans
            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();
        }
        else {
            // choices button clicked
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.BLUE);
        }
    }

    void loadNewQuestion() {

        if(currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }
        TVQuestion.setText(QuestionAnswer.question[currentQuestionIndex]);
        option1.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        option2.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        option3.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        option4.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
        TVQuestionNo.setText("Question " + (currentQuestionIndex + 1) + " of " + totalQuestion);

    }

    void finishQuiz() {
        String passStatus = "";
        if(score > totalQuestion*0.60) {
            passStatus = "Passed";
        } else {
            passStatus = "Failed";
        }

        new AlertDialog.Builder(getContext())
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", ((dialogInterface, i) -> restartQuiz()))
                .setNegativeButton("Back to Quiz", (dialogInterface, i) -> backToQuiz())
                .setCancelable(false)
                .show();


        // Obtain the ViewModel from the activity scope
        scoreViewModel = new ViewModelProvider(requireActivity()).get(ScoreViewModel.class);

        // Assume you have the quiz score available
        int quizScore = score;

        // Set the quiz score in the ViewModel
        scoreViewModel.setQuizScore(quizScore);

    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    void backToQuiz() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.DestQuiz);
    }
}