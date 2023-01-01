package com.ssg.bank.domain.mocks;

import com.ssg.bank.domain.IOperationPrinter;
import com.ssg.bank.domain.OperationLine;

import java.util.LinkedList;
import java.util.List;

public class MockOperationPrinter implements IOperationPrinter {

    public List<OperationLine> getOperations() {
        return operations;
    }

    private List<OperationLine> operations = new LinkedList<>();

    @Override
    public void print(List<OperationLine> operations) {
        this.operations = operations;
    }
}
