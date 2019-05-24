package br.com.cadim.cadim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManageFile {
    private static String nowDate() {
        @SuppressLint("SimpleDateFormat")

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
        Date now = new Date();

        return dateFormat.format(now);
    }

    private static String getDirectory() {

        File root = new File(Environment
                .getExternalStorageDirectory()
                + "/Arida/MobileECG/");

        if (!root.exists()) root.mkdirs();

        return root.toString();
    }

    public static void saveSignal(ArrayList<Integer> signalEcg) {
        File file, dir;
        FileOutputStream fos;

        String fileName = "ECG-" + nowDate() + ".txt";

        byte[] data;
        String signal;

        try {
            dir = new File(getDirectory());
            file = new File(dir, fileName);

            int fileLines;

            for (fileLines = 0; fileLines < signalEcg.size() - 1; fileLines++) {

                signal = signalEcg.get(fileLines) + " ";
                data = signal.getBytes();

                fos = new FileOutputStream(file, true);
                fos.write(data);
                fos.flush();
                fos.close();
            }

        } catch (FileNotFoundException e) {
            message(null, "Erro FNFE: " + e.getMessage());

        } catch (IOException e) {
            message(null,"Erro IOE: " + e.getMessage());
        }
    }

    public static void message(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
