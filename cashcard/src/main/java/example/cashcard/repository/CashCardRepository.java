package example.cashcard.repository;

import example.cashcard.dto.CashCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CashCardRepository extends CrudRepository<CashCard, Long> , PagingAndSortingRepository<CashCard,Long> {
}
