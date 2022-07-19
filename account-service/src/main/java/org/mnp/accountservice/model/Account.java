package org.mnp.accountservice.model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "accounts")
@Getter @Setter @ToString
public class Account {

    @Column(name = "account_number")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountNumber;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;

    @Column(name = "create_date")
    private LocalDate createDate;


}
