package jdbc;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class XAService {
	
	@Resource
	private Bank bank;
	
	@Resource
	private Bank2 bank2;
	
	@Transactional
	public void Transfer(int account1,int account2,int  amount) throws Exception{
		long balance = bank.getBalance ( account1 );
        System.out.println ( "Balance of account "+account1+" is: " + balance );
        System.out.println ( "Withdrawing "+amount+" of account "+account1+"..." );

		bank.withdraw ( account1 , amount );
		long  balance2 = bank.getBalance ( account1 );
        System.out.println ( "New balance of account "+account1+" is: " + balance2 );
        
        System.out.println ( "====================华丽的分隔符============================" );
        //bank2
        balance = bank2.getBalance ( account2 );
        System.out.println ( "Balance of account "+account2+" is: " + balance );
        System.out.println ( "deposit "+amount+" of account "+account2+"..." );

		bank2.deposit(account2, amount);
	    balance2 = bank2.getBalance ( account2 );
        System.out.println ( "New balance of account "+account2+" is: " + balance2);
        
		
		
	}
	
}
