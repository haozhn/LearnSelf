package com.example.hao.learnself.date_2019_12_20;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassPatchUtil {
    public static final void patchClass(Context context) {
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class<?> superclass = classLoader.getClass().getSuperclass();
            Field baseDexPathList = superclass.getDeclaredField("pathList");
            baseDexPathList.setAccessible(true);
            Object pathList = baseDexPathList.get(classLoader);
            Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            Object[] dexElements = (Object[]) dexElementsField.get(pathList);
            Method makeDexElements = pathList.getClass().getDeclaredMethod("makePathElements", List.class, File.class, List.class);
            makeDexElements.setAccessible(true);

            ArrayList<File> files = new ArrayList<>();
            File file = new File(copyFile("/sdcard/patch_dex.jar", context));
            files.add(file);

            File optimizedDirectory = new File(context.getFilesDir().getAbsolutePath() + File.separator + "patch");
            if (!optimizedDirectory.exists()) {
                optimizedDirectory.mkdirs();
            }

            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            Object[] patchdexElements = (Object[]) makeDexElements.invoke(pathList, files, optimizedDirectory, suppressedExceptions);

            Object[] finalArray = (Object[]) Array.newInstance(dexElements.getClass().getComponentType(), dexElements.length + patchdexElements.length);
            System.arraycopy(patchdexElements, 0, finalArray, 0, dexElements.length);
            System.arraycopy(dexElements, 0, finalArray, patchdexElements.length, dexElements.length);
            dexElementsField.set(pathList, finalArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String copyFile(String patchPath, Context context) {
        String src = context.getFilesDir().getAbsolutePath() + File.separator + "patch_dex.jar";
        Toast.makeText(context, src, Toast.LENGTH_LONG).show();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File(patchPath));
            outputStream = new BufferedOutputStream(new FileOutputStream(src));
            byte[] temp = new byte[1024];
            int len;
            while ((len = (inputStream.read(temp))) != -1) {
                outputStream.write(temp, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return src;
    }
}
