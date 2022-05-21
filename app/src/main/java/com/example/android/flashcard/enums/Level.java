package com.example.android.flashcard.enums;

public enum Level {
    A1(0), A2(1), B1(2), B2(3), C1(4), C2(5);

    private int order;

    Level(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
