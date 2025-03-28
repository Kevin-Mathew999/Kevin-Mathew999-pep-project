package Service;

import Model.Account;
import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {

    MessageDAO messageDAO;
    AccountDAO accountDAO;

    // Constructor for AccountService which instantiates AccountDAO

    public AccountService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }
    
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account userRegistration(Account account){
        if(!account.getUsername().isEmpty() && account.getUsername() != null){
            if(account.getPassword().length() >= 4){
                if(!accountDAO.doesUsernameExist(account.getUsername()) ){
                    Account registered = accountDAO.insertAccount(account);
                    return registered;
                    
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
    
}
