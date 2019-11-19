package com.droidbaza.pagingnews;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.droidbaza.pagingnews.mvp.view.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       MainFragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.cont, fragment)
                .commit();

    }
}
