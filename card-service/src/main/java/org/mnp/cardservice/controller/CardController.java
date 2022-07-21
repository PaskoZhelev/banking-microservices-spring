package org.mnp.cardservice.controller;

import org.mnp.cardservice.model.Card;
import org.mnp.cardservice.repository.CardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardRepository cardRepository;

    public CardController(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        return new ResponseEntity<>(cardRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<Card>> getLoansByCustomerId(@PathVariable(value = "customerId") final int customerId) {
        final List<Card> loans = cardRepository.findByCustomerId(customerId);

        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        try {
            Card newCard = cardRepository.save(card);
            return new ResponseEntity<>(newCard, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
