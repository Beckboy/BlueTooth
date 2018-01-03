package com.example.hunter_j.demo_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button mBtnOpenBt;
    BluetoothAdapter mBluetoothAdapter;

    public static final String TAG = "MainActivityt"; //打开蓝牙
    public static final int REQUEST_OPEN_BT = 0X01; //打开蓝牙

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //打开本地的蓝牙的适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (null == mBluetoothAdapter){
            showToast("该设备不支持蓝牙");
            return;
        }
        //获取名字 MAC地址
        String name = mBluetoothAdapter.getName();
        String mac = mBluetoothAdapter.getAddress();
        Log.e(TAG,"名字:"+name+",地址:"+mac);

        //获取当前蓝牙的状态
        int state = mBluetoothAdapter.getState();
        switch (state){
            case BluetoothAdapter.STATE_ON://蓝牙已经打开
                showToast("蓝牙已经打开");
                break;
            case BluetoothAdapter.STATE_TURNING_ON://；蓝牙正在打开
                showToast("蓝牙正在打开...");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF://；蓝牙正在关闭
                showToast("蓝牙正在关闭...");
                break;
            case BluetoothAdapter.STATE_OFF://蓝牙已经关闭
                showToast("蓝牙已经关闭");
                break;
        }

        mBtnOpenBt = (Button) findViewById(R.id.btn_open_bt);
        mBtnOpenBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //关闭——打开本地蓝牙设备
             //判断该设备蓝牙是否打开
                if (mBluetoothAdapter.isEnabled()){
                    showToast("蓝牙已经处于打开状态...");
                    //关闭蓝牙
                    boolean isClose = mBluetoothAdapter.disable();
                    Log.e(TAG,"蓝牙是否关闭:"+isClose);
                }else {
                    //打开蓝牙
//                    boolean isOpen = mBluetoothAdapter.enable();
//                    showToast("蓝牙的状态:"+isOpen);

                    //调用系统的API打开
                    Intent open = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(open,REQUEST_OPEN_BT);
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_OPEN_BT == 0X01){
            if (requestCode == RESULT_CANCELED){
                showToast("取消");
            }else {
                showToast("请求成功...");
            }
        }
    }

    public void showToast(String msg){
        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }

}
