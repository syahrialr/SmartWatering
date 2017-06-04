package zexal.org.smartwatering;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.suke.widget.SwitchButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Manual extends AppCompatActivity {

    String url = "http://krstudio.web.id";
    @BindView(R.id.flowrate) TextView fl;
    @BindView(R.id.currentliquid) TextView cl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        ButterKnife.bind(this);

        Thread t = new Thread(){
            @Override
            public void run(){
                while (!isInterrupted()){

                    try {
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadJSON();
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        t.start();


        final SwitchButton switchButton = (SwitchButton) findViewById(R.id.switch_button);

        SharedPreferences sharedPrefs = getSharedPreferences("zexal.org.smartwatering", MODE_PRIVATE);
        switchButton.setChecked(sharedPrefs.getBoolean("NameOfThingToSave", false));

        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("zexal.org.smartwatering", MODE_PRIVATE).edit();
                    editor.putBoolean("NameOfThingToSave", true);
                    editor.apply();
                    switchButton.setChecked(true);
                    /* Switch is led 1 */
                    new Background_get().execute("update?api_key=QX9HVQ0P3HCPLZHO&field1=1");
                    Toast.makeText(Manual.this, "On!", Toast.LENGTH_LONG).show();
                    new CountDownTimer(30000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            switchButton.setEnabled(false);
                        }

                        public void onFinish() {
                            switchButton.setEnabled(true);
                        }
                    }.start();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("zexal.org.smartwatering", MODE_PRIVATE).edit();
                    editor.putBoolean("NameOfThingToSave", false);
                    editor.apply();
                    new Background_get().execute("update?api_key=QX9HVQ0P3HCPLZHO&field1=0");
                    Toast.makeText(Manual.this, "Off!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private class Background_get extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                /* Change the IP to the IP you set in the arduino sketch */
                URL url = new URL("http://api.thingspeak.com/" + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");

                in.close();
                connection.disconnect();
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<Data>> call = request.getJSON();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                updateTextSuhu(response.body().get(0).getFlowrate(),response.body().get(0).getCurrentliquid());
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });

    }

    private void updateTextSuhu(String flowrate, String currentliquid) {
        fl.setText("Debit : " + flowrate + " L/Min");
        cl.setText("Current Liquid Flowing : " + currentliquid + " mL/Sec");

    }

}
