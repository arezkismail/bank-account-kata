package com.skynet.bank.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Getter
@Setter
public class Customer implements Serializable{
	
	private UUID code;
	private String name;
	private String address;
	
	private Account account;
	
	
	public Customer() {
	}

	public Customer(String name, String address) {
		super();
		this.code = randomUUID();
		this.name = name;
		this.address = address;
	}
	
	

}
