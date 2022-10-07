package org.mnp.accountservice.service.client;

import org.mnp.accountservice.model.Loan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("loans")
public interface LoansClient {
    @RequestMapping(method = RequestMethod.GET, value = "/{customerId}", consumes = "application/json")
    List<Loan> getLoanDetails(@PathVariable("customerId") int customerId);
}
