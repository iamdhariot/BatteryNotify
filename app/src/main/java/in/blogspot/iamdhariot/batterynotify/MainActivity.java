package in.blogspot.iamdhariot.batterynotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    // context
    private Context context;

    public static final String TAG = "MainActivity";

    // broadcast receiver
    private BroadcastReceiver broadcastReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent batteryStatus) {

            // are we charging? / charged?
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
            boolean isCharging = status==BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

            // How we are charging?
            int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
            boolean usbCharge = chargePlug==BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge  =chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            if(isCharging){
                if(usbCharge){
                    textViewBatteryStatus.setText("Charging over USB.");
                }else if(acCharge){
                    textViewBatteryStatus.setText("Charging over AC.");
                }else{
                    textViewBatteryStatus.setText("Charging.");
                }

            }else{
                textViewBatteryStatus.setText("Not Charging.");
            }

            // battery level
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            textViewBatteryPercentage.setText(level+"%");


        }
    };


    // views
    private LinearLayout linearLayoutCharged,linearLayoutDisconnect,linearLayoutConnect,linearLayout;
    private Button buttonChargedChangeTone,buttonDisConnectChangeTone,buttonConnectChangeTone;
    private Switch aSwitchVibrateCharged,aSwitchVibrateConnect,aSwitchVibrateDisconnect,aSwitchCharged,aSwitchConnect,aSwitchDisconnect;
    private TextView textViewBatteryPercentage,textViewBatteryStatus,textViewToneNameCharged,textViewToneNameDisconnect,textViewToneNameConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context= MainActivity.this;

        // views reference
        linearLayout = findViewById(R.id.linear_layout);
        linearLayoutCharged = findViewById(R.id.linear_layout_charged);
        linearLayoutConnect = findViewById(R.id.linear_layout_charger_connect);
        linearLayoutDisconnect = findViewById(R.id.linear_layout_charger_disconnect);

        buttonChargedChangeTone = findViewById(R.id.button_charged_tone_change);
        buttonConnectChangeTone = findViewById(R.id.button_connect_tone_change);
        buttonDisConnectChangeTone = findViewById(R.id.button_disconnect_tone_change);

        aSwitchCharged = findViewById(R.id.switch_charged);
        aSwitchConnect = findViewById(R.id.switch_connect);
        aSwitchDisconnect = findViewById(R.id.switch_disconnect);
        aSwitchVibrateCharged= findViewById(R.id.switch_vibrate_charged);
        aSwitchVibrateConnect = findViewById(R.id.switch_vibrate_connect);
        aSwitchVibrateDisconnect = findViewById(R.id.switch_vibrate_disconnect);

        textViewBatteryPercentage = findViewById(R.id.text_view_battery_percentage);
        textViewBatteryStatus = findViewById(R.id.text_view_battery_status);
        textViewToneNameCharged = findViewById(R.id.text_view_tone_charged);
        textViewToneNameConnect = findViewById(R.id.text_view_tone_charger_connect);
        textViewToneNameDisconnect = findViewById(R.id.text_view_tone_charger_disconnect);


        // button onclick
        buttonConnectChangeTone.setOnClickListener(this);
        buttonDisConnectChangeTone.setOnClickListener(this);
        buttonChargedChangeTone.setOnClickListener(this);

        // switch listener

        aSwitchConnect.setOnCheckedChangeListener(this);
        aSwitchDisconnect.setOnCheckedChangeListener(this);
        aSwitchCharged.setOnCheckedChangeListener(this);
        aSwitchVibrateConnect.setOnCheckedChangeListener(this);
        aSwitchVibrateDisconnect.setOnCheckedChangeListener(this);
        aSwitchVibrateCharged.setOnCheckedChangeListener(this);


        // battery state
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        /*Intent batteryStatus  =*/context.registerReceiver(broadcastReceiver,intentFilter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_charged_tone_change:
                // todo change tone
                break;
            case R.id.button_connect_tone_change:
                // todo change tone
                break;
            case R.id.button_disconnect_tone_change:
                // todo change tone
                break;
                default:
                    break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch_charged:
                if(isChecked){
                    showCharged();
                }else{
                    hideCharged();
                }

                break;
            case R.id.switch_connect:
                if(isChecked){
                    showChargerConnect();
                }else{
                    hideChargerConnect();
                }
                break;
            case R.id.switch_disconnect:
                if(isChecked){
                    showChargerDisconnect();
                }else{
                    hideChargerDisconnect();
                }

                break;
            case R.id.switch_vibrate_charged:
                break;
            case R.id.switch_vibrate_connect:
                break;
            case R.id.switch_vibrate_disconnect:
                break;
            default:
                break;




        }

    }
    
    
    private void showChargerConnect(){
        TransitionManager.beginDelayedTransition(linearLayout);
        linearLayoutConnect.setVisibility(View.VISIBLE);
        aSwitchVibrateConnect.setChecked(true);
        
    }

    private void hideChargerConnect(){
        TransitionManager.beginDelayedTransition(linearLayout);
        linearLayoutConnect.setVisibility(View.GONE);
        aSwitchVibrateConnect.setChecked(false);
    }
    private void showChargerDisconnect(){
        TransitionManager.beginDelayedTransition(linearLayout);
        linearLayoutDisconnect.setVisibility(View.VISIBLE);
        aSwitchVibrateDisconnect.setChecked(true);
        
    }

    private void hideChargerDisconnect(){
        TransitionManager.beginDelayedTransition(linearLayout);
        linearLayoutDisconnect.setVisibility(View.GONE);
        aSwitchVibrateDisconnect.setChecked(false);

    }
    private void showCharged(){
        TransitionManager.beginDelayedTransition(linearLayout);
        linearLayoutCharged.setVisibility(View.VISIBLE);
        aSwitchVibrateCharged.setChecked(true);
        
    }
    private void hideCharged(){
        TransitionManager.beginDelayedTransition(linearLayout);
        linearLayoutCharged.setVisibility(View.GONE);
        aSwitchVibrateCharged.setChecked(false);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
