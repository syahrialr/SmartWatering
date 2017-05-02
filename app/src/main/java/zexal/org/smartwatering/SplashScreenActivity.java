package zexal.org.smartwatering;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



/**
 * Created by M Syahrial Rukmana on 16/11/2016.
 */

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
            finish();
        }


    }


