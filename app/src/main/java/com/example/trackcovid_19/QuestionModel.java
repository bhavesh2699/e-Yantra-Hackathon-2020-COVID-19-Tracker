package com.example.trackcovid_19;

import java.util.List;

public class QuestionModel {
    private String question;
    private List<String> answers;
    private List<Integer> answersPoints;
    private String userAns;

    public QuestionModel() {
    }

    public QuestionModel(String question, List<String> answers, List<Integer> answersPoints) {
        this.question = question;
        this.answers = answers;
        this.answersPoints = answersPoints;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getUserAns() {
        return userAns;
    }

    public void setUserAns(String userAns) {
        this.userAns = userAns;
    }

    public List<Integer> getAnswersPoints() {
        return answersPoints;
    }
}
