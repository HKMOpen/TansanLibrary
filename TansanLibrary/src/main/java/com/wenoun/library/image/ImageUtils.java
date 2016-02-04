package com.wenoun.library.image;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.wenoun.library.util.DocumentsContract;

//import android.provider.DocumentsContract;

/**
 * Created by SnakeJey on 2015-08-19.
 */
public class ImageUtils {
    /**
     *
     * @param res getResources()
     * @param resId Resources ID
     * @param reqWidth Request Width
     * @param reqHeight Request Height
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     *
     * @param imgPath ImagePath, String
     * @param reqWidth Request Width
     * @param reqHeight Request Height
     * @return
     */
    public static Bitmap decodeSampledBitmapFromPath(String imgPath,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imgPath, options);
    }

    /**
     *
     * @param imgPath
     * @param reqWidth
     * @return
     */
    public static Bitmap decodeSampledBitmapFromPath(String imgPath,
                                                     int reqWidth) {

        // First decode with inJustDecodeBounds=true to check dimensions

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);
        float ccc=(float)options.outWidth/(float)reqWidth;
        float _height=(float)options.outHeight/ccc;
        //Log.d("ChatDataAdapter, Img","w : "+width+" h : "+height+" ccc : "+ccc+" _height : "+_height);
        int reqHeight=(int)_height;
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imgPath, options);
    }

    /**
     *
     * @param ctx Context
     * @param res getResources()
     * @param resId Resource Id
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Context ctx,Resources res, int resId) {
        Display display = ((WindowManager) ctx.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay();
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, displayWidth, displayHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     *
     * @param ctx Context
     * @param imgPath ImagePath
     * @return
     */
    public static Bitmap decodeSampledBitmapFromPath(Context ctx,String imgPath) {
        Display display = ((WindowManager) ctx.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay();
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, displayWidth, displayHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imgPath, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int getDisplayWidth(Context ctx){
        Display display = ((WindowManager) ctx.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getWidth();

    }
    public static int getDisplayHeight(Context ctx){
        Display display = ((WindowManager) ctx.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getHeight();

    }
    public static class UploadImageThread extends Thread{
        public OnUploadResultListener onUploadResultListener = null;

        public interface OnUploadResultListener {
            void OnSuccess(Thread thread);
            void OnFail(Thread thread, String e);
        }
        public static final int RESULT_IS_NOT_FILE=0;
        public static final int RESULT_OK=1;
        public static final int RESULT_FAILED=2;
        private Handler handler=new Handler(){
            /**
             * Subclasses must implement this to receive messages.
             *
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RESULT_IS_NOT_FILE:
                        onUploadResultListener.OnFail(UploadImageThread.this, "File is not exists");
                        break;
                    case RESULT_OK:
                        onUploadResultListener.OnSuccess(UploadImageThread.this);
                        break;
                    case RESULT_FAILED:
                        onUploadResultListener.OnFail(UploadImageThread.this, (String) msg.obj);
                        break;
                    default:
                        break;
                }
            }
        };
        private String sourceFileUri="";
        private String upLoadServerUri="";
        public UploadImageThread(Handler handler,String sourceFileUri,String upLoadServerUri){
            this.handler=handler;
            this.sourceFileUri=sourceFileUri;
            this.upLoadServerUri=upLoadServerUri;
        }
        public UploadImageThread(OnUploadResultListener onUploadResultListener,String sourceFileUri,String upLoadServerUri){
            this.onUploadResultListener=onUploadResultListener;
            this.sourceFileUri=sourceFileUri;
            this.upLoadServerUri=upLoadServerUri;
        }

        /**
         * Calls the <code>run()</code> method of the Runnable object the receiver
         * holds. If no Runnable is set, does nothing.
         *
         * @see Thread#start
         */
        @Override
        public void run() {
            int serverResponseCode=-1;
            String fileName = sourceFileUri;

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);
            Log.i("uploadFile", "sourceFileUri : " + sourceFileUri);
            if (!sourceFile.isFile()) {
                Message msg = new Message();
                msg.what = RESULT_IS_NOT_FILE; //실패
                handler.sendMessage(msg);
                return;

            }
            else
            {
                try {

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(
                            sourceFile);
                    URL url = new URL(upLoadServerUri);
                    Log.d("uploadImg, Test",upLoadServerUri);
                    // Open a HTTP connection to the URLl
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + fileName + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();
//                    conn.
                    Log.i("uploadFile Test", "HTTP Response is : "
                            + serverResponseMessage + ": " + serverResponseCode);

                    if(serverResponseCode == 200){
//                        BufferedReader br = new BufferedReader( new OutputStreamReader( conn.getInputStream(), "EUC-KR" ), conn.getContentLength() );
                        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String buf;

// 표준출력으로 한 라인씩 출력

                        while( ( buf = br.readLine() ) != null ) {
                            Log.d("uploadFile Test",buf);
//                            System.out.println( buf );

                        }

// 스트림을 닫는다.

                        br.close();
                        Message msg = new Message();
                        msg.what = RESULT_OK; //실패
                        handler.sendMessage(msg);
                    }

                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {
                    Message msg = new Message();
                    msg.what = RESULT_FAILED; //실패
                    msg.obj=ex.getMessage();
                    handler.sendMessage(msg);

                    Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                    return;
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = RESULT_FAILED; //실패
                    msg.obj=e.getMessage();
                    handler.sendMessage(msg);
                    Log.e("Upload server Exception", "Exception : "
                            + e.getMessage(), e);
                    return;
                }

            } // End else block
        }
    }
    public static String getPath(Context ctx,final Uri uri) {

        // TEST
        // HTC:
        // content://com.android.providers.media.documents/document/image:.....
        // GalaxyS4 : content://media/external/images/media/....

        // * 기존에 갤러리에서 호출했던 방식.
        // Cursor c =
        // mActivity.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI,
        // null,
        // Images.Media._ID + "=?",
        // new String[] { uri.getLastPathSegment() } ,
        // null);
        // @@@@@@
        // content://com.android.providers.media.documents/document/image%....
        // getAuthority = "com.android.providers.media.documents"
        //
        // content://media/external/images/media/....
        // getAuthority = "media"
        //

        final boolean isAndroidVersionKitKat = Build.VERSION.SDK_INT >= 19; // (
        // ==
        // Build.VERSION_CODE.KITKAT
        // )

        // 1. 안드로이드 버전 체크
        // com.android.providers.media.documents/document/image :: uri로 전달 받는
        // 경로가 킷캣으로 업데이트 되면서 변경 됨.
        if (isAndroidVersionKitKat && DocumentsContract.isDocumentUri(uri)) {

            // com.android.providers.media.documents/document/image:1234 ...
            //
            if (isMediaDocument(uri) && DocumentsContract.isDocumentUri(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;

                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                } else if ("video".equals(type)) {
                    return null; // 필자는 이미지만 처리할 예정이므로 비디오 및 오디오를 불러오는 부분은 작성하지
                    // 않음.

                } else if ("audio".equals(type)) {
                    return null;
                }

                final String selection = MediaStore.Images.Media._ID + "=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(ctx.getApplicationContext(), contentUri,
                        selection, selectionArgs);
            }

        }

        // content://media/external/images/media/....
        // 안드로이드 버전에 관계없이 경로가 com.android... 형식으로 집히지 않을 수 도 있음. [ 겔럭시S4 테스트 확인
        // ]
        if (isPathSDCardType(uri)) {

            final String selection = MediaStore.Images.Media._ID + "=?";
            final String[] selectionArgs = new String[] { uri
                    .getLastPathSegment() };

            return getDataColumn(ctx.getApplicationContext(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection,
                    selectionArgs);
        }

        // File 접근일 경우
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    // URI 를 받아서 Column 데이터 접근.
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {

            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    // 킷캣에서 추가된 document식 Path
    public static boolean isMediaDocument(Uri uri) {

        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    // 기본 경로 ( 킷캣 이전버전 )
    public static boolean isPathSDCardType(Uri uri) {
        // Path : external/images/media/ID(1234...)
        Log.d("Profile", uri.getPath());
        return "external".equals(uri.getPathSegments().get(0));
    }

    public static String getRealPathFromUri(Activity activity, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.managedQuery(contentUri, proj, null, null,
                null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
