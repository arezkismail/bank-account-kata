package com.skynet.bank.domain.model;

import com.skynet.bank.domain.enums.OperationTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Getter
@Setter
public class Operation implements Serializable{

	private static final String SEPARATOR = " | ";
	private static final String DATE_FORMAT = "dd/MM/YYYY";
	private DateTimeFormatter dTF = DateTimeFormatter.ofPattern(DATE_FORMAT);
	
	private UUID id;
	private LocalDateTime operationDate;
	private double amount;
	private OperationTypeEnum operationTypeEnum;
	private double balance;
	
	private Account account;
	
	public Operation(UUID id, LocalDateTime operationDate, OperationTypeEnum operationTypeEnum, double amount, double balance) {
		super();
		this.id = id;
		this.operationDate = operationDate;
		this.operationTypeEnum = operationTypeEnum;
		this.amount = amount;
		this.balance = balance;
	}

	public String toString() {
		return this.operationTypeEnum.name()
				.concat(SEPARATOR)
				.concat(dTF.format(this.operationDate))
				.concat(SEPARATOR).concat(String.valueOf(this.amount))
				.concat(SEPARATOR).concat(String.valueOf(this.balance));
	}

}
