package ca.uqac.keepitcool.myfirstgame;

import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Progress implements Serializable {

    private List<Level> levels = new ArrayList<Level>();
    private Progress() {}

    /** Instance unique pré-initialisée */
    private static Progress INSTANCE = new Progress();

    /** Point d'accès pour l'instance unique du singleton */
    public static Progress getInstance() {	return INSTANCE; }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public Level getLevel(int index) {
        return this.levels.get(index);
    }

    public String toString() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(
                    new Base64OutputStream(baos, Base64.NO_PADDING
                            | Base64.NO_WRAP));
            oos.writeObject(INSTANCE);
            oos.close();
            return baos.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void fromString(String str) {
        try {
            INSTANCE = (Progress) new ObjectInputStream(new Base64InputStream(
                    new ByteArrayInputStream(str.getBytes()), Base64.NO_PADDING
                    | Base64.NO_WRAP)).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
