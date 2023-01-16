package com.ssg.bank.infra;

import com.ssg.bank.domain.IOperationPrinter;
import com.ssg.bank.domain.Operation;
import com.ssg.bank.domain.Statement;

import java.time.format.DateTimeFormatter;

public class OperationPrinter implements IOperationPrinter {

    private static final String OPERATION_HEADER = "operation  | date  | amount  | balance";
    private static final String OPERATION_LINE =  "%s | %s | %s | %s \n";
    private static final String DATE_FORMAT = "dd/MM/YYYY HH:mm";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Override
    public void print(Statement statement) {
        StringBuilder operationPrinter = new StringBuilder(OPERATION_HEADER+"\n");
        statement.getOperationLines().forEach(operationLine -> {
            Operation operation = operationLine.operation();
            String line = String.format(OPERATION_LINE, operation.type(),
                    operation.date().format(dateFormatter), operation.amount().value(),
                    operationLine.balance().value());
            operationPrinter.append(line);
        });
        System.out.println(operationPrinter);
    }

}
