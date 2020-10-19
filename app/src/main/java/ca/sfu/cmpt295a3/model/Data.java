package ca.sfu.cmpt295a3.model;

import java.util.ArrayList;

//Class to pass in SharedPreferences Data
public class Data {
    ArrayList<Integer> data = new ArrayList<>();

    private static Data instance;
    private Data() {

    }
    public static Data getInstance() {
        if(instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public void add(int value) {
        data.add(value);
    }

    public int get(int index) {
        return data.get(index);
    }

    public void add(int index, int value)
    {
        data.add(index,value);
    }

    public int size()
    {
        return data.size();
    }

}
