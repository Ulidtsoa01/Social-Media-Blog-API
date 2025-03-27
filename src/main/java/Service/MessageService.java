package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
  private MessageDAO messageDAO;

  public MessageService() {
    messageDAO = new MessageDAO();
  }

  public MessageService(MessageDAO messageDAO) {
    this.messageDAO = messageDAO;
  }

  public Message createMessage(Message msg) {
    String msg_txt = msg.getMessage_text();
    if (msg_txt == null || msg_txt.isEmpty() || msg_txt.length() >= 255) {
      return null;
    }

    if (messageDAO.getAllMessagesByAccountId(msg.getPosted_by()).size() > 0) {
      messageDAO.insertMessage(msg);
      return messageDAO.insertMessage(msg);
    }
    return null;
  }

  public List<Message> getAllMessages() {
    return messageDAO.getAllMessages();
  }

  public Message getMessageById(int id) {
    return messageDAO.getMessageById(id);
  }

  public Message deleteMessage(int id) {
    Message msg = messageDAO.getMessageById(id);
    if (msg != null) {
      messageDAO.deleteMessage(id);
      return msg;
    }
    return null;
  }

  public Message updateMessage(int id, String msg_txt) {
    if (msg_txt == null || msg_txt.isEmpty() || msg_txt.length() >= 255) {
      return null;
    }
    messageDAO.updateMessage(id, msg_txt);
    return messageDAO.getMessageById(id);
  }

  public List<Message> getAllMessagesByAccountId(int id) {
    return messageDAO.getAllMessagesByAccountId(id);
  }

}
