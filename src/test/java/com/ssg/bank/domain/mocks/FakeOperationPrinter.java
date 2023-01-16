package com.ssg.bank.domain.mocks;

import com.ssg.bank.domain.IOperationPrinter;
import com.ssg.bank.domain.OperationLine;
import com.ssg.bank.domain.Statement;

import java.util.LinkedList;
import java.util.List;

 public class FakeOperationPrinter implements IOperationPrinter {

    private final List<OperationLine> operationLines = new LinkedList<>();

    @Override
    public void print(Statement statement) {
        this.operationLines.addAll(statement.getOperationLines());
    }

    public List<OperationLine> getOperationLines() {
        return operationLines;
    }

}
