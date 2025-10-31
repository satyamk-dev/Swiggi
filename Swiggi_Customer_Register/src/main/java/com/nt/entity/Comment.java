package com.nt.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name="CommentTable")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor 
public class Comment {
	@Id
	@SequenceGenerator(name = "customer_seq", sequenceName = "customer_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
	private Integer cid;
	
	@NonNull
	private String customerName;
	
	@NonNull
	private String comment;
	
	@NonNull
	private Double Stars;
	
	@UpdateTimestamp
	private LocalDateTime commentDate;
	
	

}
