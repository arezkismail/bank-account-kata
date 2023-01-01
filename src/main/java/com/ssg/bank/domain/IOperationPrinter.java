package com.ssg.bank.domain;

import java.util.List;

public interface IOperationPrinter {

    void print(List<OperationLine> operations);
}
