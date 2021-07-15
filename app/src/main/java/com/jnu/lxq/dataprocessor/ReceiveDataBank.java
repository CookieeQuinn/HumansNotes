package com.jnu.lxq.dataprocessor;


import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ReceiveDataBank {
    private ArrayList<Receive> arrayListReceive = new ArrayList<>();
    private final Context context;
    private final String RECEIVE_FILE_NAME="receives.txt";

    public ReceiveDataBank(Context context)
    {
        this.context=context;
    }

    public ArrayList<Receive> getReceive() {
        return arrayListReceive;
    }

    public void Save()
    {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(context.openFileOutput(RECEIVE_FILE_NAME,Context.MODE_PRIVATE));
            oos.writeObject(arrayListReceive);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Load()
    {
        ObjectInputStream ois = null;
//        arrayListReceive=new ArrayList<>();
        try {
            ois = new ObjectInputStream(context.openFileInput(RECEIVE_FILE_NAME));
            arrayListReceive = (ArrayList<Receive>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
