package pl.edu.agh.tictactoe;

import pl.edu.agh.tictactoe.controller.GameController;
import pl.edu.agh.tictactoe.gui.Gui;
import pl.edu.agh.tictactoe.model.Model;

public class Main {

    public static void main(String[] argv) {
        Gui view = new Gui();
        Model model = new Model();
        new GameController(view, model);
        view.setVisible(true);
    }


}
