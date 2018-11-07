package com.app.golfapp.ui.activity;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

public class MyPhoneStateListener extends PhoneStateListener {
    public static final int INVALID = Integer.MAX_VALUE;

    public int signalStrengthDbm = INVALID;
    public int signalStrengthAsuLevel = INVALID;
    public int SignalStrength_ASU ;
    public int SignalStrength_dBm;

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength)
    {
        signalStrengthDbm = getSignalStrengthByName(signalStrength, "getDbm");
        //  signalStrengthDbm=getSignalStrengthByName()
        signalStrengthAsuLevel = getSignalStrengthByName(signalStrength, "getAsuLevel");
    }

    private int getSignalStrengthByName(SignalStrength signalStrength, String methodName)
    {

        try
        {
            Class classFromName = Class.forName(SignalStrength.class.getName());
            java.lang.reflect.Method method = classFromName.getDeclaredMethod(methodName);
            Object object = method.invoke(signalStrength);
            SignalStrength_ASU = signalStrength.getGsmSignalStrength();
            SignalStrength_dBm = (2 * SignalStrength_ASU) - 113;
            return (int)SignalStrength_dBm;
        }
        catch (Exception ex)
        {
            return INVALID;
        }
    }
}
