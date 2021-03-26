package com.cg.creditcardpayment.api;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.creditcardpayment.exception.AccountException;
import com.cg.creditcardpayment.model.AccountModel;
import com.cg.creditcardpayment.service.IAccountService;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {

	@Autowired
	private IAccountService accountService;
	
	
	@GetMapping("/getAll")
	public ResponseEntity<List<AccountModel>> findAll() {
		return ResponseEntity.ok(accountService.findAll());
	}
	
	@GetMapping("/getAccount/{accountnumber}")
	public ResponseEntity<AccountModel> findById(@PathVariable("accountnumber") String accountNumber){
		ResponseEntity<AccountModel> response=null;
		if(!(accountService.existsById(accountNumber)) || accountNumber==null) {
			response=new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			response=new ResponseEntity<>(accountService.findById(accountNumber),HttpStatus.FOUND);
		}
		return response;
	}
	
	@PostMapping("/addAccount")
	@Transactional
	public ResponseEntity<String> add(@RequestBody AccountModel account) throws AccountException {
		ResponseEntity<String> response=null;
		if(account==null) {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			account=accountService.add(account);
			response= new ResponseEntity<>("Account is Added",HttpStatus.CREATED);
		}
		return response;
	}
	
	@DeleteMapping("/deleteAccount/{accountnumber}")
	@Transactional
	public ResponseEntity<String> deleteUser(@PathVariable("accountnumber") String accountNumber) {
		ResponseEntity<String> response=null;
		AccountModel account=accountService.findById(accountNumber);
		if(account==null) {
			response = new ResponseEntity<>("Account Doesnot Exists",HttpStatus.NOT_FOUND);
		}else {
			accountService.deleteById(accountNumber);
			response=new ResponseEntity<>("Account Deleted",HttpStatus.OK);
		}
		return response;
	}
	
	@PutMapping("/updateAccount")
	@Transactional
	public ResponseEntity<AccountModel> updateAccount(@RequestBody AccountModel account) throws AccountException{
		ResponseEntity<AccountModel> response=null;
		if(account==null) {
			response=new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			account =accountService.save(account);
			response =new ResponseEntity<>(account,HttpStatus.OK);
		}
		
		return response;
	}
	
}
