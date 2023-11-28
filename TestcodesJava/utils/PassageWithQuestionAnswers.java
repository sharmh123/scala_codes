package TestcodesJava.utils;

import TestCodesScala.games.QuestionAndAnswer;

import java.util.List;

public class PassageWithQuestionAnswers {

    private String passage;
    private List<QuestionAndAnswer> questionAndAnswerList;

    public PassageWithQuestionAnswers(String passage, List<QuestionAndAnswer> questionAndAnswerList) {
        this.passage = passage;
        this.questionAndAnswerList = questionAndAnswerList;
    }

    public String getPassage() {
        return passage;
    }

    public void setPassage(String passage) {
        this.passage = passage;
    }

    public List<QuestionAndAnswer> getQuestionAndAnswerList() {
        return questionAndAnswerList;
    }

    public void setQuestionAndAnswerList(List<QuestionAndAnswer> questionAndAnswerList) {
        this.questionAndAnswerList = questionAndAnswerList;
    }

}
