package com.escom.topsecret;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.escom.topsecret.entities.Project;
import com.escom.topsecret.utils.Constants;
import com.escom.topsecret.utils.ProjectAdapter;
import com.escom.topsecret.utils.RecyclerTouchListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SessionActivity extends AppCompatActivity {

    @BindView(R.id.rv_projects)
    RecyclerView recyclerView;

    private ProjectAdapter projectAdapter;
    private List<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        ButterKnife.bind(this);

        Gson gson = new Gson();

        projects = new ArrayList<>();


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        projectAdapter = new ProjectAdapter(projects);
        recyclerView.setAdapter(projectAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), (view, position) -> {
            Intent intent = new Intent(SessionActivity.this, DisplayActivity.class);
            intent.putExtra(Constants.PROJECT, gson.toJson(projects.get(position)));
            startActivity(intent);
        }));
        
        populateRecyclerView();

    }

    private void populateRecyclerView() {
        projects.add(new Project("Proyecto 1", "Química", "06 Dec 15", "Producción de eritrocitos por medio de nitrato de carbono"));
        projects.add(new Project("Proyecto 2", "Matemáticas", "18 Oct 17", "Los domingos a las 4:00 ya todos tienen que estar listos en el puerto de Velia si quieres asistir a la party de Vell."));
        projects.add(new Project("Proyecto 3", "Seguridad Naval", "02 Feb 16", " En las tardes/noches avisaremos que vamos a hacer party de Ballenas y Khalks, para que tengan mosquetes listos si quieren ir."));
        projects.add(new Project("Proyecto 4", "Armamento Militar", "16 Nov 13", "La segunda es Redbattlefield. Esta no tenemos un horario especifico pero todos los días en las noches vamos a preguntar para ver si vamos ya sea entre nosotros o en los canales de Velia con gente poderosa."));
        projects.add(new Project("Proyecto 5", "Nanopartículas", "18 Jul 12", "La primera es obviamente sailing pero esta vez será diferente. Ahora es recomendable llevar su propio barco, si no tienen entonces lleven maderas para reparar y mucho peso para lootear además de claro llevar a un personaje nivel 50 o superior. "));
        projects.add(new Project("Proyecto 6", "Rape", "18 Mar 19", "Somos una familia con 10 años de existencia, durante este tiempo hemos tenido presencia en distintos juegos y servidores, siendo el mas conocido RagnarokOnline, "));
        projects.add(new Project("Proyecto 7", "Instrumentación", "23 Jun 11", "Tree of Savior con mira a Black Desert.pero en el duro proceso de dejar un juego al que le invertimos mas de 3000 horas perdimos algunos integrantes y es por eso que los que decidimos continuar juntos"));

        projectAdapter.notifyDataSetChanged();
    }
}
