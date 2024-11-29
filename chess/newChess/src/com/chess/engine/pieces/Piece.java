package com.chess.engine.pieces;

import java.util.Collection;
import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece {
    protected final PieceType pieceType;
    protected int piecePosition;
    protected final Color pieceColor; // Color=Alliance
    protected final boolean isFirstMove;
    private final int cacheHashCode;

    Piece(PieceType pieceType, final int piecePosition, final Color pieceColor) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.piecePosition = piecePosition;
        this.isFirstMove = false;
        this.cacheHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceColor.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
                pieceColor == otherPiece.getPieceColor() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode() {
        return this.cacheHashCode;
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public Color getPieceColor() {
        return this.pieceColor;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public abstract Collection<Move> calcLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public enum PieceType {
        PAWN("P") {
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            public boolean isKing() {
                return true;
            }
        },
        ROOK("R") {
            public boolean isKing() {
                return false;
            }
        };

        private String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public boolean isKing() {
            return false;
        }
    }

}
