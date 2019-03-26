package ru.atom.chat.client;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.chat.client.ChatClient;
import ru.atom.chat.server.ChatApplication;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChatApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ChatClientTest {
    private static String MY_NAME_IN_CHAT = "I_AM_STUPID";
    private static String MY_MESSAGE_TO_CHAT = "SOMEONE_KILL_ME";

    @Test
    public void login() throws IOException {
        Response response = ChatClient.login(MY_NAME_IN_CHAT);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200 || body.equals("Already logged in:("));
    }

    @Test
    public void viewChat() throws IOException {
        Response response = ChatClient.viewChat();
        System.out.println("[" + response + "]");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void viewOnline() throws IOException {
        Response loginResponse = ChatClient.login(MY_NAME_IN_CHAT);
        Response viewResponse = ChatClient.viewOnline();
        System.out.println("[" + viewResponse + "]");
        String responseBody = viewResponse.body().string();
        Assert.assertTrue(viewResponse.code() == 200 && responseBody.equals(MY_NAME_IN_CHAT));
    }

    @Test
    public void say() throws IOException {
        Response loginResponse = ChatClient.login(MY_NAME_IN_CHAT);
        System.out.println("[" + loginResponse + "]");
        Response sayResponse = ChatClient.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        System.out.println("[" + sayResponse + "]");
        System.out.println(sayResponse.body().string());
        Assert.assertEquals(200, sayResponse.code());
    }

    @Test
    public void logout() throws IOException {
        Response loginResponse = ChatClient.login(MY_NAME_IN_CHAT);
        System.out.println("[" + loginResponse + "]");
        Response logoutResponse = ChatClient.logout(MY_NAME_IN_CHAT);
        System.out.println("[" + logoutResponse + "]");
        System.out.println(logoutResponse.body().string());
        Assert.assertEquals(200, logoutResponse.code());
    }

    @Test
    public void deletingMessage_shouldDeleteSpecifiedMessage() throws IOException {
        Response loginResponse = ChatClient.login(MY_NAME_IN_CHAT);
        Response sayingResponse = ChatClient.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        Response recantResponse = ChatClient.recant(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        Assert.assertEquals(204, recantResponse.code());
    }

    @Test
    public void deletingMessage_shouldThrowError_ifMessageDoesNotExist() throws IOException {
        Response loginResponse = ChatClient.login(MY_NAME_IN_CHAT);
        Response recantResponse = ChatClient.recant(MY_NAME_IN_CHAT, "not_existing_message");
        Assert.assertTrue(recantResponse.code() == 400);

    }

    @Test
    public void deletingMessage_shouldThrowError_ifUserIsNotLoggedIn() throws IOException {
        Response recantResponse = ChatClient.recant(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        Assert.assertTrue(recantResponse.code() == 400);
    }

    @Test
    public void shouldClearAllChat() throws IOException {
        Response response = ChatClient.clear();
        System.out.println(response.code());
        Assert.assertTrue(response.code() == 204);
    }
}