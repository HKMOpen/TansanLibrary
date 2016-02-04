package com.wenoun.library.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetMarketVersionThread extends Thread {

    /* TANSAN Dev.TEAM
     * 만든이 : JEY
     * http://www.wenoun.com
     *
     * 사용방법 :
     *
     * GetMarketVersionThread thread=new GetMarketVersionThread(
     * new OnGetMarketVersionListener(){
     * 	@Override
     * 	pubic void OnSuccess(Thread thread,String marketVersion){
     * 		//Todo Succeed Get version from market.
     * 	}
     * 	@Override
     * 	public void OnFail(Thread thread,String errorMsg){
     * 		//Todo Failed Get version from market.
     * 	}
     * },getPackageName());
     * thread.start();
     *
     * 지원 메소드
     * # setPackageName(String packageName) return : void  패키지명 설정자.
     * # getPackageName()  return : String  현재 설정되있는 패키지명을 반환하는 반환자.
     */
    public static final int GET_TYPE_SUCCESS = 0; //msg.what 성공
    public static final int GET_TYPE_FAILED = 1;    //msg.what 실패
    private String packageName;
    private final String URL = "https://play.google.com/store/apps/details?id=";
    private String url;
    private Handler handler = new Handler() {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_TYPE_SUCCESS:
                    onGetMarketVersionListener.OnSuccess(GetMarketVersionThread.this, (String) msg.obj);
                    break;
                case GET_TYPE_FAILED:
                    onGetMarketVersionListener.OnFail(GetMarketVersionThread.this, (String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };
    public OnGetMarketVersionListener onGetMarketVersionListener = null;

    public interface OnGetMarketVersionListener {
        void OnSuccess(Thread thread, String marketVersion);

        void OnFail(Thread thread, String e);
    }

    public GetMarketVersionThread(Handler handler, String packageName) {
        // TODO Auto-generated constructor stub
        this.handler = handler;
        this.packageName = packageName;
        url = URL + packageName;
    }

    public GetMarketVersionThread(OnGetMarketVersionListener onGetMarketVersionListener, String packageName) {
        // TODO Auto-generated constructor stub
        this.onGetMarketVersionListener = onGetMarketVersionListener;
        this.packageName = packageName;
        url = URL + packageName;
    }

    @Override
    public void run() {
        String result = "";
        StringBuilder html = new StringBuilder();
        HttpURLConnection conn = null;

        try {
            URL url = new URL(this.url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000);
            if (conn != null) {

                conn.setUseCaches(false);
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));

                    for (; ; ) {
                        String line = br.readLine();
                        if (line == null)
                            break;
                        html.append(line + '\n');
                    }

                    br.close();
                }
                //conn.disconnect();
            }
            conn.disconnect(); //접속 종료
        } catch (Exception e) {
            // TODO Auto-generated catch block

//			   Log.e("post 전송중 에러!", e.getMessage());
            Message msg = new Message();
            msg.what = GET_TYPE_FAILED; //실패
            msg.obj = "post 전송중 에러!";
            handler.sendMessage(msg);
            conn.disconnect(); //접속 종료
            return;
        } finally {


        }
        String tmp00, tmp01;
        int start, end;
        // return html.toString();
        try {
            start = html.indexOf("softwareVersion") + "softwareVersion".length() + 2;
            end = html.indexOf("</div>", start);
            Log.d("GetMarket", html.toString());
            result = html.substring(start, end);
            result = result.trim();
            Message msg = new Message();
            msg.what = GET_TYPE_SUCCESS; //성공
            msg.obj = result; //가져온 String Data를 저장
            handler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.what = GET_TYPE_FAILED; //실패
            msg.obj = "post 전송중 에러!";
            handler.sendMessage(msg);
            result = "";
            return;
        }

    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public static boolean isNeedUpdate(String n, String m) {
        try {
            if (n.length() == 0 || m.length() == 0) {
                return false;
            }
            if (n.contains(".") && m.contains(".")) {
                String[] _n = n.split("\\.");
                String[] _m = m.split("\\.");
                int cnt = 0;
                if (_n.length > _m.length)
                    cnt = _m.length;
                else
                    cnt = _n.length;
                for (int i = 0; i < cnt; i++) {
                    int __n = Integer.parseInt(_n[i]);
                    int __m = Integer.parseInt(_m[i]);
                    if (__n == __m) {
                        continue;
                    } else if (__n > __m) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                int _n = Integer.parseInt(n);
                int _m = Integer.parseInt(m);
                if (_n > _m) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


}
