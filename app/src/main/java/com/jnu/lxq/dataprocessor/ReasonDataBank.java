package com.jnu.lxq.dataprocessor;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ReasonDataBank {
    private ArrayList<String> reasonList = new ArrayList<>();
    private final Context context;
    private final String REASON_FILE_NAME="reasons.txt";
    public ReasonDataBank(Context context)
    {
        this.context=context;
    }

    public ArrayList<String> getReasonList() {
        return reasonList;
    }
    public void Save()
    {
        ObjectOutputStream oos = null;
        try {

            oos = new ObjectOutputStream(context.openFileOutput(REASON_FILE_NAME,Context.MODE_PRIVATE));
            oos.writeObject(reasonList);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Load()
    {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(context.openFileInput(REASON_FILE_NAME));
            reasonList = (ArrayList<String>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
