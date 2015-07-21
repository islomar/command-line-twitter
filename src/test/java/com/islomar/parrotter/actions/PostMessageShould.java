package com.islomar.parrotter.actions;

import com.islomar.parrotter.model.message.MessageService;
import com.islomar.parrotter.model.user.UserService;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@Test
public class PostMessageShould {

  private static final String ALICE = "Alice";
  private static final String MESSAGE_TEXT = "I love the weather today";

  @Mock UserService userService;
  @Mock MessageService messageService;


  @BeforeMethod
  public void setUpMethod() {
    initMocks(this);
  }

  public void save_the_user_and_post_the_message() {
    PostMessage postMessage = new PostMessage(userService, messageService, ALICE, MESSAGE_TEXT);

    postMessage.execute();

    verify(userService).saveUser(ALICE);
    verify(messageService).saveMessage(ALICE, MESSAGE_TEXT);
  }

}
