package pl.maszynalicznikowa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.maszynalicznikowa.adapters.InstructionAdapter;
import pl.maszynalicznikowa.adapters.ArgumentAdapter;
import pl.maszynalicznikowa.adapters.RejestrAdapter;
import pl.maszynalicznikowa.models.InstructionModel;
import pl.maszynalicznikowa.models.RejestrModel;
import pl.maszynalicznikowa.models.ValueModel;

public class MainActivity extends AppCompatActivity {

    private int args;
    private int rejestryAddedByUser;
    private int numberInstructions;
    private TextView argsTextView;
    private ListView argsListView;
    private ListView rejestrListView;
    private ListView instructionsListView;
    private ArgumentAdapter argumentAdapter;
    private RejestrAdapter rejestrAdapter;
    private InstructionAdapter instructionAdapter;
    private ArrayList<ValueModel> numbers;
    private ArrayList<RejestrModel> rejestrs;
    private ArrayList<InstructionModel> instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        args = 0;
        rejestryAddedByUser = 0;
        numberInstructions = 0;

        argsTextView = findViewById(R.id.argsTextView);
        argsTextView.setText(String.valueOf(args));

        argsListView = findViewById(R.id.argumentsListView);
        argsListView.setDivider(null);
        numbers = new ArrayList<>();
        argumentAdapter = new ArgumentAdapter(this, R.layout.edit_arg, numbers);
        argsListView.setAdapter(argumentAdapter);

        rejestrListView = findViewById(R.id.rejestrListView);
        rejestrListView.setDivider(null);
        rejestrs = new ArrayList<>();
        rejestrs.add(new RejestrModel(0,0));
        rejestrAdapter = new RejestrAdapter(this, R.layout.rejestr_layout, rejestrs);
        rejestrListView.setAdapter(rejestrAdapter);
        argumentAdapter.setRejestrAdapter(rejestrAdapter);

        instructionsListView = findViewById(R.id.instructionsListView);
        instructionsListView.setDivider(null);
        instructions = new ArrayList<>();
        instructionAdapter = new InstructionAdapter(this, R.layout.instructions_layout, instructions);
        instructionsListView.setAdapter(instructionAdapter);

