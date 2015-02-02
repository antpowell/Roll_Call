package com.egmail.anthony.powell.roll_call;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
 * Created by ap198_000 on 1/19/2015.
 */
public class Proxy extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proxy_sign_in);

        Button signIn = (Button) findViewById(R.id.StoreProxyButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Proxy.this, SignIn.class));
                finish();

            }
        });
    }
}
