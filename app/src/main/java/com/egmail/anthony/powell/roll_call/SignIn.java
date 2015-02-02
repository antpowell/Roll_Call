package com.egmail.anthony.powell.roll_call;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
 * Created by ap198_000 on 1/19/2015.
 */
public class SignIn extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in_activity);

        Button proxy = (Button) findViewById(R.id.ProxyButton);
        proxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, Proxy.class));
            }
        });
    }
}
