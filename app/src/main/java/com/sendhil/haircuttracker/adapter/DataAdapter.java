package com.sendhil.haircuttracker.adapter;
import com.sendhil.haircuttracker.R;
import com.sendhil.haircuttracker.model.HaircutModel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {
    private LayoutInflater mInflater;
    private Realm mRealm;
    private RealmResults<HaircutModel> mResults;

    public DataAdapter(Context context, Realm realm, RealmResults<HaircutModel> results) {
        mRealm = realm;
        mInflater = LayoutInflater.from(context);
        setResults(results);
    }

    public HaircutModel getItem(int position) {
        return mResults.get(position);
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_view, parent, false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        HaircutModel data = mResults.get(position);

        DateFormat formatter = DateFormat.getDateInstance();
        final String dateFormatted = formatter.format(data.getDateStamp());

        holder.setData(dateFormatted);
    }

    public void setResults(RealmResults<HaircutModel> results) {
        mResults = results;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return mResults.get(position).getDateStamp();
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public void add() {

        //Create a new object that contains the data we want to add
        HaircutModel data = new HaircutModel();

        //Set the timestamp of creation of this object as the current time

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date today = new Date();

        try {
            Date todayWithZeroTime = formatter.parse(formatter.format(today));
            data.setDateStamp(todayWithZeroTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Start a transaction
        mRealm.beginTransaction();

        //Copy or update the object if it already exists, update is possible only if your table has a primary key
        mRealm.copyToRealmOrUpdate(data);

        //Commit the transaction
        mRealm.commitTransaction();

        //Tell the Adapter to update what it shows.
        notifyDataSetChanged();
    }

    public void remove() {

        //Start a transaction
        mRealm.beginTransaction();

        //Remove the item from the desired position
        mResults.deleteLastFromRealm();

        //Commit the transaction
        mRealm.commitTransaction();

        //Tell the Adapter to update what it shows
        notifyDataSetChanged();
    }

    public static class DataHolder extends RecyclerView.ViewHolder {
        TextView area;

        public DataHolder(View itemView) {
            super(itemView);
            area = (TextView) itemView.findViewById(R.id.haircut_text_view);
        }

        public void setData(String text) {
            area.setText(text);
        }
    }
}
