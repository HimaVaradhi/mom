package in.amruthashala.momapp.common;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import android.util.Log;


import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FilePath {

    /**
     * Method for return file path of Gallery image
     *
     * @param context
     * @param uri
     * @return path of the selected image file from gallery
     */

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        try {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + split[1];
                    } else {
                        final int splitIndex = docId.indexOf(':', 1);
                        final String tag = docId.substring(0, splitIndex);
                        final String path = docId.substring(splitIndex + 1);

                        String nonPrimaryVolume = getPathToNonPrimaryVolume(context, tag);
                        if (nonPrimaryVolume != null) {
                            String result = nonPrimaryVolume + "/" + path;
                            File file = new File(result);
                            if (file.exists() && file.canRead()) {
                                return result;
                            }
                        }
                    }
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    /*final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);*/

                    final String id = DocumentsContract.getDocumentId(uri);

                    if (id != null && id.startsWith("raw:")) {
                        return id.substring(4);
                    }

                    String[] contentUriPrefixesToTry = new String[]{
                            "content://downloads/public_downloads",
                            "content://downloads/my_downloads",
                            "content://downloads/all_downloads"
                    };

                    for (String contentUriPrefix : contentUriPrefixesToTry) {
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
                        try {
                            String path = getDataColumn(context, contentUri, null, null);
                            if (path != null) {
                                return path;
                            }
                        } catch (Exception e) {

                        }
                    }

                    // path could not be retrieved using ContentResolver, therefore copy file to accessible cache using streams
                    String fileName = FileUtils.getFileName(context, uri);
                    File cacheDir = FileUtils.getDocumentCacheDir(context);
                    File file = FileUtils.generateFileName(fileName, cacheDir);
                    String destinationPath = null;
                    if (file != null) {
                        destinationPath = file.getAbsolutePath();
                        FileUtils.saveFileFromUri(context, uri, destinationPath);
                    }

                    return destinationPath;
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};

                    return getDataColumn(context, contentUri, selection,
                            selectionArgs);
                } else {
                    String filePath = uri.getPath();

                    String name = new File(uri.getPath()).getName();
                    File file = new File(context.getCacheDir(), name);

                    int maxBufferSize = 1 * 1024 * 1024;

                    try {
                        InputStream inputStream = context.getContentResolver().openInputStream(uri);
                        Log.e("InputStream Size", "Size " + inputStream);
                        int bytesAvailable = inputStream.available();
//                    int bufferSize = 1024;
                        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        final byte[] buffers = new byte[bufferSize];

                        FileOutputStream outputStream = new FileOutputStream(file);
                        int read = 0;
                        while ((read = inputStream.read(buffers)) != -1) {
                            outputStream.write(buffers, 0, read);
                        }
                        Log.e("File Size", "Size " + file.length());
                        inputStream.close();
                        outputStream.close();

                        return file.getPath();
                    } catch (Exception e) {

                    }
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();
                if (getDataColumn(context, uri, null, null) != null)
                    return getDataColumn(context, uri, null, null);
                else return noFile(uri, context);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            } else {
                String filePath = uri.getPath();

                String name = new File(uri.getPath()).getName();
                File file = new File(context.getCacheDir(), name);

                int maxBufferSize = 1 * 1024 * 1024;

                try {
                    InputStream inputStream = context.getContentResolver().openInputStream(uri);
                    Log.e("InputStream Size", "Size " + inputStream);
                    int bytesAvailable = inputStream.available();
//                    int bufferSize = 1024;
                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    final byte[] buffers = new byte[bufferSize];

                    FileOutputStream outputStream = new FileOutputStream(file);
                    int read = 0;
                    while ((read = inputStream.read(buffers)) != -1) {
                        outputStream.write(buffers, 0, read);
                    }
                    Log.e("File Size", "Size " + file.length());
                    inputStream.close();
                    outputStream.close();

                    return file.getPath();
                } catch (Exception e) {

                }
            }

        } catch (IllegalArgumentException e1) {
            String filePath = uri.getPath();

            String name = new File(uri.getPath()).getName();
            File file = new File(context.getCacheDir(), name);

            int maxBufferSize = 1 * 1024 * 1024;

            try {
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                Log.e("InputStream Size", "Size " + inputStream);
                int bytesAvailable = inputStream.available();
//                    int bufferSize = 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                final byte[] buffers = new byte[bufferSize];

                FileOutputStream outputStream = new FileOutputStream(file);
                int read = 0;
                while ((read = inputStream.read(buffers)) != -1) {
                    outputStream.write(buffers, 0, read);
                }
                Log.e("File Size", "Size " + file.length());
                inputStream.close();
                outputStream.close();

                return file.getPath();
            } catch (Exception e) {

            }
        } catch (Exception e) {

        }
        return null;
    }

    /*code under test ..................................................*/
    public static String getAbsolutePath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= 19) {
            String id = uri.getLastPathSegment();
            final String[] imageColumns = {MediaStore.Images.Media.DATA};
            final String imageOrderBy = null;
            Uri tempUri = uri;
            Cursor imageCursor = context.getContentResolver().query(tempUri, imageColumns,
                    MediaStore.Images.Media._ID + "=" + id, null, imageOrderBy);
            if (imageCursor.moveToFirst()) {
                return imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            } else {
                return null;
            }
        } else {
            String[] projection = {MediaStore.MediaColumns.DATA};

            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } else
                return null;
        }

    }

    public static String noFile(Uri uri, Context context) {
        String filePath = uri.getPath();

        String name = new File(uri.getPath()).getName();
        File file = new File(context.getCacheDir(), name);

        int maxBufferSize = 1 * 1024 * 1024;

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            Log.e("InputStream Size", "Size " + inputStream);
            int bytesAvailable = inputStream.available();
//                    int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            final byte[] buffers = new byte[bufferSize];

            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();

            return file.getPath();
        } catch (Exception e) {

        }
        return "";

    }
    /*code under test ..................................................*/


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public static String getPathToNonPrimaryVolume(Context context, String tag) {
        try {
            File[] volumes = context.getExternalCacheDirs();
            if (volumes != null) {
                for (File volume : volumes) {
                    if (volume != null) {
                        String path = volume.getAbsolutePath();
                        if (path != null) {
                            int index = path.indexOf(tag);
                            if (index != -1) {
                                return path.substring(0, index) + tag;
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {

        }
        return null;
    }
}


