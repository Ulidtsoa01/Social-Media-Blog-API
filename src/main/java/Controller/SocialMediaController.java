package Controller;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.*;
import Service.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages",
                this::getAllMessagesByAccountHandler);
        app.get("/", this::defaultHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     */

    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account added = accountService.registerUser(account);
        if (added == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(added));
        }
    }

    public void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account retrieve = accountService.login(account);
        if (retrieve == null) {
            ctx.status(401);
        } else {
            ctx.json(mapper.writeValueAsString(retrieve));
        }
    }

    private void postMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message added = messageService.createMessage(message);
        if (added == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(added));
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        if (message == null) {
            ctx.json("");
        } else {
            ctx.json(message);
        }
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message retrieved = messageService.deleteMessage(message_id);
        if (retrieved == null) {
            ctx.json("");
        } else {
            ctx.json(mapper.writeValueAsString(retrieved));
        }
    }

    private void patchMessageHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(ctx.body(), Map.class);
        String message_text = map.get("message_text");
        Message updated = messageService.updateMessage(message_id, message_text);
        if (updated == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(updated));
        }
    }

    private void getAllMessagesByAccountHandler(Context ctx) {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountId(account_id);
        ctx.json(messages);
    }

    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void defaultHandler(Context context) {
        context.result("sample text");
    }
}