package entity;

public class Score {
    private int studentId;
    private int subjectId;
    private int score;

    public Score() {
    }

    public Score(int studentId, int subjectId, int score) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.score = score;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
