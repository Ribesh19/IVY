package com.creatudevelopers.ribeshmaharjan.ivy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ThankyouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);

        Thread thread = new Thread( new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(3000);
                    Intent intent=new Intent(ThankyouActivity.this, MainLayoutActivity.class);
                    startActivity(intent);

                }


            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                finish();
            }

        }
    });

    thread.start();
        }

}
