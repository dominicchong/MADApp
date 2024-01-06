package com.example.madapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madapp.quiz.QuestionList;
import com.example.madapp.quiz.QuizQuestionFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileEnhancedSurveyQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileEnhancedSurveyQuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // Question variables
    private List<QuestionList> questionList = new ArrayList<>();
    List<QuestionList> randomQuestionList;
    int score = 0;
    int currentQuestionIndex = 0;
    int maxTotalQuestion = 8;
    String selectedAnswer = "";
    ImageView IVBackToQuiz;
    TextView TVQuestion;
    TextView TVQuestionNo;
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    Button BtnSubmitAns;


    public ProfileEnhancedSurveyQuestionFragment() {
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
    public static ProfileEnhancedSurveyQuestionFragment newInstance(String param1, String param2) {
        ProfileEnhancedSurveyQuestionFragment fragment = new ProfileEnhancedSurveyQuestionFragment();
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
        return inflater.inflate(R.layout.fragment_profile_enhanced_survey_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TVQuestion = view.findViewById(R.id.TVQuestion);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        option4 = view.findViewById(R.id.option4);
        BtnSubmitAns = view.findViewById(R.id.BtnSubmitAns);
        TVQuestionNo = view.findViewById(R.id.TVQuestionNo);
        IVBackToQuiz = view.findViewById(R.id.IVBackToQuiz);

        IVBackToQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to go back to profile enhanced survey?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> backToProfileEnhancedSurvey())
                        .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                        .setCancelable(false)
                        .show();
            }
        });


        // get questions from Firebase Data
        DatabaseReference quizDBRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ecohelper-eea91-default-rtdb.firebaseio.com/survey");

        // show dialog while questions are being fetched
        Context context;
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();


        quizDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<QuestionList> questionList = new ArrayList<>();

                // Get all questions from the Firebase database of the quiz
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Getting question, options, and answer data from Firebase database
                    String getQuestion = dataSnapshot.child("question").getValue(String.class);
                    String getOption1 = dataSnapshot.child("option1").getValue(String.class);
                    String getOption2 = dataSnapshot.child("option2").getValue(String.class);
                    String getOption3 = dataSnapshot.child("option3").getValue(String.class);
                    String getOption4 = dataSnapshot.child("option4").getValue(String.class);
                    String getAnswer = dataSnapshot.child("answer").getValue(String.class);

                    // Adding data into questionList
                    QuestionList question = new QuestionList(getQuestion, getOption1, getOption2, getOption3, getOption4, getAnswer);
                    questionList.add(question);
                }

                // Shuffle order of questions so that each time user play, question will not always be the same
                shuffleQuestion(questionList);

                // Hide the progress dialog
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
        int originalColor = Color.rgb(0, 137, 123);

        // reset button background to teal_700
        option1.setBackgroundColor(originalColor);
        option2.setBackgroundColor(originalColor);
        option3.setBackgroundColor(originalColor);
        option4.setBackgroundColor(originalColor);

        Button clickedButton = (Button) view;
        if(clickedButton.getId() == R.id.BtnSubmitAns) {

            // if user does not select an answer, a toast message will appear
            if(selectedAnswer.equals("")) {
                Toast.makeText(view.getContext(), "You must select an answer!", Toast.LENGTH_SHORT).show();
            }
            else {
                // Score increases by 1 if selected ans is equal to correct ans
                if(selectedAnswer.equals(randomQuestionList.get(currentQuestionIndex).getAnswer())) {
                    score++;
                }
                currentQuestionIndex++;
                loadNewQuestion();
            }
        }
        else {
            // choices button clicked
            selectedAnswer = clickedButton.getText().toString();

            // change selected choice to purplish color
            clickedButton.setBackgroundColor(Color.rgb(20, 117, 252));
        }
    }

    void loadNewQuestion() {
        if(currentQuestionIndex == randomQuestionList.size()) {
            finishQuiz();
            return;
        }

        // set current question to TextView along with options
        TVQuestion.setText(randomQuestionList.get(currentQuestionIndex).getQuestion());
        option1.setText(randomQuestionList.get(currentQuestionIndex).getOption1());
        option2.setText(randomQuestionList.get(currentQuestionIndex).getOption2());
        option3.setText(randomQuestionList.get(currentQuestionIndex).getOption3());
        option4.setText(randomQuestionList.get(currentQuestionIndex).getOption4());
        TVQuestionNo.setText("Question " + (currentQuestionIndex + 1) + " of " + randomQuestionList.size());

        // reset selected answer to empty value
        selectedAnswer = "";

        // Update the text of the submit button
        updateSubmitButtonText();

    }

    void updateSubmitButtonText() {
        Button BtnSubmitAns = requireView().findViewById(R.id.BtnSubmitAns);
        if (currentQuestionIndex == randomQuestionList.size() - 1) {
            BtnSubmitAns.setText("Submit");
        } else {
            BtnSubmitAns.setText("Next");
        }
    }


    void finishQuiz() {
        // Inflate the custom layout
        View customDialogView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog_layout, null);

        // Find views in the custom layout
        ImageView CustomIV = customDialogView.findViewById(R.id.CustomIV);
        TextView customDialogTitle = customDialogView.findViewById(R.id.customDialogTitle);
        TextView customDialogMessage = customDialogView.findViewById(R.id.customDialogMessage);


        String passStatus = "Survey Done!";

        // Set custom content
        customDialogTitle.setText(passStatus);
        customDialogMessage.setText("Your score is " + score + " out of " + randomQuestionList.size());

        // Update the user's score in Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            // Create a new "score" field and set its value
            userRef.child("score").setValue(score);
        }


        // Create the custom AlertDialog
        new AlertDialog.Builder(getContext())
                .setView(customDialogView)
                .setPositiveButton("Back to Survey Page", (dialogInterface, i) -> backToProfileEnhancedSurvey())
                .setCancelable(false)
                .show();

    }

    void backToProfileEnhancedSurvey() {
        // reset score
        score = 0;
        currentQuestionIndex = 0;
        selectedAnswer = "";

        // Navigate back to the profile enhanced survey page
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.DestProfileEnhancedSurvey);

        // Hide the back button in the toolbar
        MainActivity activity = (MainActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    void shuffleQuestion(List<QuestionList> questionList) {
        // Shuffle the questionList to randomize the order
        Collections.shuffle(questionList);

        // Get the maximum number of questions to display
        int maxQuestions = Math.min(8, questionList.size());

        // Get the sublist of questions with the maximum number
        randomQuestionList = questionList.subList(0, maxQuestions);
    }
}