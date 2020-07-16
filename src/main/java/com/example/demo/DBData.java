package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "DATA")
public class DBData {
	@Id
	@Column(name = "ID", columnDefinition="nvarchar(50)")
	private String id;
	@Column(name = "VALUE", columnDefinition="nvarchar(max)")
	private String value;
}
