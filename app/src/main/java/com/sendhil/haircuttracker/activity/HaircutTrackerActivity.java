package com.sendhil.haircuttracker.activity;

import com.sendhil.haircuttracker.R;
import com.sendhil.haircuttracker.adapter.DataAdapter;
import com.sendhil.haircuttracker.model.HaircutModel;
import com.sendhil.haircuttracker.ui.DividerItemDecoration;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HaircutTrackerActivity extends AppCompatActivity implements View.OnClickListener{

    private Boolean isFabOpen = false;
    private FloatingActionButton fabAdd,fabHaircut, fabDelete;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private RecyclerView mRecyclerView = null;
    private Realm mRealm;
    private DataAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_haircut_tracker);

        fabAdd = (FloatingActionButton)findViewById(R.id.fab_add);
        fabHaircut = (FloatingActionButton)findViewById(R.id.fab_haircut);
        fabDelete = (FloatingActionButton)findViewById(R.id.fab_delete);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        fabAdd.setOnClickListener(this);
        fabHaircut.setOnClickListener(this);
        fabDelete.setOnClickListener(this);


        mRealm = Realm.getDefaultInstance();
        mRecyclerView = (RecyclerView) findViewById(R.id.realm_recycler_view);


        //Asynchronous query
        RealmResults<HaircutModel> mResults = mRealm.where(HaircutModel.class).findAll();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DataAdapter(this, mRealm, mResults);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab_add:

                animateFAB();
                break;
            case R.id.fab_haircut:
                mAdapter.add();
                break;

            case R.id.fab_delete:
                mAdapter.remove();
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fabAdd.startAnimation(rotate_backward);

            fabHaircut.startAnimation(fab_close);
            fabDelete.startAnimation(fab_close);

            fabHaircut.setClickable(false);
            fabDelete.setClickable(false);

            isFabOpen = false;

        } else {

            fabAdd.startAnimation(rotate_forward);


            fabHaircut.startAnimation(fab_open);
            fabDelete.startAnimation(fab_open);

            fabHaircut.setClickable(true);
            fabDelete.setClickable(true);

            isFabOpen = true;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
        mRealm = null;
    }
}
