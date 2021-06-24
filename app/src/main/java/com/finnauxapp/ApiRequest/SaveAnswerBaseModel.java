package com.finnauxapp.ApiRequest;

import java.util.ArrayList;

public class SaveAnswerBaseModel {


    public ArrayList<SaveAnswerChildModel> getFIAnswer() {
        return FIAnswer;
    }

    public void setFIAnswer(ArrayList<SaveAnswerChildModel> FIAnswer) {
        this.FIAnswer = FIAnswer;
    }

    public ArrayList<SaveAnswerChildModel> FIAnswer;
}
