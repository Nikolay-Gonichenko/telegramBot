public class Game {
    private int level;
    private int currentQuestion;
    private int rightAnswers;
    private boolean isStarted;
    public void start(int level){
        this.level = level;
        isStarted = true;
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
}
