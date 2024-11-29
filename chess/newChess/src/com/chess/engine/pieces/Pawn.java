package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MAJOR_MOVE;

public class Pawn extends Piece {
    private static final int[] CANDIDATE_MOVE_PAWN_COORDINATE = { 7, 8, 9, 16 };

    public Pawn(final int piecePosition, final Color pieceColor) {
        super(PieceType.PAWN, piecePosition, pieceColor);

    }

    @Override
    public Collection<Move> calcLegalMoves(final Board board) {

        final List<Move> legaMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_PAWN_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition
                    + (this.pieceColor.getDirection() * currentCandidateOffset);

            if (!BoardUtils.isValidCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccup()) {
                // promotions
                legaMoves.add(new MAJOR_MOVE(board, this, candidateDestinationCoordinate));
            } else if (currentCandidateOffset == 16 && this.isFirstMove()
                    && ((BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceColor().isBlack())
                            || (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceColor().isWhite()))) {
                final int behindDestinationCoordinate = this.piecePosition + (this.pieceColor.getDirection() * 8);
                if (!board.getTile(behindDestinationCoordinate).isTileOccup()
                        && !board.getTile(candidateDestinationCoordinate).isTileOccup()) {
                    legaMoves.add(new MAJOR_MOVE(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7
                    && ((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isWhite()) ||
                            (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccup()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        // promotion
                        legaMoves.add(new MAJOR_MOVE(board, this, candidateDestinationCoordinate));
                    }
                }
            } else if (currentCandidateOffset == 9
                    && ((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isBlack()) ||
                            (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isWhite()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccup()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        // promotion
                        legaMoves.add(new MAJOR_MOVE(board, this, candidateDestinationCoordinate));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legaMoves);
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
