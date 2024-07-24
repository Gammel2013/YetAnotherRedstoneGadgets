package com.gammel2012.utils;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.*;

public class SelectorIntegerProperty extends IntegerProperty {

    private Collection<Integer> values;
    private int min;
    private int max;

    protected SelectorIntegerProperty(String pName, int pMin, int pMax, int... pValues) {
        super(pName, pMin, pMax);

        List<Integer> list = new ArrayList<>();
        for (int i : pValues) {
            list.add(i);
        }

        this.values = ImmutableSet.copyOf(list);
        this.min = pMin;
        this.max = pMax;
    }

    public static SelectorIntegerProperty create(String pName, int... pValues) {

        OptionalInt pMin = Arrays.stream(pValues).min();
        OptionalInt pMax = Arrays.stream(pValues).max();

        if (pMin.isEmpty() || pMax.isEmpty()) {
            throw new RuntimeException("Failing to create SelectorIntegerProperty for values " + Arrays.toString(pValues));
        }

        return new SelectorIntegerProperty(pName, pMin.getAsInt(), pMax.getAsInt(), pValues);
    }

    @Override
    public Collection<Integer> getPossibleValues() {
        return this.values;
    }

    @Override
    public Optional<Integer> getValue(String pValue) {
        try {
            Integer integer = Integer.valueOf(pValue);
            return this.values.contains(integer) ? Optional.of(integer) : Optional.empty();
        } catch (NumberFormatException numberformatexception) {
            return Optional.empty();
        }
    }

    @Override
    public boolean equals(Object pOther) {
        if (this == pOther) {
            return true;
        } else {
            if (pOther instanceof SelectorIntegerProperty integerproperty && super.equals(pOther)) {
                return this.values.equals(integerproperty.values);
            }

            return false;
        }
    }
}
