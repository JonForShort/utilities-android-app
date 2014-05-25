package com.github.thejunkjon.utilities.app.filebrowser;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.thejunkjon.utilities.app.R;
import com.github.thejunkjon.utilities.app.TitlelessActivity;

public class FileBrowserActivity extends TitlelessActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.file_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
