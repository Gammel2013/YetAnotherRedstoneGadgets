package com.gammel2012.yetanotherredstonegadgets.enums;

import net.minecraft.util.StringRepresentable;

public enum AnalogReaderArea implements StringRepresentable {
    THREE_SQUARE("3x3", 3, 3, 1),
    FIVE_SQUARE("5x5", 5, 5, 1),
    SEVEN_SQUARE("7x7", 7, 7, 1),
    THREE_CUBE("3x3x3", 3, 3, 3),
    FIVE_CUBE("5x5x5", 5, 5, 5);

    private String name;
    private int width;
    private int height;
    private int depth;

    AnalogReaderArea(String s, int width, int height, int depth) {
        this.name = s;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public int getVolume() {
        return width * height * depth;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public static AnalogReaderArea fromString(String s) {
        for (AnalogReaderArea area : values()) {
            if (area.getSerializedName().equals(s)) return area;
        }

        throw new RuntimeException("AnalogReaderArea '" + s + "' does not exist.");
    }
}
