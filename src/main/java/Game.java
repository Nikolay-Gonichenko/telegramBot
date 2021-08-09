import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class Game {
    private int level;
    private int currentQuestion;
    private int rightAnswers;
    private boolean isStarted;
    public void start(int level){
        this.level = level;
        isStarted = true;
        currentQuestion = 1;
        rightAnswers = 0;
    }



    public boolean isStarted() {
        return isStarted;
    }
    public void rightAnswer(){
        rightAnswers++;
    }
    public void nextQuestion(){
        currentQuestion++;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public int getRightAnswers() {
        return rightAnswers;
    }

    public String[] getQuestion(ResourceBundle rb) {
        String[] request = new String[4];
        String question  = "question" + "_" + level + "_" + currentQuestion;
        String answer1 = "answer" + "_" + level + "_" + currentQuestion + "_1";
        String answer2 = "answer" + "_" + level + "_" + currentQuestion + "_2";
        String answer3 = "answer" + "_" + level + "_" + currentQuestion + "_3";
        request[0] = new String(rb.getString(question).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        request[1] = new String(rb.getString(answer1).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        request[2] = new String(rb.getString(answer2).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        request[3] = new String(rb.getString(answer3).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        return request;
    }

    public void end() {
        isStarted = false;
        level = 0;
    }

    public int getLevel() {
        return level;
    }
}
