package org.mnp.accountservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CustomerDetails {
    private Account account;
    private List<Loan> loans;
    private List<Card> cards;

    public static CustomerDetails of(final Account account, final List<Loan> loans, final List<Card> cards) {
        return new CustomerDetails(account, loans, cards);
    }

    public CustomerDetails(final Account account, final List<Loan> loans, final List<Card> cards) {
        this.account = account;
        this.loans = loans;
        this.cards = cards;
    }
}
