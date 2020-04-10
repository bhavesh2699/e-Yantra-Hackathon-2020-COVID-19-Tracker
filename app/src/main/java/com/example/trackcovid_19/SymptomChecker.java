package com.example.trackcovid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SymptomChecker extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout container,ansContainer,UserContainer,UserbtnContainer;
    private LinearLayout mainpage;
    private EditText tv_user_name;
    private TextView tvQuestion, therisk, tnc;
    private List<QuestionModel> questions=new ArrayList<>();
    private DatabaseReference mdatabase;
    private FirebaseUser user;
    private int currentIndex=-1;
    private int risk=0;
    private Button muserbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_checker);
        container = findViewById(R.id.container);
        ansContainer = findViewById(R.id.ansContainer);
        tvQuestion = findViewById(R.id.tv_question);
        mainpage = findViewById(R.id.mainpage);
        therisk = findViewById(R.id.therisk);
        tnc = findViewById(R.id.tnc);
        UserContainer = findViewById(R.id.UserContainer);
        tv_user_name = findViewById(R.id.tv_user_name);
        muserbtn = findViewById(R.id.muserbtn);
        container.setVisibility(View.INVISIBLE);
        ansContainer.setVisibility(View.INVISIBLE);
        UserbtnContainer = findViewById(R.id.UserbtnContainer);
        loadData();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("UserResponse");

        muserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tv_user_name.getText().toString();
                if (username.matches("")){
                    Toast.makeText(SymptomChecker.this, "You did not enter a username", Toast.LENGTH_SHORT).show();



                }else {
                    UserbtnContainer.removeAllViews();
                    UserContainer.removeAllViews();
                    container.setVisibility(View.VISIBLE);
                    ansContainer.setVisibility(View.VISIBLE);

                  /*  user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null){
                        String uid = user.getUid();

                    }*/
                }
            }
        });
    }
    private void loadData(){

        questions.add(new QuestionModel("What is your Gender?",Arrays.asList("Male","Female"),Arrays.asList(0,0)));
        questions.add(new QuestionModel("What is your age group ?",Arrays.asList("Less Than 12 Year","12-50 Years","50-60 Years","Above 60"),Arrays.asList(10,5,10,15)));
        questions.add(new QuestionModel("Do You have any health issues?",Arrays.asList("Asthma","Chronic lungs disease","Diabeties","Heart Disease","None of the above"),Arrays.asList(5,5,5,5,0)));
        questions.add(new QuestionModel("Have you or Someone in your family visited any of the below countries in the last 14 days",Arrays.asList("China","Italy","Span", "Iran", "Europe", "Middle East","South Africa","Country not listed about", "None of the above"),Arrays.asList(20,20,20,20,20,20,20,20,0)));
        questions.add(new QuestionModel("Have you or someone in your family travelled within India in India in public transport and came in Contact with someone with cold, cough or fever",Arrays.asList("YES","NO"),Arrays.asList(10,0)));
        questions.add(new QuestionModel("Have you or someone in your family come in close contact with a confirmed COVID-19 patient in the 14 days?",Arrays.asList("YES","NO"),Arrays.asList(20,0)));
        questions.add(new QuestionModel("Do you have Fever?",Arrays.asList("YES","NO"),Arrays.asList(10,0)));
        questions.add(new QuestionModel("Do you have Headache?",Arrays.asList("YES","NO"),Arrays.asList(10,0)));
        questions.add(new QuestionModel("Do you have Cough?",Arrays.asList("YES","NO"),Arrays.asList(10,0)));
        questions.add(new QuestionModel("Do you have Cold?",Arrays.asList("YES","NO"),Arrays.asList(10,0)));
        questions.add(new QuestionModel("Do you have Sore throat?",Arrays.asList("YES","NO"),Arrays.asList(10,0)));
        questions.add(new QuestionModel("Do you have fever?",Arrays.asList("YES","NO"),Arrays.asList(10,0)));
        questions.add(new QuestionModel("Do you have Shortness of Breath?",Arrays.asList("YES","NO"),Arrays.asList(10,0)));

        whatNext();
        //ek ek answer ko 10 point de de taki result bhi aa jae
        //sale do bar click kr rha h last wale pe
        //ab result wala krna h
        // har answer k alag answer honge
    }
    private void whatNext(){
        currentIndex+=1;
        if(currentIndex>=questions.size()){//show result
            calculateResult();
        }else{
            showQuestion(currentIndex);
        }
    }

    private void calculateResult() {
        for(QuestionModel question:questions){

        }
        if (risk>70 && risk<100){

            ansContainer.removeAllViews();
            container.removeAllViews();
            mainpage.setBackgroundColor(Color.YELLOW);
            therisk.setTextColor(Color.BLACK);
            therisk.setText("Medium Risk, Please get a health check up");
            tnc.setText("*This is based on current understanding of the disease which is subject to change.");
            user = FirebaseAuth.getInstance().getCurrentUser();
            String riskReport = "Medium Risk";

            if(user!=null){
                String uid = user.getUid();
                String nameofuser = tv_user_name.getText().toString();
                mdatabase.child(uid).child("risk").child("Medium risk").push().setValue(nameofuser);
                mdatabase.child(uid).child("risk").push().setValue(riskReport);            }

        } else if (risk>100){
            container.removeAllViews();
            ansContainer.removeAllViews();
            mainpage.setBackgroundColor(Color.RED);
            therisk.setTextColor(Color.WHITE);
            therisk.setText("High Risk, Please do consult the doctor and take their advice in case you are not feeling well.");
            tnc.setText("*This is based on current understanding of the disease which is subject to change.");
            user = FirebaseAuth.getInstance().getCurrentUser();
            String riskReport = "High Risk";
            if(user!=null){
                String uid = user.getUid();
                String nameofuser = tv_user_name.getText().toString();
                mdatabase.child(uid).child("risk").child("High risk").push().setValue(nameofuser);
                mdatabase.child(uid).child("risk").push().setValue(riskReport);            }

        }else if(risk<70) {
            ansContainer.removeAllViews();
            container.removeAllViews();
            mainpage.setBackgroundColor(Color.LTGRAY);
            therisk.setTextColor(Color.WHITE);
            therisk.setText("Low Risk");
            tnc.setText("*This is based on current understanding of the disease which is subject to change.");
            user = FirebaseAuth.getInstance().getCurrentUser();
            String riskReport = "Low Risk";
            if(user!=null){
                String uid = user.getUid();
                String nameofuser = tv_user_name.getText().toString();
                mdatabase.child(uid).child("risk").child("low risk").push().setValue(nameofuser);
                mdatabase.child(uid).child("risk").push().setValue(riskReport);            }
        }
      //  Toast.makeText(this, "Risk"+risk, Toast.LENGTH_SHORT).show();

    }

    private void showQuestion(int i) {
        QuestionModel ques = questions.get(i);
        List<String> answers = ques.getAnswers();
        tvQuestion.setText(ques.getQuestion());
        ansContainer.removeAllViews();
        for(int j=0;j<answers.size();j++){
            String answer =answers.get(j);
            TextView tvAnswer = new TextView(this);
            tvAnswer.setTextColor(Color.WHITE);
            tvAnswer.setText(answer);
            tvAnswer.setId(j);
            tvAnswer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            tvAnswer.setOnClickListener(this);
            ansContainer.addView(tvAnswer);
        }
    }



    @Override
    public void onClick(View v) {


        if (v instanceof TextView && currentIndex < questions.size()) {
            questions.get(currentIndex).setUserAns(((TextView) v).getText().toString());
            risk += questions.get(currentIndex).getAnswersPoints().get(v.getId());
            whatNext();

        }

    }
}
