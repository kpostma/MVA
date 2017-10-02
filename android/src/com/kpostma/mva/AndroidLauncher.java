package com.kpostma.mva;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.kpostma.mva.MVA;

import java.util.jar.Manifest;

public class AndroidLauncher extends AndroidApplication{

    private static final String TAG = "AndroidLauncher";
    protected AdView adview;
    private SparseIntArray mErrorString;
    private static final int REQUEST_PERMISSION =10;
    RelativeLayout layout;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //ask for permissions
        mErrorString = new SparseIntArray();
        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_NETWORK_STATE},R.string.msg, REQUEST_PERMISSION);


        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        layout = new RelativeLayout(this);
        View gameview = initializeForView(new MVA(), config);
        layout.addView(gameview);


        adview = new AdView(this);
        adview.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.i(TAG, "Ad Loaded...");
            }
        });

        adview.setAdSize(AdSize.SMART_BANNER);
        adview.setAdUnitId("ca-app-pub-4147772629816498/5061079328");
        adview.setAdListener(new AdListener() {
            @Override
            public void onAdLeftApplication (){
                {
                    //Do your stuff
                    adview.destroy();

                }
            }
        });
        AdRequest.Builder builder = new AdRequest.Builder();

        builder.addTestDevice("1D5D6FE580C78D9628BE5459C85919DB");
        RelativeLayout.LayoutParams adparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        layout.addView(adview, adparams);

        adview.loadAd(builder.build());

        setContentView(layout);


	}

    public  void onPermissionsGranted(int requestCode){};

    public void requestPermissions(final String[] requstedPermissions, final int stringId, final int requestCode){
        mErrorString.put(requestCode,stringId);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermissions = false;
        for(String permission: requstedPermissions){
            permissionCheck = permissionCheck + checkSelfPermission(permission);
            showRequestPermissions = showRequestPermissions || shouldShowRequestPermissionRationale( permission);
        }

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            requestPermissions(requstedPermissions, requestCode);
        }else{
            requestPermissions(requstedPermissions, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for(int permission:grantResults){
            permissionCheck = permissionCheck + permission;
        }
        if((grantResults.length>0) && PackageManager.PERMISSION_GRANTED == permissionCheck)
        {
            onPermissionsGranted(requestCode);
        }
        else
        {
            Intent i = new Intent();
            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            i.setData(Uri.parse("package:" + getPackageName()));
            i.addCategory(Intent.CATEGORY_DEFAULT);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(i);
        }
    }

}
