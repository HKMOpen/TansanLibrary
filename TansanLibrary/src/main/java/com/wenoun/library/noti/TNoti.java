package com.wenoun.library.noti;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;

/**
 * Created by jeyhoon on 16. 2. 29..
 */
public class TNoti {
    private Context ctx=null;
    private NotificationCompat.Builder builder=null;
    private int smallIconResID;
    private Bitmap largeIcon=null;
    private PendingIntent contentIntent=null;
    private String ticker="";
    private String contentTitle="";
    private String contentText="";
    private int number=0;
    private boolean isAutoCancel=true;
    private int notiId=0;
    public TNoti(Context ctx,int notiId){
        this.ctx=ctx;
        this.notiId=notiId;
    }
    public TNoti setTicker(String ticker){
        this.ticker=ticker;
        return this;
    }
    public String getTicker(){
        return ticker;
    }

    public int getSmallIconResID() {
        return smallIconResID;
    }

    public TNoti setSmallIconResID(int smallIconResID) {
        this.smallIconResID = smallIconResID;
        return this;
    }

    public Bitmap getLargeIcon() {
        return largeIcon;
    }

    public TNoti setLargeIcon(Bitmap largeIcon) {
        this.largeIcon = largeIcon;
        return this;
    }

    public PendingIntent getContentIntent() {
        return contentIntent;
    }

    public TNoti setContentIntent(PendingIntent contentIntent) {
        this.contentIntent = contentIntent;
        return this;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public TNoti setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
        return this;
    }

    public String getContentText() {
        return contentText;
    }

    public TNoti setContentText(String contentText) {
        this.contentText = contentText;
        return this;
    }
    public TNoti setNumber(int number){
        this.number=number;
        return this;
    }
    public int getNumber(){
        return number;
    }
    public TNoti setAutoCancel(boolean isAutoCancel){
        this.isAutoCancel=isAutoCancel;
        return this;
    }
    public NotificationCompat.Builder getBuilder(){
        return builder;
    }
    public TNoti buildNoti(){
        builder = new NotificationCompat.Builder(ctx)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setTicker(ticker)
                .setSmallIcon(smallIconResID)
                .setLargeIcon(largeIcon)
                .setContentIntent(contentIntent)
                .setAutoCancel(isAutoCancel)
                .setWhen(System.currentTimeMillis())
                .setDefaults( Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS)
                .setNumber(number);
        return this;
    }
    public TNoti buildNoti(NotificationCompat.Builder builder){
        this.builder=builder;
        return this;
    }
    public void setNoti() {
        NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification  n = builder.build();
        nm.notify(notiId, n);
    }
}
