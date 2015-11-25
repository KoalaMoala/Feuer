package ca.uqac.keepitcool.myfirstgame;

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Progress implements Serializable {

    private static final long serialVersionUID = 1L;
    private static volatile Progress INSTANCE = null;
    private List<Level> levels;

    private Progress() {
        this.levels = new ArrayList<Level>();
    }

    public static synchronized Progress getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new Progress();
        }
        return INSTANCE;
    }

    public static synchronized Progress getInstance(String currentProgress) {
        if (null == INSTANCE) {
            INSTANCE = new Progress();
        }
        if(!currentProgress.isEmpty()) {
            INSTANCE.buildProgressFromString(currentProgress);
        } else {
            Level l = new Level(1, "Niveau 1", "Un niveau parmi tant d'autres");
            INSTANCE.addLevel(l);
        }
        return INSTANCE;
    }

    private synchronized void addLevel(Level level) {
        this.levels.add(level);
    }

    private synchronized Level getLevel(int index) {
        return this.levels.get(index);
    }

    public synchronized String getDescriptionFromCurrentLevel() {
        return this.levels.get(this.levels.size() - 1).getDescription();
    }

    public String generateStringFromProgress() {
        return this.saveAsString();
    }

    private synchronized String saveAsString() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(new Base64OutputStream(baos, Base64.NO_PADDING|Base64.NO_WRAP));
            oos.writeObject(INSTANCE);
            oos.close();
            return baos.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private synchronized void buildProgressFromString(String str) {
        try {
            INSTANCE = (Progress) new ObjectInputStream(new Base64InputStream(new ByteArrayInputStream(str.getBytes()), Base64.NO_PADDING|Base64.NO_WRAP)).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
