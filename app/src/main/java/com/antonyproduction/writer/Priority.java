package com.antonyproduction.writer;

public enum Priority {
    HIGH,
    MEDIUM,
    LOW;
    public static Priority valueOf(int ordinal){  //возвращение объекта по его индексу
        for (Priority item: values()){
            if(item.ordinal() == ordinal){
                return item;
            }
        }
        throw new IllegalArgumentException();
    }

}
