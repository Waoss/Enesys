package com.waoss.enesys.cpu.instructions;

import com.waoss.enesys.mem.Addresing;

public final class Instruction {

    private InstructionName instructionName;
    private byte opcode;
    private Addresing addresing;

    public Instruction(InstructionName instructionName, Addresing addresing) {
        this.instructionName = instructionName;
        this.addresing = addresing;
    }

    public Instruction(byte opcode, Addresing addresing) {
        this.opcode = opcode;
        this.addresing = addresing;
    }

    public InstructionName getInstructionName() {
        return instructionName;
    }

    public byte getOpcode() {
        return opcode;
    }

    public Addresing getAddresing() {
        return addresing;
    }
}
