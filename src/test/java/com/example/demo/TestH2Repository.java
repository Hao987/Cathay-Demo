package com.example.demo;

import com.example.demo.Entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<Coin,String> {
}
