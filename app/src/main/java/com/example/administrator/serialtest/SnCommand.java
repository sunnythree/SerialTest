package com.example.administrator.serialtest;

/**
 * Created by Administrator on 2017/2/26 0026.
 */

public class SnCommand {
    public int id;
    public byte[] cmd;
    public SnCommand(int id,byte[] cmd){
        this.id= id;
        this.cmd = cmd;
    }
}
