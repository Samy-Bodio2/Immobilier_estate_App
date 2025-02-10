package com.example.immobilier.Repository;

import com.example.immobilier.Entity.Contrat;
import com.example.immobilier.Entity.Transaction;
import com.example.immobilier.Entity.ExtraType.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<List<Transaction>> findByTypeTransaction(TypeTransaction typeTransaction);
    Optional<List<Transaction>> findByDateTransaction(String dateTransaction);
    Optional<List<Transaction>> findByMontant(double montant);
    @Query("SELECT c.associateName FROM Contrat c WHERE c.idContrat = :idContrat")
    String findAssociateNameByIdContrat(@Param("idContrat") int idContrat);
}
