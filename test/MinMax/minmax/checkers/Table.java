package minmax.checkers;

public class Table {

    private static final int BOARD_SIZE = 8;
    Figure[][] fields;
    Player playerA;
    Player playerB;
    Player currePlayer;

    public Table(Player playerA, Player playerB) {
        validatePlayers(playerA, playerB);
        this.playerA = playerA;
        this.playerB = playerB;
        this.currePlayer = playerA;
    }

    public void resetTable() {
        fields = new Figure[BOARD_SIZE][BOARD_SIZE];
        for(int y = 0; y < BOARD_SIZE; y++) {
            for(int x = 0; x < BOARD_SIZE; x++) {
                placeFigure(x, y);
            }
        }
    }

    public void move(String currentPos, String newPos) {
        int[] currentXY = decodePosition(currentPos);
        int[] newXY = decodePosition(newPos);
        move(currentXY[0], currentXY[1], newXY[0], newXY[1]);
    }

    public void move(int currentX, int currentY, int newX, int newY) {
        Figure selecFigure = fields[currentX][currentY];
        if(selecFigure == null) {
            throw new IllegalArgumentException("Na wybranym polu nie ma pionka!");
        }
        if(!currePlayer.ownFigure(selecFigure)) {
            throw new IllegalArgumentException("To nie twoj pionek!");
        }
        Figure targetField = fields[newX][newY];
        if(targetField != null) {
            System.out.println(targetField);
            if(!selecFigure.isAlly(targetField)) {
                currePlayer.score();
            } else {
                throw new IllegalArgumentException("Nieprawidlowy ruch!");
            }
        }
        fields[currentX][currentY] = null;
        fields[newX][newY] = selecFigure;
        if(currePlayer == playerA) {
            currePlayer = playerB;
        } else {
            currePlayer = playerA;
        }
    }

    private void placeFigure(int x, int y) {
        fields[x][y] = getStartState(x, y);
    }

    private Figure getStartState(int x, int y) {
        if(fieldIsPlayable(x, y)) {
            if(y < 3) {
                return Figure.WHITE_PIECE;
            } else if(y > 4) {
                return Figure.BLACK_PIECE;
            }
        } else {
            return Figure.UNPLAYABLE_FIELD;
        }
        return null;
    }

    private boolean fieldIsPlayable(int x, int y) {
        return (x + y) % 2 == 0;
    }

    private int[] decodePosition(String position) {
        if(position == null) {
            throw new IllegalStateException("Nieprawid³owa pozycja.");
        }
        position = position.trim().toLowerCase();
        if(position.length() != 2){
            throw new IllegalStateException("Nieprawid³owa pozycja.");
        }
        char x = position.charAt(0);
        char y = position.charAt(1);
        int[] positionXY = new int[2];
        positionXY[0] = decodePositionLetter(x);
        try {
            positionXY[1] = Integer.valueOf(String.valueOf(y)).intValue()-1;
        } catch(NumberFormatException e) {
            throw new IllegalStateException("Nieprawid³owa pozycja.", e);
        }
        if(positionXY[0] >= fields.length || positionXY[1] >= fields[0].length) {
            throw new IllegalStateException("Nieprawid³owa pozycja.");
        }
        return positionXY;
    }

    private int decodePositionLetter(char positionChar) {
        switch(positionChar) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            default:
                return Integer.MAX_VALUE;
        }

    }

    private void validatePlayers(Player playerA, Player playerB) {
        if(playerA == playerB) {
            throw new RuntimeException("Ta gra wymaga dwoje graczy.");
        }
        if (playerA.ownFigure(playerB.getOwnFigures()[0])) {
            throw new RuntimeException("Gracze musz¹ graæ po przeciwnej stronie.");
        }
    }

}
