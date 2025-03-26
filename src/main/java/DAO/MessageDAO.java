package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

  public Message insertMessage(Message message) {
    Connection conn = ConnectionUtil.getConnection();
    try {
      String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
      PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setInt(1, message.getPosted_by());
      ps.setString(2, message.getMessage_text());
      ps.setLong(3, message.getTime_posted_epoch());

      ps.executeUpdate();
      ResultSet pkeyResultSet = ps.getGeneratedKeys();
      if (pkeyResultSet.next()) {
        int message_id = pkeyResultSet.getInt(1);
        return new Message(message_id, message.getPosted_by(), message.getMessage_text(),
            message.getTime_posted_epoch());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public List<Message> getAllMessages() {
    Connection conn = ConnectionUtil.getConnection();
    List<Message> messages = new ArrayList<>();
    try {
      String sql = "SELECT * FROM message;";
      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),
            rs.getLong("time_posted_epoch"));
        messages.add(message);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return messages;
  }

  public Message getMessageById(int id) {
    Connection conn = ConnectionUtil.getConnection();
    try {
      String sql = "SELECT * FROM message WHERE message_id = ?;";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),
            rs.getLong("time_posted_epoch"));
        return message;
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public List<Message> getAllMessagesByAccountId(int id) {
    Connection conn = ConnectionUtil.getConnection();
    List<Message> messages = new ArrayList<>();
    try {
      String sql = "SELECT * FROM message WHERE account_id = ?;";
      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),
            rs.getLong("time_posted_epoch"));
        messages.add(message);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return messages;
  }

  public void deleteMessage(int id) {
    Connection conn = ConnectionUtil.getConnection();
    try {
      String sql = "DELETE FROM message WHERE message_id = ?;";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);

      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void updateMessage(int id, String message_text) {
    Connection conn = ConnectionUtil.getConnection();
    try {
      String sql = "UPDATE message SET message_text=? WHERE message_id = ?;";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, message_text);
      ps.setInt(2, id);

      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
