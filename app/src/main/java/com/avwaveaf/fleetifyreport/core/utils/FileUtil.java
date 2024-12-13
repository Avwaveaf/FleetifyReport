package com.avwaveaf.fleetifyreport.core.utils;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUtil {
    public static RequestBody textToBody(String s) {
        return RequestBody.create(s, MediaType.parse("text/plain"));
    }

    public static MultipartBody.Part fileToBody(File file) {
        RequestBody img = RequestBody.create(file, MediaType.parse("image/jpeg"));
        return MultipartBody.Part.createFormData("photo", file.getName(), img);
    }

    public static File uriToFile(Uri imageUri, Context context) throws IOException {
        File myFile = createCustomTempFile(context);
        try (InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
             FileOutputStream outputStream = new FileOutputStream(myFile)) {
            byte[] buffer = new byte[1024];
            int length;
            if (inputStream != null) {
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
        }
        return myFile;
    }

    private static File createCustomTempFile(Context context) throws IOException {
        String fileName = "temp_file_" + System.currentTimeMillis();
        File storageDir = context.getCacheDir();
        return File.createTempFile(fileName, ".jpg", storageDir);
    }

}
