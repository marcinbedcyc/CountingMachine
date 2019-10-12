package pl.maszynalicznikowa.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import pl.maszynalicznikowa.R;
import pl.maszynalicznikowa.models.ValueModel;

public class ArgumentAdapter extends ArrayAdapter<ValueModel> {

    private  List<ValueModel> objects;
    private RejestrAdapter rejestrAdapter;

    public ArgumentAdapter(@NonNull Context context, int resource, @NonNull List<ValueModel> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.edit_arg, parent, false);
        }

        final Holder holder = new Holder();
        holder.editText = convertView.findViewById(R.id.args_list_Item);
        holder.editText.setText(String.valueOf(objects.get(position).getValue()));
        holder.position = position;

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(holder.position < objects.size()) {
                    if (s.toString().equals("")) {
                        objects.get(holder.position).setValue(0);
                        rejestrAdapter.setRejestrAt(0, holder.position);
                    } else {
                        objects.get(holder.position).setValue(Integer.valueOf(s.toString()));
                        rejestrAdapter.setRejestrAt(Integer.valueOf(s.toString()), holder.position);
                    }
                }
            }
        });

        return convertView;
    }

    class Holder {
        EditText editText;
        int position;
    }

    public void removeLast(){
        if(!objects.isEmpty()) {
            objects.remove(objects.size() - 1);
            ArgumentAdapter.this.notifyDataSetChanged();
        }
    }

    public List<ValueModel> getObjects() {
        return objects;
    }

    public RejestrAdapter getRejestrAdapter() {
        return rejestrAdapter;
    }

    public void setRejestrAdapter(RejestrAdapter rejestrAdapter) {
        this.rejestrAdapter = rejestrAdapter;
    }
}
