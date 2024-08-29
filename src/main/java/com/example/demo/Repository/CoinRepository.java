package com.example.demo.Repository;

import com.example.demo.Entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin, String> {

    Coin findCoinByCode(String code);

    void deleteByCode(String code);
}
