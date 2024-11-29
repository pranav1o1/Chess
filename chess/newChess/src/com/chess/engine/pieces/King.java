package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MAJOR_MOVE;
import com.chess.engine.board.tile;

public class King extends Piece {
    private final static int[] CANDIDATE_MOVE_K_COORDINATE = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public King(int piecePosition, Color pieceColor) {
        super(PieceType.KING, piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_K_COORDINATE) {

            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (isFirstColoumnExclusion(this.piecePosition, currentCandidateOffset)
                    || isEightColoumnExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }
            if (BoardUtils.isValidCoordinate(candidateDestinationCoordinate)) {
                final tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                if (!candidateDestinationTile.isTileOccup()) {
                    legalMoves.add(new MAJOR_MOVE(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor();

                    if (this.pieceColor != pieceColor) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public King movePiece(Move move) {
        return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    private static boolean isFirstColoumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == -9) || (candidateOffset == -1)
                || (candidateOffset == 7));
    }

    private static boolean isEightColoumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset == 9) || (candidateOffset == 1)
                || (candidateOffset == -7));
    }
}
