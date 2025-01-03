import java.util.ArrayList;
import java.util.List;

public class NoteManager {
    private List<Note> notes;

    public NoteManager() {
        notes = new ArrayList<>();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }

    public List<Note> getNotes() {
        return notes;
    }
}
