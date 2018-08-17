package com.example.brunosouza.forcemusic;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.brunosouza.forcemusic.fragments.HistoricoFragment;
import com.example.brunosouza.forcemusic.fragments.HomeFragment;
import com.example.brunosouza.forcemusic.fragments.MusicaFragment;
import com.example.brunosouza.forcemusic.fragments.SobreFragment;

/**
 * @author Bruno Souza
 * Esta activity exibe um Navigation Drawer e foi implementado
 * um acelerômetro com intuito de retornar as coordenadas
 *
 * @see NavigationView
 * @link https://developer.android.com/training/implementing-navigation/nav-drawer?hl=pt-br
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    /**
     * Este método é utilizado para carregar os layouts XML e as demais
     * operações de inicialização
     *
     * @param savedInstanceState = permite restaurar o estado da activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_home);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(sel, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Tem a função de recuar/voltar
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Carrega o layout de menu
     *
     * @param menu = objeto passado como parâmetro ao getMenuInflater
     * @return = retorna true ao método onCreateOptionsMenu()
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // "infla" o menu; adiciona os itens a action bar se estiverem presentes.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * Resposável por carregar o método displaySelectedScreen()
     *
     * @param item = objeto passado como parâmetro ao método displaySelectedScreen()
     * @return = retorna true ao método onNavigationItemSelected()
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());

        return true;
    }

    /**
     * Este método tem a função de alternar entre os menus e abrir o Fragment
     * referente ao item de menu clicado
     *
     * @param id = objeto passado como parâmetro ao método displaySelectedScreen()
     * este objeto é responsável por identificar o layout XML referente ao menu clicado
     */
    private void displaySelectedScreen(int id){
        // inicia o objeto fragment como nulo
        Fragment fragment = null;

        // estrutura de decisão dos itens de menu
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_musica) {
            fragment = new MusicaFragment();
        } else if (id == R.id.nav_historico) {
            fragment = new HistoricoFragment();
        } else if (id == R.id.nav_sobre) {
            fragment = new SobreFragment();
        }

        // estrutura de decisão onde é criado o fragment
        // caso seja diferente de nulo
        if (fragment != null) {
            // carrega o FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // faz a substituição do fragment
            ft.replace(R.id.fragment_container, fragment);
            // salva as operações feitas
            ft.commit();
        }

        // carrega o DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    /**
     * Este método é responsável por carregar o acelerômetro
     */
    private SensorEventListener sel = new SensorEventListener() {
        /**
         * trata os eventos do acelerômetro
         */
        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor mySensor = event.sensor;

            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                long curTime = System.currentTimeMillis();

                if ((curTime - lastUpdate) > 1000) {
                    long diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;

                    float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                    if (speed > SHAKE_THRESHOLD) {

                    }

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }

//                Log.d(TAG, "X: " + x);
//                Log.d(TAG, "Y: " + y);
//                Log.d(TAG, "Z: " + z);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
//        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.i(TAG, "onResume");
        senSensorManager.registerListener(sel, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        senSensorManager.unregisterListener(sel);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}