package jdbc;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import jdbc.Bank;
import jdbc.Bank2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:jdbc/config.xml")
public class BankTest {


	@Autowired
	@Qualifier("bank")
	Bank bank;
	
	@Autowired
	@Qualifier("bank2")
	Bank2 bank2;
	
	@Test
	@Transactional
	public void testWithdraw() throws SQLException, Exception{
        int account = 10;
		long balance = bank.getBalance ( account );
        System.out.println ( "Balance of account "+account+" is: " + balance );
        int amount = 100;
        System.out.println ( "Withdrawing "+amount+" of account "+account+"..." );

		bank.withdraw ( account , amount );
		long  balance2 = bank.getBalance ( account );
        System.out.println ( "New balance of account "+account+" is: " + balance2 );
        assertEquals(balance2,balance-amount );
        
        System.out.println ( "====================华丽的分隔符============================" );
        //bank2
        account =20;
        balance = bank2.getBalance ( account );
        System.out.println ( "Balance of account "+account+" is: " + balance );
        amount = 100;
        System.out.println ( "deposit "+amount+" of account "+account+"..." );
        //bank2操作失败，bank1将回滚
		bank2.deposit(account, amount);
	    balance2 = bank2.getBalance ( account );
        System.out.println ( "New balance of account "+account+" is: " + balance2);
        
        assertEquals(balance2,balance+amount );

	}
	
}
