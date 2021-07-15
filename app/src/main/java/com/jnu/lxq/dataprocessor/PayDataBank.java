package com.jnu.lxq.dataprocessor;


import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PayDataBank {
    private ArrayList<Pay> arrayListPays=new ArrayList<>();
    private final Context context;
    private final String PAY_FILE_NAME="pays.txt";

    public PayDataBank(Context context)
    {
        this.context=context;
    }

    public ArrayList<Pay> getPays() {
        return arrayListPays;
    }

    public void Save()
    {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(context.openFileOutput(PAY_FILE_NAME,Context.MODE_PRIVATE));
            oos.writeObject(arrayListPays);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Load()
    {
        ObjectInputStream ois = null;
//        arrayListPays=new ArrayList<>();
        try {
            ois = new ObjectInputStream(context.openFileInput(PAY_FILE_NAME));
            arrayListPays = (ArrayList<Pay>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
