package com.unittest.firebasereview;

import java.util.List;

public class Review {
    List<String> answers;


    public Review(){
    }

    public Review(List<String> answers) {
        this.answers = answers;

    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

}
