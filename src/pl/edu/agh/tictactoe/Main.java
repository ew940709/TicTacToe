package pl.edu.agh.tictactoe;

import pl.edu.agh.tictactoe.controller.GameController;
import pl.edu.agh.tictactoe.gui.Gui;
import pl.edu.agh.tictactoe.model.Model;
import pl.edu.agh.tictactoe.model.PrologAI;

public class Main {

    public static void main(String[] argv) {
        PrologAI ai = new PrologAI();
        PrologAI.Point move = ai.nextMove();
        System.out.println(move);

        Gui view = new Gui();
        Model model = new Model();
        new GameController(view, model);
        view.setVisible(true);
    }


}
