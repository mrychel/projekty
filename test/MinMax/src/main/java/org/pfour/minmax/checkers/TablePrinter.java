package org.pfour.minmax.checkers;

public class TablePrinter {

    private static final String SPACE = " ";
    private static final String LABEL_Y = "  A B C D E F G H ";
    private static final char[] WHITE_PIECE = new char[]{'?', 'o'};
    private static final char[] BLACK_PIECE = new char[]{'?', 'x'};
    private static final char[] WHITE_KING = new char[]{'?', 'O'};
    private static final char[] BLACK_KING = new char[]{'?', 'X'};
    private static final char[] PLAYABLE_FIELD = new char[]{'?', '-'};
    private static final char[] UNPLAYABLE_FIELD = new char[]{'?', ' '};

    private Table table;
    private int tabStyle;

    public TablePrinter(Table table) {
        this.table = table;
    }

    public TablePrinter(Table table, int tabStyle) {
        this.table = table;
        this.tabStyle = tabStyle % PLAYABLE_FIELD.length;
    }

    public void printTable() {
        System.out.print(getTableAsString());
    }

    public String getTableAsString() {
        validateIfTableReadyForPrinting(table);
        StringBuilder tableString = new StringBuilder();
        tableString.append("==== ").append(table.currePlayer.getName()).append(" turn ====").append(System.lineSeparator());
        tableString.append(table.playerB.getName()).append(" score ").append(table.playerB.getPoints()).append(System.lineSeparator());
        tableString.append(LABEL_Y).append(System.lineSeparator());
        for(int y = (table.fields[0].length-1); y >= 0 ; y--) {
            tableString.append(y+1).append(SPACE);
            for(int x = 0; x < table.fields.length; x++) {
                tableString.append(getFigureRepresentation(table.fields[x][y])).append(SPACE);
            }
            tableString.append(y+1).append(System.lineSeparator());
        }
        tableString.append(LABEL_Y).append(System.lineSeparator());
        tableString.append(table.playerA.getName()).append(" score ").append(table.playerA.getPoints()).append(System.lineSeparator());
        return tableString.toString();
    }

    private char getFigureRepresentation(Figure field) {
        if(field == null) {
            return PLAYABLE_FIELD[tabStyle];
        }
        switch(field) {
            case WHITE_PIECE:
                return WHITE_PIECE[tabStyle];
            case BLACK_PIECE:
                return BLACK_PIECE[tabStyle];
            case WHITE_KING:
                return WHITE_KING[tabStyle];
            case BLACK_KING:
                return BLACK_KING[tabStyle];
            case UNPLAYABLE_FIELD:
            default:
                return UNPLAYABLE_FIELD[tabStyle];
        }
    }

    private void validateIfTableReadyForPrinting(Table table) {
        if(table == null || table.fields == null || table.fields[0] == null) {
            throw new RuntimeException("Brak warcabnicy!");
        }
    }

}
