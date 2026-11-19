package controllerNotepad;

import modelNotepad.ModelPad;
import viewNotepad.ViewPad;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.time.LocalDateTime;
import java.util.List;

public class ControllerPad {
    private final ViewPad view;
    private final List<ModelPad> notes;
    private ModelPad currentNote;
    private boolean isUpdatingView = false;

    public ControllerPad(ViewPad view, List<ModelPad> notes) {
        this.view = view;
        this.notes = notes;

        loadInitialNotes();
        registerListeners();
    }

    private void loadInitialNotes() {
        view.getNotesListModel().clear();
        for (ModelPad note : notes) {
            view.getNotesListModel().addElement(note);
        }

        if (!notes.isEmpty()) {
            selectNote(0);
        } else {
            currentNote = null;
            view.displayNote(null);
        }
    }

    private void registerListeners() {

        view.getNotesList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = view.getNotesList().getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < view.getNotesListModel().size()) {
                    ModelPad selected = view.getNotesListModel().getElementAt(selectedIndex);
                    selectNoteObject(selected);
                }
            }
        });

        view.getNewNoteBtn().addActionListener(e -> handleNewNote());

        view.getBodyTextArea().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onBodyChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onBodyChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onBodyChanged();
            }
        });
    }

    private void selectNote(int index) {
        view.getNotesList().setSelectedIndex(index);
        ModelPad selected = view.getNotesListModel().getElementAt(index);
        selectNoteObject(selected);
    }

    private void selectNoteObject(ModelPad note) {
        isUpdatingView = true;
        currentNote = note;
        view.displayNote(note);
        view.updateWordCount(countWords(note != null ? note.getContent() : ""));
        isUpdatingView = false;
    }

    private void handleNewNote() {
        ModelPad newNote = new ModelPad("Untitled note", "", LocalDateTime.now());
        notes.add(0, newNote);
        view.getNotesListModel().add(0, newNote);

        selectNote(0);
        view.setStatusText("New note created");
    }

    private void onBodyChanged() {
        if (isUpdatingView) {
            return;
        }
        String text = view.getBodyTextInput();
        view.updateWordCount(countWords(text));

        if (currentNote != null) {
            currentNote.setContent(text);
            view.getNotesList().repaint();
        }
    }

    private int countWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        return text.trim().split("\\s+").length;
    }
}