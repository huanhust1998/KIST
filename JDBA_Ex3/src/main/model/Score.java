package main.model;

public class Score {
    private int studentID;
    private int subjectID;
    private int scores;
    public Score() {

    }

    public Score(int studentID, int subjectID,int scores) {
        this.studentID = studentID;
        this.subjectID = subjectID;
        this.scores = scores;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }
}
