package com.escom.topsecret;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.escom.topsecret.Entities.Project;
import com.escom.topsecret.Utils.Constants;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayActivity extends AppCompatActivity {

    @BindView(R.id.dp_description)
    TextView description;

    ActionBar actionBar;

    private Project project;

    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle(project.getTitle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ButterKnife.bind(this);

        project = new Gson().fromJson(getIntent().getStringExtra(Constants.PROJECT), Project.class);
        actionBar = getSupportActionBar();
        description.setText(project.getDescription());
    }
}
