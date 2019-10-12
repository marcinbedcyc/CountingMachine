package pl.maszynalicznikowa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pl.maszynalicznikowa.R;
import pl.maszynalicznikowa.models.RejestrModel;
import pl.maszynalicznikowa.models.ValueModel;

public class RejestrAdapter extends ArrayAdapter<RejestrModel> {
    List<RejestrModel> objects;

    public RejestrAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rejestr_layout, parent, false);
        }

        TextView indexTextView = convertView.findViewById(R.id.indexTextView);
        indexTextView.setText("Index: " + String.valueOf(objects.get(position).getIndex()));

        TextView valueTextView = convertView.findViewById(R.id.valueTextView);
        valueTextView.setText("Wartość: " + String.valueOf(objects.get(position).getValue()));

        return convertView;
    }

    public void removeLast(){
        if(objects.size() > 1) {
            int index = objects.size();
            objects.remove(index-1);
            RejestrAdapter.this.notifyDataSetChanged();
        }
    }

    public void clearRejestrAt(int index){
        objects.get(index).setValue(0);
        RejestrAdapter.this.notifyDataSetChanged();
    }

    public void succesorRejestrAt(int index){
        long value = objects.get(index).getValue();
        value++;
        objects.get(index).setValue(value);
        RejestrAdapter.this.notifyDataSetChanged();
    }

    public void setRejestrAt(long value, int index){
        objects.get(index+1).setValue(value);
        RejestrAdapter.this.notifyDataSetChanged();
    }

    public List<RejestrModel> getObjects() {
        return objects;
    }

    public void reset(){
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).setValue(0);
        }
    }

    public void setArguments( List<ValueModel> args){
        for (int i = 0; i < args.size(); i++) {
            objects.get(i+1).setValue(args.get(i).getValue());
        }

    }
}

