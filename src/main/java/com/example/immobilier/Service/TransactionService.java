package com.example.immobilier.Service;

import com.example.immobilier.Entity.Transaction;
import com.example.immobilier.Entity.ExtraType.TypeTransaction;
import com.example.immobilier.Repository.TransactionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    public TransactionRepository transactionRepository;
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(int id) {
        Optional<Transaction> optionalTransactions =  transactionRepository.findById(id);
        return optionalTransactions.orElse(null);
    }

    public List<Transaction> searchTransactionsByTypeTransaction(TypeTransaction value) {
        Optional<List<Transaction>> optionalTransactions = transactionRepository.findByTypeTransaction(value);
        return optionalTransactions.orElse(null);
    }

    public List<Transaction> searchTransactionsByDateTransaction(String value) {
        Optional<List<Transaction>> optionalTransactions = transactionRepository.findByDateTransaction(value);
        return optionalTransactions.orElse(null);
    }

    public List<Transaction> searchTransactionsByMontant(double value) {
        Optional<List<Transaction>> optionalTransactions = transactionRepository.findByMontant(value);
        return optionalTransactions.orElse(null);
    }

    @Transactional
    public void deleteTransactionById(int id) throws Exception {
        try {
            transactionRepository.deleteById(id);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void deleteAllTransactions() throws Exception {
        try {
            transactionRepository.deleteAll();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public String getAssociateNameByContractId(int contractId) {
        return transactionRepository.findAssociateNameByIdContrat(contractId);
    }

    @Transactional
    public Transaction updateTransaction(int TransactionId,Transaction transaction) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(TransactionId);
        if(optionalTransaction.isEmpty()){
            throw new IllegalStateException(
                    "Transaction with id "+TransactionId+" does not exists"
            );
        }
        var updateTransaction = Transaction.builder()
                .typeTransaction(transaction.getTypeTransaction())
                .dateTransaction(transaction.getDateTransaction())
                .montant(transaction.getMontant())
                .contratAssocie(transaction.getContratAssocie())
                .build();

        return transactionRepository.save(updateTransaction);
    }
}
