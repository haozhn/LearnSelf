package com.example.hao.learnself.date_2019_12_20;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.example.hao.learnself.R;

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

public class HotfixTestActivity extends Activity implements View.OnClickListener {
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotfix_test);

        findViewById(R.id.toast_btn).setOnClickListener(this);
        findViewById(R.id.patch_class_btn).setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toast_btn:
                Toast.makeText(this, new Bug().badMethod(), Toast.LENGTH_LONG).show();
                break;
            case R.id.patch_class_btn:
                patchClass();
                break;
        }
    }

    private void patchClass() {
        try {
            ClassLoader classLoader = getClassLoader();
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
            File file = new File(copyFile("/sdcard/patch_dex.jar", this));
            files.add(file);

            File optimizedDirectory = new File(getFilesDir().getAbsolutePath() + File.separator + "patch");
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

    private String copyFile(String patchPath, Context context) {
        String src = context.getFilesDir().getAbsolutePath() + File.separator + "patch_dex.jar";
        Toast.makeText(this, src, Toast.LENGTH_LONG).show();
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
