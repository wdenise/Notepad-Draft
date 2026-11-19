package viewNotepad;

import modelNotepad.ModelPad;

import javax.swing.*;
import java.awt.*;

public class ViewPad extends JFrame {
    private JTextField searchField;
    private DefaultListModel<ModelPad> notesListModel;
    private JList<ModelPad> notesList;

    private JButton newNoteBtn;
    private JButton editBtn;
    private JButton deleteBtn;

    private JTextField noteTitleField;
    private JLabel dateLabel;
    private JTextArea bodyTextArea;
    private JLabel wordCountLabel;
    private JLabel statusLabel;

    public ViewPad() {
        setTitle("Notepad");
        setSize(960, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            createSidebarPanel(),
            createEditorPanel()
        );
        splitPane.setDividerLocation(280);
        splitPane.setDividerSize(1);
        splitPane.setEnabled(false);

        add(splitPane);
    }

    // --- LEFT SIDEBAR ---
    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(new Color(246, 246, 248));
        sidebar.setBorder(BorderFactory.createEmptyBorder(16, 16, 12, 16));

        JLabel appTitleLabel = new JLabel("Notepad");
        appTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        appTitleLabel.setForeground(new Color(25, 25, 25));

        searchField = new JTextField();
        searchField.putClientProperty("JTextField.placeholderText", "Search notes...");
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 224)),
            BorderFactory.createEmptyBorder(7, 10, 7, 10)
        ));

        JPanel topSection = new JPanel(new BorderLayout(0, 12));
        topSection.setOpaque(false);
        topSection.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        topSection.add(appTitleLabel, BorderLayout.NORTH);
        topSection.add(searchField, BorderLayout.CENTER);

        notesListModel = new DefaultListModel<>();
        notesList = new JList<>(notesListModel);
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notesList.setBackground(new Color(246, 246, 248));
        notesList.setSelectionBackground(new Color(226, 228, 232));
        notesList.setSelectionForeground(new Color(20, 20, 20));
        notesList.setFixedCellHeight(64);
        notesList.setCellRenderer(new NoteCellRenderer());

        JScrollPane listScrollPane = new JScrollPane(notesList);
        listScrollPane.setBorder(null);
        listScrollPane.getViewport().setBackground(new Color(246, 246, 248));

        JLabel storageLabel = new JLabel("Notes saved on this device");
        storageLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        storageLabel.setForeground(Color.GRAY);
        storageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        sidebar.add(topSection, BorderLayout.NORTH);
        sidebar.add(listScrollPane, BorderLayout.CENTER);
        sidebar.add(storageLabel, BorderLayout.SOUTH);

        return sidebar;
    }

    // --- RIGHT EDITOR AREA ---
    private JPanel createEditorPanel() {
        JPanel editorContainer = new JPanel(new BorderLayout());
        editorContainer.setBackground(Color.WHITE);
        editorContainer.setBorder(BorderFactory.createEmptyBorder(16, 28, 12, 28));

        // Top bar: subtitle on left, action buttons on right
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));

        JLabel subtitleLabel = new JLabel("A little space for your thoughts");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitleLabel.setForeground(Color.GRAY);

        JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonGroup.setOpaque(false);

        editBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete note");
        newNoteBtn = new JButton("New note");
        newNoteBtn.setBackground(new Color(30, 30, 32));
        newNoteBtn.setForeground(Color.WHITE);
        newNoteBtn.setFocusPainted(false);

        buttonGroup.add(editBtn);
        buttonGroup.add(deleteBtn);
        buttonGroup.add(newNoteBtn);

        topBar.add(subtitleLabel, BorderLayout.WEST);
        topBar.add(buttonGroup, BorderLayout.EAST);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBackground(Color.WHITE);

        noteTitleField = new JTextField();
        noteTitleField.setFont(new Font("SansSerif", Font.BOLD, 24));
        noteTitleField.setForeground(new Color(25, 25, 25));
        noteTitleField.setBorder(null);
        noteTitleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        noteTitleField.setAlignmentX(Component.LEFT_ALIGNMENT);

        dateLabel = new JLabel("No note selected");
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dateLabel.setForeground(Color.GRAY);
        dateLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 16, 0));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        bodyTextArea = new JTextArea();
        bodyTextArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bodyTextArea.setForeground(new Color(40, 40, 40));
        bodyTextArea.setLineWrap(true);
        bodyTextArea.setWrapStyleWord(true);
        bodyTextArea.setBorder(null);

        JScrollPane bodyScrollPane = new JScrollPane(bodyTextArea);
        bodyScrollPane.setBorder(null);
        bodyScrollPane.getViewport().setBackground(Color.WHITE);
        bodyScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        fieldsPanel.add(noteTitleField);
        fieldsPanel.add(dateLabel);
        fieldsPanel.add(bodyScrollPane);

        JPanel statusBotBar = new JPanel(new BorderLayout());
        statusBotBar.setOpaque(false);
        statusBotBar.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        wordCountLabel = new JLabel("0 words");
        wordCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        wordCountLabel.setForeground(Color.GRAY);

        statusLabel = new JLabel("Plain text");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(Color.GRAY);

        statusBotBar.add(wordCountLabel, BorderLayout.WEST);
        statusBotBar.add(statusLabel, BorderLayout.EAST);

        editorContainer.add(topBar, BorderLayout.NORTH);
        editorContainer.add(fieldsPanel, BorderLayout.CENTER);
        editorContainer.add(statusBotBar, BorderLayout.SOUTH);

        return editorContainer;
    }

    private static class NoteCellRenderer extends JPanel implements ListCellRenderer<ModelPad> {
        private final JLabel titleLbl = new JLabel();
        private final JLabel snippetLbl = new JLabel();

        public NoteCellRenderer() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

            titleLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
            snippetLbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
            snippetLbl.setForeground(Color.GRAY);

            add(titleLbl);
            add(Box.createVerticalStrut(2));
            add(snippetLbl);
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends ModelPad> list,
                ModelPad value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            String title = (value.getTitle() == null || value.getTitle().isBlank())
                    ? "Untitled note"
                    : value.getTitle();
            String rawContent = (value.getContent() == null) ? "" : value.getContent().replace("\n", " ");
            String snippet = rawContent.length() > 42 ? rawContent.substring(0, 42) + "..." : rawContent;

            titleLbl.setText(title);
            snippetLbl.setText(snippet);

            setOpaque(true);
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            titleLbl.setForeground(isSelected ? list.getSelectionForeground() : Color.BLACK);
            return this;
        }
    }

    public void displayNote(ModelPad note) {
        if (note == null) {
            noteTitleField.setText("");
            bodyTextArea.setText("");
            dateLabel.setText("No note selected");
            updateWordCount(0);
            return;
        }
        noteTitleField.setText(note.getTitle());
        bodyTextArea.setText(note.getContent());
        dateLabel.setText("Edited " + note.getFormattedTimestamp());
    }

    public void updateWordCount(int count) {
        wordCountLabel.setText(count + (count == 1 ? " word" : " words"));
    }

    public void setStatusText(String status) {
        statusLabel.setText(status);
    }

    public DefaultListModel<ModelPad> getNotesListModel() { return notesListModel; }
    public JList<ModelPad> getNotesList() { return notesList; }
    public JTextField getSearchField() { return searchField; }
    public JButton getNewNoteBtn() { return newNoteBtn; }
    public JButton getEditBtn() { return editBtn; }
    public JButton getDeleteBtn() { return deleteBtn; }
    public String getNoteTitleInput() { return noteTitleField.getText(); }
    public String getBodyTextInput() { return bodyTextArea.getText(); }
    public JTextArea getBodyTextArea() { return bodyTextArea; }
}