package com.bw.dliao.activitys;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bw.dliao.R;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoiceActivity extends Activity {

    @BindView(R.id.call_yuyin_btn)
    Button callYuyinBtn;
    @BindView(R.id.call_shipin_btn)
    Button callShipinBtn;
    @BindView(R.id.answer_call_yuyin_btn)
    Button answerCallYuyinBtn;
    @BindView(R.id.answer_call_shipin_btn)
    Button answerCallShipinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        ButterKnife.bind(this);


        incoming();


    }

    @OnClick({R.id.call_yuyin_btn, R.id.call_shipin_btn,R.id.answer_call_yuyin_btn, R.id.answer_call_shipin_btn
    ,R.id.reject_call_yuyin_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_yuyin_btn:
                try {
                    EMClient.getInstance().callManager().makeVoiceCall("66");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.call_shipin_btn:

                try {
                    EMClient.getInstance().callManager().makeVideoCall("1");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.answer_call_yuyin_btn:
                try {
                    EMClient.getInstance().callManager().answerCall();
                } catch (EMNoActiveCallException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.answer_call_shipin_btn:
                break;
            case R.id.reject_call_yuyin_btn:
                try {
                    EMClient.getInstance().callManager().rejectCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.end_call_yuyin_btn:
                try {
                    EMClient.getInstance().callManager().endCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void incoming() {
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        registerReceiver(new CallReceiver(), callFilter);


        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {

                System.out.println("callState = " + callState);
                System.out.println("error = " + error);

                switch (callState) {
                    case CONNECTING: // 正在连接对方

                        break;
                    case CONNECTED: // 双方已经建立连接

                        break;

                    case ACCEPTED: // 电话接通成功

                        break;
                    case DISCONNECTED: // 电话断了

                        break;
                    case NETWORK_UNSTABLE: //网络不稳定
                        if (error == CallError.ERROR_NO_DATA) {
                            //无通话数据
                        } else {

                        }
                        break;
                    case NETWORK_NORMAL: //网络恢复正常

                        break;
                    default:
                        break;
                }

            }
        });

    }



    private class CallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 拨打方username
            String from = intent.getStringExtra("from");
            // call type
            String type = intent.getStringExtra("type");
            //跳转到通话页面

            System.out.println("context = " + context);
            System.out.println("intent = " + intent);

        }
    }
}
