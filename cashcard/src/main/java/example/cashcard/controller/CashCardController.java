package example.cashcard.controller;

import example.cashcard.repository.CashCardRepository;
import example.cashcard.dto.CashCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    private CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    /**
     * controller method for fetching the cash card with selected id
     *
     * @param requestedId
     * @return HTTP status ok with the requested cash card in the body
     */
    @GetMapping("/{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        Optional<CashCard> optionalCashCard = cashCardRepository.findById(requestedId);
        return optionalCashCard.isPresent() ? ResponseEntity.ok(optionalCashCard.get()) : ResponseEntity.notFound().build();
    }

    /**
     * controller method for creating a cash card with unique id
     *
     * @param newCashCard
     * @return HTTP created status
     */
    @PostMapping
    private ResponseEntity createCashCard(@RequestBody CashCard newCashCard, UriComponentsBuilder ucb) {
        CashCard savedCashCard = cashCardRepository.save(newCashCard);
        URI locationOfNewCashCard = ucb.path("/cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created((locationOfNewCashCard)).build();
    }

    @GetMapping
    public ResponseEntity<List<CashCard>> findAll(Pageable pageable) {
        Page<CashCard> page = cashCardRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC,"amount"))));
        return ResponseEntity.ok(page.getContent());
    }
}
