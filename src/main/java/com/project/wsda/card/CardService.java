package com.project.wsda.card;

public interface CardService {

    void saveCard(CardDto cardDto);

    Card findCardById(Integer id);

    void updateCreditById(Integer id, Integer credit);

    void updateStateById(Integer id, String state);
}
