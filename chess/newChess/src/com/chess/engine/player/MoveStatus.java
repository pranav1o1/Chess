package com.chess.engine.player;

public enum MoveStatus {
    COMPLETE {
        @Override
        boolean isComplete() {
            return true;
        }
    },
    ILLEGAL_MOVE {
        @Override
        boolean isComplete() {
            return false;
        };
    },
    LEAVE_IN_CHECK {
        @Override
        boolean isComplete() {
            return false;
        };
    };

    abstract boolean isComplete();
}
