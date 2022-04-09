package com.domanov.statisticservice.repository;

import com.domanov.statisticservice.model.MoneyTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseHistoryRepository extends JpaRepository<MoneyTransfer, Integer> {
}
