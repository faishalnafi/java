public class MainApp {
    public static void main(String[] args) {
        NoteManager noteManager = new NoteManager();
        NoteView noteView = new NoteView(noteManager);
        noteView.setVisible(false);
    }
}
