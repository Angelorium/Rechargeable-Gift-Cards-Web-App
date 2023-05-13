package com.project.wsda.card;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Card> findAllCards(){
        List<Card> cards = cardRepository.findAll();
        System.out.println(cards);
        return cardRepository.findAll();
    }
}
