package com.islomar.parrotter.actions;

import com.islomar.parrotter.model.MessageOutput;
import com.islomar.parrotter.model.InMemoryMessageRepository;
import com.islomar.parrotter.infrastructure.repositories.MessageRepository;
import com.islomar.parrotter.model.Message;
import com.islomar.parrotter.services.MessageService;

import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Clock;
import java.time.Instant;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@Test
public class ViewUserTimelineTest {

  private static final String ALICE = "Alice";
  private static final String NON_EXISTING_USER = "NonExistingUser";
  public static final String EMPTY_TEXT = "";

  @Mock
  MessageOutput messageOutput;

  @Mock
  MessageRepository messageRepository;

  private ViewUserTimeline viewUserTimeline;
  private PublishMessage publishMessage;

  @BeforeClass
  public void setUp() {
    initMocks(this);

    MessageService messageService = new MessageService(messageOutput, new InMemoryMessageRepository(Clock.systemUTC()));

    viewUserTimeline = new ViewUserTimeline(messageOutput, messageService);
    publishMessage = new PublishMessage();
  }

  public void no_message_shown_for_non_existing_user() {

    viewUserTimeline.view(NON_EXISTING_USER);
    Message emptyMessage = new Message(NON_EXISTING_USER, EMPTY_TEXT, Instant.now());

    verify(messageOutput).printMessage(emptyMessage);
  }

  public void given_that_Alice_published_one_message_When_I_view_her_messages_Then_I_see_it() {

    String messageText = "Hello world";
    Message helloWorldMessage = new Message(ALICE, messageText, Instant.now());
    publishMessage.execute(helloWorldMessage);

    viewUserTimeline.view(ALICE);

    verify(messageOutput).printMessage(helloWorldMessage);
  }
}
