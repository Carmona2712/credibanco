package com.nexos.credibanco.repository;

import com.nexos.credibanco.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionRepository extends JpaRepository<Transaction,Long> {
}
