package com.example.madapp.quiz;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizQuestionFragment extends Fragment {

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


    public QuizQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizQuestionFragment newInstance(String param1, String param2) {
        QuizQuestionFragment fragment = new QuizQuestionFragment();
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
        return inflater.inflate(R.layout.fragment_quiz_question, container, false);
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
        int originalColor = Color.rgb(106, 27, 154);

        // reset button background to purple_800
        option1.setBackgroundColor(originalColor);
        option2.setBackgroundColor(originalColor);
        option3.setBackgroundColor(originalColor);
        option4.setBackgroundColor(originalColor);

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

            // change selected choice to bluish color
            clickedButton.setBackgroundColor(Color.rgb(64, 102, 224));
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
        // Inflate the custom layout
        View customDialogView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog_layout, null);

        // Find views in the custom layout
        ImageView CustomIV = customDialogView.findViewById(R.id.CustomIV);
        TextView customDialogTitle = customDialogView.findViewById(R.id.customDialogTitle);
        TextView customDialogMessage = customDialogView.findViewById(R.id.customDialogMessage);


        String passStatus = "";
        if(score > totalQuestion*0.70) {
            passStatus = "Congratulations! You Win";
            CustomIV.setImageResource(R.drawable.trophy);
        } else {
            passStatus = "You Lost! Try Again?";
            CustomIV.setImageResource(R.drawable.game_over);
        }

        // Set custom content
        customDialogTitle.setText(passStatus);
        customDialogMessage.setText("Your score is " + score + " out of " + totalQuestion);

        // Create the custom AlertDialog
        new AlertDialog.Builder(getContext())
                .setView(customDialogView)
                .setPositiveButton("Try Again", (dialogInterface, i) -> restartQuiz())
                .setNegativeButton("Back to Quiz", (dialogInterface, i) -> backToQuiz())
                .setCancelable(false)
                .show();

    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        selectedAnswer = "";
        loadNewQuestion();
    }

    void backToQuiz() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.DestQuiz);
    }
}