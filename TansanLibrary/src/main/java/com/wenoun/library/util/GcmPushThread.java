package com.wenoun.library.util;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by jeyhoon on 16. 2. 17..
 */
public class GcmPushThread extends Thread {
    private OnGcmResultListener mListener;
    private String apiKey="";
    private String token="";
    private ArrayList<GcmData> dataList=new ArrayList<GcmData>();
    private final String OPEN="\"";
    private final String MIDDLE="\":\"";
    private final String CLOSE="\",";
    public static final int RESULT_OK=0;
    public static final int RESULT_FALIED=1;
    public interface OnGcmResultListener{
        public void OnFinish(GcmPushThread thread,int resultCode,String result);
    }
    public class GcmData{
        private String key="";
        private String value="";
        public GcmData(String key, String value){
            this.key=key;
            this.value=value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public GcmPushThread(String apiKey, String token, @NonNull ArrayList<GcmData> dataList, OnGcmResultListener mListener){
        this.apiKey=apiKey;
        this.token=token;
        this.dataList=dataList;
        this.mListener=mListener;
    }
    public GcmPushThread(String apiKey, String token, OnGcmResultListener mListener){
        this.apiKey=apiKey;
        this.token=token;
        this.dataList=dataList;
        this.mListener=mListener;
    }
    public GcmPushThread addGcmData(String key, String $value){
        String value=$value;
        try{
            value=URLEncoder.encode($value,"UTF-8");
        }catch(Exception e){
            value=$value;
        }
        dataList.add(new GcmData(key,value));
        return this;
    }
    private String getDataString(){
        String result="";
        for(GcmData data : dataList){
            result=result+OPEN+data.getKey()+MIDDLE+data.getValue()+CLOSE;
        }
        return result;
    }
    /**
     * Calls the <code>run()</code> method of the Runnable object the receiver
     * holds. If no Runnable is set, does nothing.
     *
     * @see Thread#start
     */
    @Override
    public void run() {
        HttpURLConnection conn=null;
        DataOutputStream dos=null;
        try {
            URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization","key="+apiKey);
            conn.setRequestProperty("Content-Type","application/json");
//                    Log.d("Test",conn.getContentEncoding());
//                    conn.setRequestProperty("Content-Type","application/json");
//                    conn.setRequestProperty("Connection", "Keep-Alive");
//                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");

            dos=new DataOutputStream(conn.getOutputStream());
            dos.writeBytes("{" +
                    "  \"to\" : \""+token+"\"," +
                    "  \"data\" : {" +
                    getDataString() +
                    "  }," +
                    "}");
//                    dos.writeBytes("curl --header \"Authorization: key=AIzaSyCYaFucnm-1_5i6u6A8WswGGWgqm5t4NDE\"" +
//                            "--header Content-Type:\"application/json\"" +
//                            "https://gcm-http.googleapis.com/gcm/send " +
//                            "-d \"{\\\"data\\\":{\\\"title\\\":\\\"saltfactory GCM demo\\\",\\\"message\\\":\\\"Google Cloud Messaging 테스트\\\"},\\\"to\\\":\\\"cIo9b03GiXQ:APA91bGFuoevfMvVg9ufiSbmUSSmwaOenCW9mLBGwdprPo-E1nKoUBVXLbMprqTR48Krr8t6RwyOMem7fJuuJsfDtB1M8OZZeLiWoFAyjiUY6G6EkuHoWAPO91dB9vflhH2lIqFXr554\\\"}\"");
            int serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();
//                    conn.
            Log.i("uploadFile Test", "HTTP Response is : "
                    + serverResponseMessage + ": " + serverResponseCode);
            if(serverResponseCode == 200){
//                        BufferedReader br = new BufferedReader( new OutputStreamReader( conn.getInputStream(), "EUC-KR" ), conn.getContentLength() );
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String buf;
                String res="";
                while( ( buf = br.readLine() ) != null ) {
                    res=res+buf+"\n";
                }
                br.close();
                if(res.length()>0&&!res.equals("")) {
                    JSONObject obj = new JSONObject(res);
                    if(obj.getInt("success")==1){
                        mListener.OnFinish(this,RESULT_OK,res);
                    }else{
                        mListener.OnFinish(this,RESULT_FALIED,res);
                    }
                }else{
                    mListener.OnFinish(this,RESULT_FALIED,"Not Result");
                }

            }else{
                mListener.OnFinish(this,RESULT_FALIED,serverResponseMessage + ": " + serverResponseCode);
            }
        }catch(Exception e){
            e.printStackTrace();
            mListener.OnFinish(this,RESULT_OK,e.getMessage());
        }finally {
            try{
                if(null!=conn)
                    conn.disconnect();
                if(null!=dos)
                    dos.close();
            }catch(Exception e){e.printStackTrace();}
        }
    }
}
