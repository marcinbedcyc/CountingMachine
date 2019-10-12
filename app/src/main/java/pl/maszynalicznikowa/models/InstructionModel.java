package pl.maszynalicznikowa.models;

import android.widget.TextView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionModel {
    private int instructionCode;
    private int instructionArgument;
    private int getInstructionArgument2;
    private int goToInstructionCode;
    private int index;
}