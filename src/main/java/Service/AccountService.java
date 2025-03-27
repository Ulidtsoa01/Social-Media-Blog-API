package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
  private AccountDAO accountDAO;

  public AccountService() {
    accountDAO = new AccountDAO();
  }

  public AccountService(AccountDAO accountDAO) {
    this.accountDAO = accountDAO;
  }

  public Account registerUser(Account acct) {
    if (acct.getUsername() == null | acct.getUsername().isEmpty() | acct.getPassword().length() < 4) {
      return null;
    }
    if (accountDAO.getAccountByUsername(acct.getUsername()) == null) {
      return accountDAO.insertAccount(acct);
    }

    return null;
  }

  public Account login(Account acct) {
    return accountDAO.getAccountByUsernameAndPassword(acct.getUsername(), acct.getPassword());
  }
}
