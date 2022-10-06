package org.mnp.accountservice.service.client;

import org.mnp.accountservice.model.Card;
import org.mnp.accountservice.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDetailsService {

    private final LoansClient loansClient;
    private final CardsClient cardsClient;

    public CustomerDetailsService(LoansClient loansClient, CardsClient cardsClient) {
        this.loansClient = loansClient;
        this.cardsClient = cardsClient;
    }

    public List<Loan> getCustomerLoans(final int customerId) {
        return loansClient.getLoanDetails(customerId);
    }

    public List<Card> getCustomerCards(final int customerId) {
        return cardsClient.getCardDetails(customerId);
    }
}
