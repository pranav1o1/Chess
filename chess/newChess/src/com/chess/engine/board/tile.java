package com.chess.engine.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;
//import com.chess.engine.board.BoardUtils;

public abstract class tile {

    protected final int tileCoordinate;

    private final static Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTile();

    private tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public static tile creatTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTile() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return Collections.unmodifiableMap(emptyTileMap);
    }

    public abstract boolean isTileOccup();

    public abstract Piece getPiece();

    public static final class EmptyTile extends tile {

        EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public boolean isTileOccup() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupTile extends tile {

        private final Piece pieceOnTile;

        OccupTile(int tileCoordinate, Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString() {
            return getPiece().getPieceColor().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }

        @Override
        public boolean isTileOccup() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }

        public static boolean isValidTileCoordinate(final int coordinate) {
            return coordinate >= 0 && coordinate < 64;
        }
    }
}
