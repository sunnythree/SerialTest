package com.example.administrator.serialtest;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Administrator on 2017/2/26 0026.
 */

public class SnCmdPaser {
    public enum CmdType{REQUEST,QUERY,SET};
    private final String sn_irsr = "SN+IRST";
    private final String sn_irsst = "SN+IRSST";
    private final String sn_umtol = "SN+UMTOL";
    private final String sn_umdela = "SN+UMDELA";
    private final String sn_umdel = "SN+UMDEL";
    private final String sn_umaddr = "SN+UMADDR";


    ArrayList<SnCommand> arrayList = new ArrayList<>();
    public SnCmdPaser(){
        arrayList.add(new SnCommand(1,sn_irsr.getBytes()));
        arrayList.add(new SnCommand(2,sn_irsst.getBytes()));
    }
    private CmdTypeWrapper parseCmdtypeAndGetValue(byte [] buffer,int size){
        CmdTypeWrapper wrapper = new CmdTypeWrapper();
        for(int i=0;i<size;i++){
            if(buffer[i]=='?'){
                wrapper.type = CmdType.QUERY;
                byte[] tmp = new byte[i+1];
                System.arraycopy(buffer,0,tmp,0,i+1);
                wrapper.cmdRequestCode = tmp;
                return wrapper;
            }else if(buffer[i]=='='){
                wrapper.type = CmdType.SET;
                byte[] tmp = new byte[i+1];
                System.arraycopy(buffer,0,tmp,0,i+1);
                wrapper.cmdRequestCode = tmp;
                if(buffer[i+1]=='<'){
                    int k=i+2;
                    while(k<size){
                        if(buffer[k]=='>'){
                            break;
                        }else{
                            k++;
                        }
                    }
                    if(k>=size){
                        return null;
                    }else {
                        byte[] tmpvalue = new byte[k-i-2];
                        System.arraycopy(buffer,i+2,tmpvalue,0,k-i-2);
                        wrapper.cmdSetValue = tmpvalue;
                    }
                }else {
                    return null;
                }
                return wrapper;
            }
        }
        wrapper.type=CmdType.REQUEST;
        wrapper.cmdRequestCode = buffer;
        return wrapper;
    }
    private int matchCmd(CmdTypeWrapper cmdTypeWrapper){
        String getCmd = Arrays.toString(cmdTypeWrapper.cmdRequestCode);
        String cmdInList;
        for(int i=0;i<arrayList.size();i++){
            cmdInList = Arrays.toString(arrayList.get(i).cmd) ;
            if(getCmd.length() == cmdInList.length()){
                if(cmdInList.equals(getCmd)){
                    return arrayList.get(i).id;
                }
            }
        }
        return 0;
    }
    public int cmdPaser(byte [] buffer,int size){
        CmdTypeWrapper cmdTypeWrapper = parseCmdtypeAndGetValue(buffer,size);
        return matchCmd(cmdTypeWrapper);
    }
    private class CmdTypeWrapper{
        public CmdType type=CmdType.REQUEST;
        public byte[] cmdRequestCode;
        public byte[] cmdSetValue;

    }

}
