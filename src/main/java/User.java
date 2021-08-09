public class User {
    private String chatID;
    private Game game = null;

    public User(String chatID) {
        this.chatID = chatID;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getChatID() {
        return chatID;
    }

    public Game getGame() {
        return game;
    }

    public boolean hasGame() {
        return game != null;
    }
}
