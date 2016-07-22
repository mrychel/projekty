package minmax.checkers;

public enum Figure {
    BLACK_PIECE(true), BLACK_KING(true), WHITE_PIECE(true), WHITE_KING(true), UNPLAYABLE_FIELD(false);

    private boolean playable;

    Figure(boolean playable) {
        this.playable = playable;
    }

    public boolean isPlayable() {
        return playable;
    }

    public boolean isAlly(Figure figure) {
        if(figure == this
                || (this == WHITE_PIECE && figure == WHITE_KING) || (this == WHITE_KING && figure == WHITE_PIECE)
                || (this == BLACK_PIECE && figure == BLACK_KING) || (this == BLACK_KING && figure == BLACK_PIECE)) {
            return true;
        }
        return false;
    }

}
