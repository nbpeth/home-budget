package com.homebudget.controller;

import com.homebudget.domain.Expense;
import com.homebudget.repository.ExpenseRepository;
import com.homebudget.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@RestController("/expenses")
public class BudgetController {
    @Autowired
    ExpenseService expenseService;

    @PostMapping("/")
    public ResponseEntity<Expense> submitExpense(@ModelAttribute Expense expense){

        expenseService.saveExpense(expense);

        return new ResponseEntity<>(expense, HttpStatus.CREATED);
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<List<Expense>> getExpense(
        @PathVariable String month,
        @PathVariable String day,
        @PathVariable String year){

        List<Expense> expense = expenseService.getExpense(month, day, year);

        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Expense>> getAllExpenses(){
        return new ResponseEntity<>(expenseService.getAllExpenses(), HttpStatus.CREATED);
    }

}
