package com.ssg.bank.domain;

import java.util.LinkedList;
import java.util.List;

public class Statement {
    private List<OperationLine> operationLines;

    public Statement() {
        this.operationLines = new LinkedList<>();
    }

    public List<OperationLine> getOperationLines() {
        return operationLines;
    }

    public void addOperation(Operation operation, Balance balance) {
        this.operationLines.add(new OperationLine(operation, balance));
    }

}
