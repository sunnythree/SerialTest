package com.example.administrator.serialtest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.Arrays;

public class MyService extends Service implements SerialPortUtil.OnDataReceiveListener{
    SerialPortUtil util;
    private SnCmdPaser snCmdPaser;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    byte[] bb= bundle.getByteArray("data");
                    int size = bundle.getInt("size");
                    byte[] lbb = new byte[size];
                    System.arraycopy(bb,0,lbb,0,size);
                    int id = snCmdPaser.cmdPaser(lbb,size);
                    if(id>0){
                        Log.d("jinwei","get CMD"+"id is "+id);
                        parseCmdId(id);
                    }else{
                        Log.d("jinwei","not get CMD");
                    }
                    break;
            }
            return false;
        }
    });
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }
    public class MyBinder extends Binder{
        public MyService getService(){
            return  MyService.this;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        util = SerialPortUtil.getInstance();
        util.setOnDataReceiveListener(this);
        snCmdPaser = new SnCmdPaser();
        util.sendCmds("get data");
        util.sendCmds("get data");
    }

    @Override
    public void onDataReceive(byte[] buffer, int size) {
        Message msg = new Message();
        msg.what=1;
        Bundle bundle = new Bundle();
        bundle.putByteArray("data",buffer);
        bundle.putInt("size",size);
        msg.setData(bundle);
        handler.sendMessage(msg);

    }
    private void parseCmdId(int id){
        switch (id){
            case 1:
                util.sendCmds("1OK");
                break;
            case 2:
                util.sendCmds("2OK");
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            default:
                break;
        }

    }
}
