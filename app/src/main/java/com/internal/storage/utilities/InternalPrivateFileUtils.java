package com.internal.storage.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import androidx.annotation.NonNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InternalPrivateFileUtils {

    public static final String TAG = InternalPrivateFileUtils.class.getSimpleName();

    private InternalPrivateFileUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /**
     * Create file and write file, If file already exist then direct write file
     *
     * @param context
     * @param fileName
     * @return create file
     */
    public static File createAndWriteFile(@NonNull Context context, String fileName, String fileExtension, String stringData) throws IOException{

        File directoryWhereCreateFile   = context.getFilesDir();
        File createdFile                = new File(directoryWhereCreateFile, fileName+"."+fileExtension);

        if (!createdFile.exists())
        {
            if (!createdFile.createNewFile())
            {
                Log.e(TAG, "Oops! Create File Failed....");
            }
            else
            {
                Log.e(TAG, "File Create Successful....");
            }
        }
        else
        {
            Log.e(TAG, "File Already Exit....");
        }

        FileOutputStream fileOutputStream = null;

        try
        {
            fileOutputStream = new FileOutputStream(createdFile);

            byte[] bytes = stringData.getBytes();
            fileOutputStream.write(bytes);
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            fileNotFoundException.printStackTrace();
        }
        catch (IOException iOException)
        {
            iOException.printStackTrace();
        }
        finally
        {
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            }
        }

        return createdFile;
    }

    /**
     * Read file
     *
     * @param context
     * @param fileName
     * @param fileExtension
     * @return string data
     */
    public static String readFile(@NonNull Context context, String fileName, String fileExtension) {

        File directoryWhereCreateFile   = context.getFilesDir();
        File createdFile                = new File(directoryWhereCreateFile, fileName+"."+fileExtension);

        if (!createdFile.exists())
        {
            Log.e(TAG, "File Does Not Exists....");
            return null;
        }
        else
        {
            Log.e(TAG, "File Exists....");
        }

        int fileLength= (int) createdFile.length();
        byte[] bytes = new byte[fileLength];

        FileInputStream fileInputStream = null;
        String contents = null;

        try
        {
            fileInputStream  = new FileInputStream(createdFile);
            fileInputStream.read(bytes); /* read data and write into bytes array*/

            contents = new String(bytes);
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            fileNotFoundException.printStackTrace();
        }
        catch (IOException iOException)
        {
            iOException.printStackTrace();
        }
        finally
        {
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            }
        }
        return contents;
    }

    public static void createAndWriteFileUsingOpenFileOutput(@NonNull Context context, String fileName, String fileExtension, String stringData) {

        FileOutputStream fileOutputStream = null;

        try
        {
            fileOutputStream = context.openFileOutput(fileName+"."+fileExtension, Context.MODE_PRIVATE);

            byte[] bytes = stringData.getBytes();
            fileOutputStream.write(bytes);
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            fileNotFoundException.printStackTrace();
        }
        catch (IOException iOException)
        {
            iOException.printStackTrace();
        }
        finally
        {
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static String readFileUsingOpenFileInputOne(@NonNull Context context, String fileName, String fileExtension) {

        FileInputStream fileInputStream = null;
        StringBuilder stringBuffer      = new StringBuilder();
        String content                  = null;
        int read;

        try
        {
            fileInputStream = context.openFileInput(fileName+"."+fileExtension);

            while((read = fileInputStream.read())!= -1)
            {
                stringBuffer.append((char)read);
            }

            if (stringBuffer.length() > 0)
            {
                content = stringBuffer.toString();
            }
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            fileNotFoundException.printStackTrace();
        }
        catch (IOException iOException)
        {
            iOException.printStackTrace();
        }
        finally
        {
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            }
        }

        return content;
    }

    public static String readFileUsingOpenFileInputTwo(@NonNull Context context, String fileName, String fileExtension) {

        FileInputStream fileInputStream = null;
        StringBuilder stringBuffer      = new StringBuilder();
        String content = null;

        try
        {
            fileInputStream                     = context.openFileInput(fileName+"."+fileExtension);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader       = new BufferedReader(inputStreamReader);

            while ((content = bufferedReader.readLine()) != null) {
                stringBuffer.append(content).append("\n");
            }

            if (stringBuffer.length() > 0)
            {
                content = stringBuffer.toString();
            }
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            fileNotFoundException.printStackTrace();
        }
        catch (IOException iOException)
        {
            iOException.printStackTrace();
        }
        finally
        {
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            }
        }

        return content;
    }

    public static void saveImageBitmap(Context context, Bitmap bitmap, String name, String extension){
        name = name + "." + extension;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap loadImageBitmap(Context context, String name, String extension){
        name = name + "." + extension;
        FileInputStream fileInputStream = null;
        Bitmap bitmap = null;
        try {
            fileInputStream = context.openFileInput(name);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static void deleteImageBitmap(Context context, String name, String extension) {
        name = name + "." + extension;
        context.deleteFile(name);
    }

    public File createFileInCacheDirectory(@NonNull Context context, String fileName, String fileExtension) throws IOException {
        File directoryWhereCreateFile   = context.getFilesDir();
        File createdFile                = new File(directoryWhereCreateFile, fileName+"."+fileExtension);

        if (!createdFile.exists())
        {
            if (!createdFile.createNewFile())
            {
                Log.e(TAG, "Oops! Create File Failed....");
            }
            else
            {
                Log.e(TAG, "File Create Successful....");
            }
        }
        else
        {
            Log.e(TAG, "File Already Exit....");
        }

        return createdFile;
    }
}
