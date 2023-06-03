package com.project.wsda.card;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CardServiceImp implements CardService{

    private final CardRepository cardRepository;

    @Override
    public void saveCard(CardDto cardDto){
        Card card = new Card();
        card.setCredit(cardDto.getCredit());
        card.setState(cardDto.getState());
        cardRepository.save(card);
    }

    @Override
    public Card findCardById(Integer id){
        return cardRepository.findCardById(id);
    }

    @Override
    @Transactional
    public void updateCreditById(Integer id, Integer credit){
        cardRepository.updateCreditById(id, credit);
    }

    @Override
    @Transactional
    public void updateStateById(Integer id, String state){
        cardRepository.updateStateById(id, state);
    }
}
