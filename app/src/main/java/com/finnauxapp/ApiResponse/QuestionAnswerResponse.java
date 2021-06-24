package com.finnauxapp.ApiResponse;

public class QuestionAnswerResponse {


    /**
     * QueId : 8
     * Question : The hirer has his own house
     * Question_Hindi : हायरर का स्वय का मकान है
     * QuestionType : Selection
     * QuestionOptions : Yes@@No
     * FIAnswer :
     */

    private int QueId;
    private String Question;
    private String Question_Hindi;
    private String QuestionType;
    private String QuestionOptions;
    private String FIAnswer;

    public int getQueId() {
        return QueId;
    }

    public void setQueId(int QueId) {
        this.QueId = QueId;
    }

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

    public String getFIAnswer() {
        return FIAnswer;
    }

    public void setFIAnswer(String FIAnswer) {
        this.FIAnswer = FIAnswer;
    }
}
