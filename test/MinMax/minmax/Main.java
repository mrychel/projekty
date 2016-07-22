package minmax;

import minmax.checkers.*;

public class Main {

    public static void main(String...args) {
        Player playerA = new Player("Player1", new Figure[]{Figure.WHITE_PIECE, Figure.WHITE_KING});
        Player playerB = new Player("Player2", new Figure[]{Figure.BLACK_PIECE, Figure.BLACK_KING});
        Table table = new Table(playerA, playerB);
        table.resetTable();
        TablePrinter printer = new TablePrinter(table, 1);
        printer.printTable();
        table.move("a3", "b4");
        printer.printTable();
        table.move("h6", "g5");
        printer.printTable();
        table.move("b4", "c5");
        printer.printTable();
        table.move("d6", "c5");
        printer.printTable();
    }

}
