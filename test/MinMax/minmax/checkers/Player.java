package minmax.checkers;

public class Player {

    private static final String defName = "Player";

    private String name;
    private Figure[] ownFigures;
    private int points = 0;

    public Player(String name, Figure[] ownFigures) {
        validateOwnedFigures(ownFigures);
        this.name = name == null ? defName : name;
        this.ownFigures = ownFigures;
    }

    public boolean ownFigure(Figure figure) {
        if(figure == null) {
            return false;
        }
        return ownFigures[0].isAlly(figure);
    }

    public String getName() {
        return name;
    }

    public void score() {
        this.points++;
    }
    public int getPoints() {
        return points;
    }

    public Figure[] getOwnFigures() {
        return ownFigures;
    }

    private void validateOwnedFigures(Figure[] ownFigures) {
        if(ownFigures == null || ownFigures.length != 2 || ownFigures[0] == null || ownFigures[1] == null) {
            throw new RuntimeException("Gracz musi posiadaæ dwie figury do gry.");
        }
        if(!ownFigures[0].isPlayable() || !ownFigures[1].isPlayable()) {
            throw new RuntimeException("Gracz mo¿e posiadaæ tylko grywalne figury.");
        }
        if(!ownFigures[0].isAlly(ownFigures[1])) {
            throw new RuntimeException("Gracz mo¿e posiadaæ figury tylko jednego koloru.");
        }
    }

}
