package com.waoss.enesys.mem;

import javafx.beans.property.SimpleObjectProperty;

public class CompleteMemory {

    SimpleObjectProperty<RandomAccessMemory> randomAccessMemoryProperty = new SimpleObjectProperty<>(new RandomAccessMemory());

    public RandomAccessMemory getRandomAccessMemory() {
        return randomAccessMemoryProperty.get();
    }

    public void setRandomAccessMemory(RandomAccessMemory randomAccessMemory) {
        this.randomAccessMemoryProperty.set(randomAccessMemory);
    }

    public SimpleObjectProperty<RandomAccessMemory> randomAccessMemoryProperty() {
        return randomAccessMemoryProperty;
    }
}
