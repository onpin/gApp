package com.app.golfapp.utils;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by hmnsharma on 4/19/2018.
 */

public class BaseCommandUtil {
    private static final String TAG = "BaseCommandUtil";
    protected static void runCommandWithRoot(String command){
        Log.v(TAG, "Running command " + command);

        Process p;
        DataOutputStream os;

        try{
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(command);
            os.writeBytes("exit\n");
            os.flush();

          //  if( p.isAlive() ){
          //      Log.v(TAG, "Process is alive.");
          //  }else{
                Log.v(TAG, "Return code : " + p); // It usually haven't exited by now.
          //  }
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }

        try{
            p.waitFor();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        Log.v(TAG, "Return code : " + p);

    }

    public static void requireRoot(){
        runCommandWithRoot("");
    }
}
