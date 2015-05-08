package safechat.chat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.math.BigInteger;

/**
 * Created by shankar on 4/10/2015.
 */
public class MSGReceiver  extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        BigInteger message=new BigInteger("message".getBytes());
        AsymmetricKeyGeneration asymmetricKeyGeneration= new AsymmetricKeyGeneration(1024);
        //Decryption
        BigInteger encryptedMessage= new BigInteger(extras.getString("msg"));
        System.out.println("Before bigint"+extras.getString("msg"));

        message=asymmetricKeyGeneration.decrypt(encryptedMessage);
        String decryptedMessage=new String(message.toByteArray());
        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("msg",decryptedMessage);
        msgrcv.putExtra("fromu", extras.getString("fromu"));
        msgrcv.putExtra("fromname", extras.getString("name"));
        System.out.println("Kaise Kari"+decryptedMessage);


        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
        ComponentName comp = new ComponentName(context.getPackageName(),MSGService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}