package com.islomar.parrotter.services;

import com.islomar.parrotter.infrastructure.repositories.MessageRepository;

import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


@Test
public class MessageServiceTest {

  private static final String ALICE = "Alice";

  @Mock
  MessageRepository messageRepository;

  private MessageService messageService;

  @BeforeClass
  public void setUp() {
    initMocks(this);
    messageService = new MessageService(messageRepository);
  }

  public void return_all_the_messages_for_a_user() {

    given(messageRepository.findAllMessagesForUser(ALICE)).willReturn(asList(new String()));

    messageService.findAllMessagesFor(ALICE);

    verify(messageRepository).findAllMessagesForUser(ALICE);
  }

}
