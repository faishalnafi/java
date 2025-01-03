import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteView extends JFrame {
    private NoteManager noteManager;
    private JList<Note> noteList;
    private JTextArea noteContentArea;

    public NoteView(NoteManager noteManager) {
        this.noteManager = noteManager;
        setTitle("Catatan Harian");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel daftar catatan
        noteList = new JList<>();
        noteList.addListSelectionListener(e -> {
            Note selectedNote = noteList.getSelectedValue();
            if (selectedNote != null) {
                noteContentArea.setText(selectedNote.getContent());
            }
        });
        add(new JScrollPane(noteList), BorderLayout.WEST);

        // Area untuk isi catatan
        noteContentArea = new JTextArea();
        add(new JScrollPane(noteContentArea), BorderLayout.CENTER);

        // Panel tombol
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Tambah Catatan");
        JButton deleteButton = new JButton("Hapus Catatan");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Judul Catatan:");
                if (title != null && !title.isEmpty()) {
                    Note note = new Note(title, "");
                    noteManager.addNote(note);
                    updateNoteList();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Note selectedNote = noteList.getSelectedValue();
                if (selectedNote != null) {
                    noteManager.removeNote(selectedNote);
                    updateNoteList();
                    noteContentArea.setText("");
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateNoteList();
    }

    private void updateNoteList() {
        DefaultListModel<Note> model = new DefaultListModel<>();
        for (Note note : noteManager.getNotes()) {
            model.addElement(note);
        }
        noteList.setModel(model);
    }
}
