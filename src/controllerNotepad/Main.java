package controllerNotepad;

import modelNotepad.ModelPad;
import viewNotepad.ViewPad;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<ModelPad> notes = new ArrayList<>();
            notes.add(new ModelPad(
                "Weekend ideas",
                "A few things I'd like to make time for.\n\nTake a long walk somewhere new.",
                LocalDateTime.now()
            ));
            notes.add(new ModelPad(
                "Shopping list",
                "Coffee, bread, oat milk, apples",
                LocalDateTime.now().minusDays(1)
            ));

            ViewPad view = new ViewPad();
            new ControllerPad(view, notes);
            view.setVisible(true);
        });
    }
}