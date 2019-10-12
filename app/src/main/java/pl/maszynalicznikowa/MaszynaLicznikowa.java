package pl.maszynalicznikowa;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.maszynalicznikowa.adapters.RejestrAdapter;
import pl.maszynalicznikowa.models.InstructionModel;
import pl.maszynalicznikowa.models.RejestrModel;
import pl.maszynalicznikowa.models.ValueModel;

@Data
@AllArgsConstructor
public class MaszynaLicznikowa {
    private List<ValueModel> args;
    private List<InstructionModel> instructions;
    private List<RejestrModel> rejestrs;
    private RejestrAdapter rejestrAdapter;

    public long compute(){
        int commandCounter = 0;
        int numberInstructions = instructions.size();
        while(commandCounter < numberInstructions){
            System.out.println("CommandCounter: " + commandCounter);
            switch (instructions.get(commandCounter).getInstructionCode()){
                case 0:
                    clearInstruction(instructions.get(commandCounter).getInstructionArgument());
                    rejestrAdapter.notifyDataSetChanged();
                    commandCounter++;
                    break;
                case 1:
                    succesor(instructions.get(commandCounter).getInstructionArgument());
                    rejestrAdapter.notifyDataSetChanged();
                    commandCounter++;
                    break;
                case 2:
                    change(instructions.get(commandCounter).getInstructionArgument(), instructions.get(commandCounter).getGetInstructionArgument2());
                    rejestrAdapter.notifyDataSetChanged();
                    commandCounter++;
                    break;
                case 3: {
                    int temp = ifInstruction(instructions.get(commandCounter).getInstructionArgument(), instructions.get(commandCounter).getGetInstructionArgument2(), instructions.get(commandCounter).getGoToInstructionCode());
                    rejestrAdapter.notifyDataSetChanged();
                    if (temp == -1) {
                        commandCounter++;
                    } else {
                        commandCounter = temp;
                    }
                    break;
                }
            }

        }
        return rejestrs.get(0).getValue();
    }

    private void clearInstruction(int rejestrIndex){
        rejestrs.get(rejestrIndex).setValue(0);
    }

    private void succesor(int rejestrIndex){
        long value = rejestrs.get(rejestrIndex).getValue();
        value++;
        rejestrs.get(rejestrIndex).setValue(value);
    }
    private  void change(int valueRejestr, int changeRejestr){
        long value = rejestrs.get(valueRejestr).getValue();
        rejestrs.get(changeRejestr).setValue(value);
    }

    private int ifInstruction(int arg1Index, int arg2Index, int goToInstructionCode){
        if(rejestrs.get(arg1Index).getValue() == rejestrs.get(arg2Index).getValue()) return goToInstructionCode;
        else return  -1;
    }
}
