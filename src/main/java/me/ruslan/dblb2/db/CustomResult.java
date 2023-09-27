package me.ruslan.dblb2.db;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomResult {
    public ArrayList<String> columns;
    public ArrayList<HashMap<String, String>> rows;
    public int affected;

    public CustomResult(ArrayList<String> columns, ArrayList<HashMap<String, String>> rows, int affected) {
        this.columns = columns;
        this.rows = rows;
        this.affected = affected;
    }
}