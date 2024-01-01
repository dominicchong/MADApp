package com.example.madapp.quiz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.example.madapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionQuizFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // Question variables
    private List<QuestionList> questionList = new ArrayList<>();
    int score = 0;
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



    public QuestionQuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionQuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionQuizFragment newInstance(String param1, String param2) {
        QuestionQuizFragment fragment = new QuestionQuizFragment();
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

        // get questions from Firebase Data
        DatabaseReference quizDBRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ecohelper-eea91-default-rtdb.firebaseio.com/quiz");

        // show dialog while questions are being fetched
        Context context;
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        quizDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // get all questions from firebase database of quiz
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    // getting question, option and ans data from firebase database
                    String getQuestion = dataSnapshot.child("question").getValue(String.class);
                    String getOption1 = dataSnapshot.child("option1").getValue(String.class);
                    String getOption2 = dataSnapshot.child("option2").getValue(String.class);
                    String getOption3 = dataSnapshot.child("option3").getValue(String.class);
                    String getOption4 = dataSnapshot.child("option4").getValue(String.class);
                    String getAnswer = dataSnapshot.child("answer").getValue(String.class);

                    // adding data into questionList
                    QuestionList questionsList = new QuestionList(getQuestion, getOption1, getOption2, getOption3, getOption4, getAnswer);
                    questionList.add(questionsList);

                }

                // hide dialog
                progressDialog.hide();

                loadNewQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        option1.setOnClickListener(this::onClick);
        option2.setOnClickListener(this::onClick);
        option3.setOnClickListener(this::onClick);
        option4.setOnClickListener(this::onClick);
        BtnSubmitAns.setOnClickListener(this::onClick);

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
            if(selectedAnswer.equals(questionList.get(currentQuestionIndex).getAnswer())) {
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
        if(currentQuestionIndex == questionList.size()) {
            finishQuiz();
            return;
        }

        // set current question to TextView along with options from questionList ArrayList
        TVQuestion.setText(questionList.get(currentQuestionIndex).getQuestion());
        option1.setText(questionList.get(currentQuestionIndex).getOption1());
        option2.setText(questionList.get(currentQuestionIndex).getOption2());
        option3.setText(questionList.get(currentQuestionIndex).getOption3());
        option4.setText(questionList.get(currentQuestionIndex).getOption4());
        TVQuestionNo.setText("Question " + (currentQuestionIndex + 1) + " of " + questionList.size());
        TVTotalQuestion.setText("Total Questions: " + questionList.size());

    }

    void finishQuiz() {
        // Inflate the custom layout
        View customDialogView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog_layout, null);

        // Find views in the custom layout
        ImageView CustomIV = customDialogView.findViewById(R.id.CustomIV);
        TextView customDialogTitle = customDialogView.findViewById(R.id.customDialogTitle);
        TextView customDialogMessage = customDialogView.findViewById(R.id.customDialogMessage);


        String passStatus = "";
        if(score > questionList.size()*0.70) {
            passStatus = "Congratulations! You Win";
            CustomIV.setImageResource(R.drawable.trophy);
        } else {
            passStatus = "You Lost! Try Again?";
            CustomIV.setImageResource(R.drawable.game_over);
        }

        // Set custom content
        customDialogTitle.setText(passStatus);
        customDialogMessage.setText("Your score is " + score + " out of " + questionList.size());

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