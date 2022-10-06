package org.mnp.accountservice.service.client;

import org.mnp.accountservice.model.Card;
import org.mnp.accountservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("cards")
public interface CardsClient {
    @RequestMapping(method = RequestMethod.GET, value = "/cards/{customerId}", consumes = "application/json")
    List<Card> getCardDetails(@PathVariable("customerId") int customerId);
}
