package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.Color;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.Player;
import com.chess.engine.player.whitePlayer;
import com.chess.engine.player.blackPlayer;

public class Board {

    private final List<tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final blackPlayer blackPlayer;
    private final whitePlayer whitePlayer;
    private final Player currentPlayer;

    private Board(final Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calcActivePieces(this.gameBoard, Color.WHITE);
        this.blackPieces = calcActivePieces(this.gameBoard, Color.BLACK);

        final Collection<Move> whiteStandardLegalMoves = calcLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calcLegalMoves(this.blackPieces);

        this.whitePlayer = new whitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new blackPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if ((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    // private static String prettyprint(tile tile) {
    // return tile.toString();
    // }

    public Player WhitePlayer() {
        return this.whitePlayer;
    }

    public Player BlackPlayer() {
        return this.blackPlayer;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    private Collection<Move> calcLegalMoves(final Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : pieces) {
            legalMoves.addAll(piece.calcLegalMoves(this));
        }
        return Collections.unmodifiableList(legalMoves);
    }

    private static Collection<Piece> calcActivePieces(final List<tile> gameBoard, final Color color) {
        final List<Piece> activePieces = new ArrayList<>();
        for (final tile Tile : gameBoard) {
            if (Tile.isTileOccup()) {
                final Piece piece = Tile.getPiece();
                if (piece.getPieceColor() == color) {
                    activePieces.add(piece);
                }
            }
        }
        return Collections.unmodifiableList(activePieces);
    }

    public tile getTile(final int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }

    private static List<tile> createGameBoard(final Builder builder) {
        final tile[] Tiles = new tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            Tiles[i] = tile.creatTile(i, builder.boardConfig.get(i));
        }
        return Collections.unmodifiableList(new ArrayList<>(Arrays.asList(Tiles)));
    }

    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        builder.setPiece(new Rook(0, Color.BLACK));
        builder.setPiece(new Knight(1, Color.BLACK));
        builder.setPiece(new Bishop(2, Color.BLACK));
        builder.setPiece(new Queen(3, Color.BLACK));
        builder.setPiece(new King(4, Color.BLACK));
        builder.setPiece(new Bishop(5, Color.BLACK));
        builder.setPiece(new Knight(6, Color.BLACK));
        builder.setPiece(new Rook(7, Color.BLACK));
        builder.setPiece(new Pawn(8, Color.BLACK));
        builder.setPiece(new Pawn(9, Color.BLACK));
        builder.setPiece(new Pawn(10, Color.BLACK));
        builder.setPiece(new Pawn(11, Color.BLACK));
        builder.setPiece(new Pawn(12, Color.BLACK));
        builder.setPiece(new Pawn(13, Color.BLACK));
        builder.setPiece(new Pawn(14, Color.BLACK));
        builder.setPiece(new Pawn(15, Color.BLACK));

        builder.setPiece(new Rook(63, Color.WHITE));
        builder.setPiece(new Knight(62, Color.WHITE));
        builder.setPiece(new Bishop(61, Color.WHITE));
        builder.setPiece(new King(60, Color.WHITE));
        builder.setPiece(new Queen(59, Color.WHITE));
        builder.setPiece(new Bishop(58, Color.WHITE));
        builder.setPiece(new Knight(57, Color.WHITE));
        builder.setPiece(new Rook(56, Color.WHITE));
        builder.setPiece(new Pawn(55, Color.WHITE));
        builder.setPiece(new Pawn(54, Color.WHITE));
        builder.setPiece(new Pawn(53, Color.WHITE));
        builder.setPiece(new Pawn(52, Color.WHITE));
        builder.setPiece(new Pawn(51, Color.WHITE));
        builder.setPiece(new Pawn(50, Color.WHITE));
        builder.setPiece(new Pawn(49, Color.WHITE));
        builder.setPiece(new Pawn(48, Color.WHITE));

        builder.setMoveMaker(Color.WHITE);

        return builder.build();
    }

    /*
     * public Iterable<Move> getAllLegalMoves() {
     * return Iterable.unmodifiableIterable(
     * Iterable.concat(this.whitePlayer.getLegalMoves(),
     * this.blackPlayer.getLegalMoves()));
     * }
     */
    public Iterable<Move> getAllLegalMoves() {
        List<Move> allMoves = new ArrayList<>();
        allMoves.addAll(this.whitePlayer.getLegalMoves());
        allMoves.addAll(this.blackPlayer.getLegalMoves());
        return Collections.unmodifiableList(allMoves);
    }

    public static class Builder {
        Map<Integer, Piece> boardConfig;
        Color nextMoveMaker;
        Pawn enPassentPawn;

        public Builder() {
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Color nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }

        public void setEnPassentPawn(Pawn enPassentPawn) {
            this.enPassentPawn = enPassentPawn;
        }

    }
}
