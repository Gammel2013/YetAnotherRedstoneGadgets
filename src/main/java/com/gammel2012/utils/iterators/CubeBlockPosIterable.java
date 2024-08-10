package com.gammel2012.utils.iterators;

import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class CubeBlockPosIterable implements Iterable<BlockPos> {

    private final Iterator<BlockPos> iterator;

    public CubeBlockPosIterable(BlockPos origin, int dX1, int dY1, int dZ1, int dX2, int dY2, int dZ2) {
        this.iterator = new CubeBlockPosIterator(origin, dX1, dY1, dZ1, dX2, dY2, dZ2);
    }

    public CubeBlockPosIterable(BlockPos origin, int dX, int dY, int dZ) {
        this.iterator = new CubeBlockPosIterator(origin, dX, dY, dZ);
    }

    public CubeBlockPosIterable(BlockPos origin, int range) {
        this.iterator = new CubeBlockPosIterator(origin, range);
    }

    @NotNull
    @Override
    public Iterator<BlockPos> iterator() {
        return iterator;
    }

    protected static class CubeBlockPosIterator implements Iterator<BlockPos> {

        private final BlockPos origin;

        private final int dX1;
        private final int dY1;
        private final int dZ1;
        private final int dX2;
        private final int dY2;
        private final int dZ2;

        private int currentX;
        private int currentY;
        private int currentZ;

        protected CubeBlockPosIterator(BlockPos origin, int dX1, int dY1, int dZ1, int dX2, int dY2, int dZ2) {
            this.origin = origin;

            this.dX1 = dX1;
            this.dY1 = dY1;
            this.dZ1 = dZ1;
            this.dX2 = dX2;
            this.dY2 = dY2;
            this.dZ2 = dZ2;

            this.currentX = dX1;
            this.currentY = dY1;
            this.currentZ = dZ1;

            if (dX1 >= dX2 || dY1 >= dY2 || dZ1 >= dZ2) {
                throw new RuntimeException("Invalid bounds: " + dX1 + " " + dY1 + " " + dZ1 + " | " + dX2 + " " + dY2 + " " + dZ2);
            }
        }

        protected CubeBlockPosIterator(BlockPos origin, int dX, int dY, int dZ) {
            this(origin, -dX, -dY, -dZ, dX, dY, dZ);
        }

        protected CubeBlockPosIterator(BlockPos origin, int range) {
            this(origin, range, range, range);
        }

        @Override
        public boolean hasNext() {
            return currentZ <= dZ2;
        }

        @Override
        public BlockPos next() {

            BlockPos result = origin.offset(currentX, currentY, currentZ);

            if (currentX == dX2 && currentY == dY2) {
                currentY = dY1;
                currentX = dX1;
                currentZ++;
            } else if (currentX == dX2) {
                currentX = dX1;
                currentY++;
            } else {
                currentX++;
            }

            return result;
        }
    }
}