        Button plusButton = findViewById(R.id.argsPlusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args++;
                rejestryAddedByUser++;
                argsTextView.setText(String.valueOf(args));
                addArg(0);
                addRejestr();
            }
        });

        Button minusButton = findViewById(R.id.argsMinusButton);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(args > 0) {
                    args--;
                    rejestryAddedByUser--;
                    argsTextView.setText(String.valueOf(args));
                    minusArg();
                    minusRejestr();
                }
                else
                    System.out.println("Liczba argumentów nie może być ujemna");
            }
        });

        Button addRejestrButton = findViewById(R.id.addRejestrButton);
        addRejestrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejestryAddedByUser++;
                addRejestr();
            }
        });

        Button minusRejestrButton = findViewById(R.id.minusRejestrButton);
        minusRejestrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rejestryAddedByUser > args){
                    minusRejestr();
                    rejestryAddedByUser--;
                }
            }
        });

        Button clearInstruction = findViewById(R.id.clearCommandButton);
        clearInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final Spinner spinner = new Spinner(MainActivity.this);
                ArrayList<Integer> num = new ArrayList<>();
                for (int i = 0; i <= rejestryAddedByUser; i++) {
                    num.add(i);
                }
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item, num );
                spinner.setAdapter(adapter);
                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(spinner);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addInstruction(0,Integer.valueOf(spinner.getSelectedItem().toString()),-1,-1, numberInstructions);
                        numberInstructions++;
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        Button succesorInstruction = findViewById(R.id.succesorCommandButton);
        succesorInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final Spinner spinner = new Spinner(MainActivity.this);
                ArrayList<Integer> num = new ArrayList<>();
                for (int i = 0; i <= rejestryAddedByUser; i++) {
                    num.add(i);
                }
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item, num );
                spinner.setAdapter(adapter);
                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(spinner);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addInstruction(1,Integer.valueOf(spinner.getSelectedItem().toString()),-1,-1, numberInstructions);
                        numberInstructions++;
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        Button changeInstruction = findViewById(R.id.changeCommandButton);
        changeInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                final Spinner spinnerArg1 = new Spinner(MainActivity.this);
                ArrayList<Integer> num = new ArrayList<>();
                for (int i = 0; i <= rejestryAddedByUser; i++) {
                    num.add(i);
                }
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item, num );
                spinnerArg1.setAdapter(adapter);

                final Spinner spinnerArg2 = new Spinner(MainActivity.this);
                spinnerArg2.setAdapter(adapter);

                linearLayout.addView(spinnerArg1);
                linearLayout.addView(spinnerArg2);

                builder.setView(linearLayout);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addInstruction(2,Integer.valueOf(spinnerArg1.getSelectedItem().toString()),Integer.valueOf(spinnerArg2.getSelectedItem().toString()),-1, numberInstructions);
                        numberInstructions++;
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        Button ifInstruction = findViewById(R.id.ifCommandButton);
        ifInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                final Spinner spinnerArg1 = new Spinner(MainActivity.this);
                ArrayList<Integer> num = new ArrayList<>();
                for (int i = 0; i <= rejestryAddedByUser; i++) {
                    num.add(i);
                }
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item, num );
                spinnerArg1.setAdapter(adapter);

                final Spinner spinnerArg2 = new Spinner(MainActivity.this);
                spinnerArg2.setAdapter(adapter);


                final Spinner spinnerInstruction = new Spinner(MainActivity.this);
                ArrayList<Integer> commands = new ArrayList<>();
                for (int i = 0; i <= 100; i++) {
                    commands.add(i);
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item, commands);
                spinnerInstruction.setAdapter(arrayAdapter);

                linearLayout.addView(spinnerArg1);
                linearLayout.addView(spinnerArg2);
                linearLayout.addView(spinnerInstruction);

                builder.setView(linearLayout);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addInstruction(3,Integer.valueOf(spinnerArg1.getSelectedItem().toString()),Integer.valueOf(spinnerArg2.getSelectedItem().toString()),Integer.valueOf(spinnerInstruction.getSelectedItem().toString()), numberInstructions);
                        numberInstructions++;
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        Button execute = findViewById(R.id.executeButton);
        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaszynaLicznikowa maszynaLicznikowa = new MaszynaLicznikowa(argumentAdapter.getObjects(), instructionAdapter.getObjects(), rejestrAdapter.getObjects(), rejestrAdapter);
                Toast toast = Toast.makeText(getApplicationContext(), "Wynik: " + maszynaLicznikowa.compute(), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        Button clearInstructions = findViewById(R.id.cleanInstructionButton);
        clearInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberInstructions = 0;
                instructionAdapter.clear();
                instructionsListView.removeAllViewsInLayout();
            }
        });

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejestrAdapter.reset();
                rejestrAdapter.setArguments(argumentAdapter.getObjects());
                rejestrAdapter.notifyDataSetChanged();
            }
        });

    }

    private void addArg(int arg){
        argumentAdapter.add(new ValueModel(arg));
        argumentAdapter.notifyDataSetChanged();
    }

    private void minusArg(){
        argumentAdapter.removeLast();
    }

    private void addRejestr(){
       rejestrAdapter.add(new RejestrModel(rejestryAddedByUser, 0));
       rejestrAdapter.notifyDataSetChanged();
    }

    private void minusRejestr(){
        rejestrAdapter.removeLast();
    }

    private void addInstruction(int code, int arg1, int arg2, int nextInstructionCode, int index){
        instructionAdapter.add(new InstructionModel(code, arg1, arg2, nextInstructionCode, index));
        instructionAdapter.notifyDataSetChanged();
    }
}
