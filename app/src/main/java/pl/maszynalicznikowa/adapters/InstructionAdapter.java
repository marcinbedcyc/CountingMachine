package pl.maszynalicznikowa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import pl.maszynalicznikowa.R;
import pl.maszynalicznikowa.models.InstructionModel;

public class InstructionAdapter extends ArrayAdapter<InstructionModel> {
    private List<InstructionModel> objects;

    public InstructionAdapter(@NonNull Context context, int resource, @NonNull List<InstructionModel> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.instructions_layout, parent, false);
        }

        Holder holder = new Holder();
        holder.textView = convertView.findViewById(R.id.instructionTextView);
        String text = new String();
        text += objects.get(position).getIndex() + ") ";
        switch (objects.get(position).getInstructionCode()){
            case 0:
                text += "Z(" + objects.get(position).getInstructionArgument() + ")";
                break;
            case 1:
                text += "S(" + objects.get(position).getInstructionArgument() + ")";
                break;
            case 2:
                text += "T(" + objects.get(position).getInstructionArgument() + ", " + objects.get(position).getGetInstructionArgument2()+")";
                break;
            case 3:
                text += "I(" + objects.get(position).getInstructionArgument() + ", " + objects.get(position).getGetInstructionArgument2() + ", " +
                objects.get(position).getGoToInstructionCode() + ")";
                break;
        }
        holder.textView.setText(text);

        return  convertView;
    }

    class Holder {
        TextView textView;
    }

    public List<InstructionModel> getObjects() {
        return objects;
    }

    public void clear(){
        objects.clear();
        InstructionAdapter.this.notifyDataSetChanged();
    }
}
