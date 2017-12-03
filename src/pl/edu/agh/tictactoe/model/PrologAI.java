package pl.edu.agh.tictactoe.model;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

import java.util.Map;

public class PrologAI {

    public static class Point {
        private final int x;
        private final int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX() { return x; }
        public int getY() { return y; }

        @Override
        public String toString() {
            return "Point{x=" + x + ", y=" + y + '}';
        }
    }

    private static final String PROGRAM_FILE = "tictactoe.pl";

    public PrologAI() {
        loadProgram();
    }

    private void loadProgram() {
       // String path = getClass().getClassLoader().getResource(PROGRAM_FILE).getFile().substring(1);
        String path = "D:\\Semestr 9\\Ekspertowe\\TicTacToe\\resources\\tictactoe.pl";
        Query loadProgramQuery = new Query("consult", new Term[] {new Atom(path)});
        loadProgramQuery.allSolutions();
    }

    public Point nextMove() {
        Query query = new Query("next_move(X)");
        Map<String, Term>[] allSolutions = query.allSolutions();
        Term term = allSolutions[0].values().iterator().next();
        if (!",".equals(term.name()) || term.arity() != 2) {
            throw new RuntimeException("Unexpected term: " + term);
        }
        return new Point(term.arg(1).intValue(), term.arg(2).intValue());
    }

    public void addPlayerField(Point point) {
        Query query = new Query("remember_enemy(("+point.getX()+","+point.getY()+"))");
        query.allSolutions();
        // assertz(...)
    }

    public void addAIField(Point point) {
        Query query = new Query("remember_self(("+point.getX()+","+ point.getY()+"))");
        query.allSolutions();
        // assertz(...)
    }
}
