package com.finnauxapp.ApiResponse;

public class AssetListResponse {
    public int getAssetId() {
        return AssetId;
    }

    public void setAssetId(int assetId) {
        AssetId = assetId;
    }

    private int AssetId;
    private String Question;
    private String Question_Hindi;
    private String QuestionType;
    private String QuestionOptions;

    public boolean isMandatory() {
        return IsMandatory;
    }

    public void setMandatory(boolean mandatory) {
        IsMandatory = mandatory;
    }

    private boolean IsMandatory;

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    private String Answer;


    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public String getQuestion_Hindi() {
        return Question_Hindi;
    }

    public void setQuestion_Hindi(String Question_Hindi) {
        this.Question_Hindi = Question_Hindi;
    }

    public String getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(String QuestionType) {
        this.QuestionType = QuestionType;
    }

    public String getQuestionOptions() {
        return QuestionOptions;
    }

    public void setQuestionOptions(String QuestionOptions) {
        this.QuestionOptions = QuestionOptions;
    }


}
