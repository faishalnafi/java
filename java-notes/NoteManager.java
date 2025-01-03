import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NoteManager {
    private List<Note> notes;
    private final String folderPath = "data-json";
    private final String filePath = folderPath + "/notes.json";

    public NoteManager() {
        notes = new ArrayList<>();
        loadNotes();
    }

    public void addNote(Note note) {
        notes.add(note);
        saveNotes();
    }

    public void removeNote(Note note) {
        notes.remove(note);
        saveNotes();
    }

    public List<Note> getNotes() {
        return notes;
    }

    private void loadNotes() {
        try {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdir();
            }

            File file = new File(filePath);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                Type listType = new TypeToken<List<Note>>() {}.getType();
                notes = new Gson().fromJson(reader, listType);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error loading notes: " + e.getMessage());
        }
    }

    private void saveNotes() {
        try (Writer writer = new FileWriter(filePath)) {
            new Gson().toJson(notes, writer);
        } catch (IOException e) {
            System.out.println("Error saving notes: " + e.getMessage());
        }
    }
}
