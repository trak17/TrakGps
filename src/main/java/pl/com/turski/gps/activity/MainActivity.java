package pl.com.turski.gps.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import pl.com.turski.gps.service.LocationService;
import pl.com.turski.trak.gps.R;

public class MainActivity extends Activity {

    private static Activity thisActivity;
    private Intent locationService;

    Button settingsButton;
    Button testLocationButton;
    TextView testLocationLat;
    TextView testLocationLng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        checkGps();
        startLocationService();
        thisActivity = this;
    }

    private void initView() {
        testLocationLat = (TextView) findViewById(R.id.testLocationLat);
        testLocationLng = (TextView) findViewById(R.id.testLocationLng);
        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        testLocationButton = (Button) findViewById(R.id.testLocationButton);
        testLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendTestLocation();
            }
        });
    }

    private void checkGps() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager == null || !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Włącz obsługę GPS i spróbuj ponownie")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            builder.create().show();
        }
    }

    private void startLocationService() {
        SharedPreferences settings = this.getSharedPreferences("pl.com.turski.trak.gps", Context.MODE_PRIVATE);

        if (settings.getBoolean("locationService", false)) {
            return;
        }
        locationService = new Intent(this, LocationService.class);
        startService(locationService);
    }

    @Override
    protected void onDestroy() {
        stopService(locationService);
        super.onDestroy();
    }

    public static void showToast(final String message) {
        thisActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(thisActivity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendTestLocation() {
        try {
            Double lat = Double.parseDouble(String.valueOf(testLocationLat.getText()));
            Double lng = Double.parseDouble(String.valueOf(testLocationLng.getText()));

            Intent locationIntent = new Intent();
            locationIntent.setAction("pl.com.turski.trak.gps.intent.action.LOCATION");
            locationIntent.putExtra("latitude", lat);
            locationIntent.putExtra("longitude", lng);
            this.sendBroadcast(locationIntent);
        } catch (NumberFormatException ex) {
            showToast("Wprowadź prawidłowe wartości lat i lng");
        }
    }
}
