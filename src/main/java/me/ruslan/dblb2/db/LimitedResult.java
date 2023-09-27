package me.ruslan.dblb2.db;

import java.util.ArrayList;

public class LimitedResult<T> {
    public ArrayList<T> objects;
    public int count;

    public LimitedResult(ArrayList<T> objects, int count) {
        this.objects = objects;
        this.count = count;
    }
}