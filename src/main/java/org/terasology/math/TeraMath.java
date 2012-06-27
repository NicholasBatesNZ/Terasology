/*
 * Copyright 2012 Benjamin Glatzel <benjamin.glatzel@me.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.math;

import org.terasology.logic.world.Chunk;

/**
 * Collection of math functions.
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public final class TeraMath {

    private TeraMath() {
    }

    public static final float RAD_TO_DEG = (float) (180.0f / Math.PI);
    public static final float DEG_TO_RAD = (float) (Math.PI / 180.0f);

    /**
     * Returns the absolute value.
     *
     * @param i
     * @return the absolute value
     */
    public static int fastAbs(int i) {
        return (i >= 0) ? i : -i;
    }

    /**
     * Returns the absolute value.
     *
     * @param d
     * @return the absolute value
     */
    public static float fastAbs(float d) {
        return (d >= 0) ? d : -d;
    }

    /**
     * Returns the absolute value.
     *
     * @param d
     * @return
     */
    public static double fastAbs(double d) {
        return (d >= 0) ? d : -d;
    }

    public static double fastFloor(double d) {
        int i = (int) d;
        return (d < 0 && d != i) ? i - 1 : i;
    }

    public static float fastFloor(float d) {
        int i = (int) d;
        return (d < 0 && d != i) ? i - 1 : i;
    }

    /**
     * Clamps a given value to be an element of [0..1].
     */
    public static double clamp(double value) {
        if (value > 1.0)
            return 1.0;
        if (value < 0.0)
            return 0.0;
        return value;
    }

    public static double clamp(double value, double min, double max) {
        if (value > max)
            return max;
        if (value < min)
            return min;
        return value;
    }

    public static float clamp(float value, float min, float max) {
        if (value > max)
            return max;
        if (value < min)
            return min;
        return value;
    }

    public static int clamp(int value, int min, int max) {
        if (value > max)
            return max;
        if (value < min)
            return min;
        return value;
    }

    /**
     * Bilinear interpolation.
     */
    public static double biLerp(double x, double y, double q11, double q12, double q21, double q22, double x1, double x2, double y1, double y2) {
        double r1 = lerp(x, x1, x2, q11, q21);
        double r2 = lerp(x, x1, x2, q12, q22);
        return lerp(y, y1, y2, r1, r2);
    }

    /**
     * Linear interpolation.
     */
    public static double lerp(double x, double x1, double x2, double q00, double q01) {
        return ((x2 - x) / (x2 - x1)) * q00 + ((x - x1) / (x2 - x1)) * q01;
    }

    public static double lerp(double x1, double x2, double p) {
        return x1 * (1.0 - p) + x2 * p;
    }

    public static float lerpf(float x1, float x2, float p) {
        return x1 * (1.0f - p) + x2 * p;
    }

    /**
     * Trilinear interpolation.
     */
    public static double triLerp(double x, double y, double z, double q000, double q001, double q010, double q011, double q100, double q101, double q110, double q111, double x1, double x2, double y1, double y2, double z1, double z2) {
        double x00 = lerp(x, x1, x2, q000, q100);
        double x10 = lerp(x, x1, x2, q010, q110);
        double x01 = lerp(x, x1, x2, q001, q101);
        double x11 = lerp(x, x1, x2, q011, q111);
        double r0 = lerp(y, y1, y2, x00, x01);
        double r1 = lerp(y, y1, y2, x10, x11);
        return lerp(z, z1, z2, r0, r1);
    }

    /**
     * Maps any given value to be positive only.
     */
    public static int mapToPositive(int x) {
        if (x >= 0)
            return x * 2;

        return -x * 2 - 1;
    }

    /**
     * Recreates the original value after applying "mapToPositive".
     */
    public static int redoMapToPositive(int x) {
        if (x % 2 == 0) {
            return x / 2;
        }

        return -(x / 2) - 1;
    }

    /**
     * Applies Cantor's pairing function to 2D coordinates.
     *
     * @param k1 X-coordinate
     * @param k2 Y-coordinate
     * @return Unique 1D value
     */
    public static int cantorize(int k1, int k2) {
        return ((k1 + k2) * (k1 + k2 + 1) / 2) + k2;
    }

    /**
     * Inverse function of Cantor's pairing function.
     *
     * @param c Cantor value
     * @return Value along the x-axis
     */
    public static int cantorX(int c) {
        int j = (int) (java.lang.Math.sqrt(0.25 + 2 * c) - 0.5);
        return j - cantorY(c);
    }

    /**
     * Inverse function of Cantor's pairing function.
     *
     * @param c Cantor value
     * @return Value along the y-axis
     */
    public static int cantorY(int c) {
        int j = (int) (java.lang.Math.sqrt(0.25 + 2 * c) - 0.5);
        return c - j * (j + 1) / 2;
    }

    /**
     * Returns the chunk position of a given coordinate.
     *
     * @param x The X-coordinate of the block
     * @return The X-coordinate of the chunk
     */
    public static int calcChunkPosX(int x, int chunkPowerX) {
        return (x >> chunkPowerX);
    }

    public static int calcChunkPosX(int x) {
        return calcChunkPosX(x, Chunk.POWER_X);
    }

    /**
     * Returns the chunk position of a given coordinate
     *
     * @param y
     * @return The Y-coordinate of the chunk
     */
    public static int calcChunkPosY(int y) {
        // If we ever have multiple vertical chunks, change this
        return 0;
    }

    /**
     * Returns the chunk position of a given coordinate.
     *
     * @param z The Z-coordinate of the block
     * @return The Z-coordinate of the chunk
     */
    public static int calcChunkPosZ(int z, int chunkPowerZ) {
        return (z >> chunkPowerZ);
    }

    public static int calcChunkPosZ(int z) {
        return calcChunkPosZ(z, Chunk.POWER_Z);
    }

    public static Vector3i calcChunkPos(Vector3i pos, Vector3i chunkPower) {
        return calcChunkPos(pos.x, pos.y, pos.z, chunkPower);
    }

    public static Vector3i calcChunkPos(Vector3i pos) {
        return calcChunkPos(pos.x, pos.y, pos.z);
    }

    public static Vector3i calcChunkPos(int x, int y, int z) {
        return calcChunkPos(x, y, z, Chunk.CHUNK_POWER);
    }

    public static Vector3i calcChunkPos(int x, int y, int z, Vector3i chunkPower) {
        return new Vector3i(calcChunkPosX(x, chunkPower.x), calcChunkPosY(y), calcChunkPosZ(z, chunkPower.z));
    }

    /**
     * Returns the internal position of a block within a chunk.
     *
     * @param blockX The X-coordinate of the block in the world
     * @return The X-coordinate of the block within the chunk
     */
    public static int calcBlockPosX(int blockX, int chunkPosFilterX) {
        return blockX & chunkPosFilterX;
    }


    public static int calcBlockPosX(int blockX) {
        return calcBlockPosX(blockX, Chunk.INNER_CHUNK_POS_FILTER_X);
    }

    public static int calcBlockPosY(int blockY) {
        return blockY;
    }

    /**
     * Returns the internal position of a block within a chunk.
     *
     * @param blockZ The Z-coordinate of the block in the world
     * @return The Z-coordinate of the block within the chunk
     */
    public static int calcBlockPosZ(int blockZ, int chunkPosFilterZ) {
        return blockZ & chunkPosFilterZ;
    }

    public static int calcBlockPosZ(int blockZ) {
        return calcBlockPosZ(blockZ, Chunk.INNER_CHUNK_POS_FILTER_Z);
    }

    public static Vector3i calcBlockPos(int x, int y, int z) {
        return calcBlockPos(x,y,z, Chunk.INNER_CHUNK_POS_FILTER);
    }

    public static Vector3i calcBlockPos(int x, int y, int z, Vector3i chunkFilterSize) {
        return new Vector3i(calcBlockPosX(x, chunkFilterSize.x), calcBlockPosY(y), calcBlockPosZ(z, chunkFilterSize.z));
    }

    public static Region3i getChunkRegionAroundBlockPos(Vector3i pos, int extent) {
        Vector3i minPos = new Vector3i(-extent, 0, -extent);
        minPos.add(pos);
        Vector3i maxPos = new Vector3i(extent, 0, extent);
        maxPos.add(pos);

        Vector3i minChunk = TeraMath.calcChunkPos(minPos);
        Vector3i maxChunk = TeraMath.calcChunkPos(maxPos);

        return Region3i.createFromMinMax(minChunk, maxChunk);
    }

    /**
     * Lowest power of two greater or equal to val
     * <p/>
     * For values &lt;= 0 returns 0
     *
     * @param val
     * @return The lowest power of two greater or equal to val
     */
    public static int ceilPowerOfTwo(int val) {
        val--;
        val = (val >> 1) | val;
        val = (val >> 2) | val;
        val = (val >> 4) | val;
        val = (val >> 8) | val;
        val = (val >> 16) | val;
        val++;
        return val;
    }

    /**
     * @param val
     * @return The size of a power of two - that is, the exponent.
     */
    public static int sizeOfPower(int val) {
        int power = 0;
        while (val > 1) {
            val = val >> 1;
            power++;
        }
        return power;
    }

    public static int floorToInt(float val) {
        int i = (int) val;
        return (val < 0 && val != i) ? i - 1 : i;
    }

    public static int ceilToInt(float val) {
        int i = (int) val;
        return (val >= 0 && val != i) ? i + 1 : i;
    }
}
