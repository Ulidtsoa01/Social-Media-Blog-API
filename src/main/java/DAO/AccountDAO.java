package DAO;

import Util.ConnectionUtil;
import Model.Account;
import Model.Message;

import java.sql.*;

public class AccountDAO {

  public Account insertAccount(Account account) {
    Connection conn = ConnectionUtil.getConnection();
    try {
      String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
      PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, account.getUsername());
      ps.setString(2, account.getPassword());

      ps.executeUpdate();
      ResultSet pkeyResultSet = ps.getGeneratedKeys();
      if (pkeyResultSet.next()) {
        int account_id = pkeyResultSet.getInt(1);
        return new Account(account_id, account.getUsername(), account.getPassword());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public Account getAccountByUsername(String username) {
    Connection conn = ConnectionUtil.getConnection();
    try {
      String sql = "SELECT * FROM account WHERE username = ?;";

      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, username);

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
        return account;
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public Account getAccountByUsernameAndPassword(String username, String password) {
    Connection conn = ConnectionUtil.getConnection();
    try {
      String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";

      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, username);
      ps.setString(2, password);

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
        return account;
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
}
