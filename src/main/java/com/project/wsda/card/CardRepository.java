package com.project.wsda.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends JpaRepository<Card, Integer> {

    Card findCardById(Integer id);

    @Modifying()
    @Query(value = "update Card as c set c.credit=?2 where c.id=?1")
    void updateCreditById(Integer id, Integer credit);

    @Modifying
    @Query(value = "update Card as c set c.state=?2 where c.id=?1")
    void updateStateById(Integer id, String state);
}
