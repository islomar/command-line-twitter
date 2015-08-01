package com.islomar.parrotter.feature;

import com.islomar.parrotter.actions.Command;
import com.islomar.parrotter.app.ParrotterApplication;
import com.islomar.parrotter.infrastructure.Console;
import com.islomar.parrotter.infrastructure.ScannerProxy;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.fail;

@Test
public class FollowUserTimelineFeature extends BaseFeature {

  private static final java.time.Instant NOW = Instant.now();
  private static final java.time.Instant SAVED_ALICE_MESSAGE_TIME = NOW;
  private static final Instant SAVED_BOB_MESSAGE_TIME = NOW.plus(2, ChronoUnit.MINUTES);


  @Mock private ScannerProxy scannerProxy;
  @Mock private Console console;
  @Mock private Clock clock;

  @BeforeMethod
  public void setUpMethod() {
    initMocks(this);
  }

  public void a_user_follows_another_user() {

    given(clock.instant()).willReturn(SAVED_ALICE_MESSAGE_TIME,
                                      SAVED_BOB_MESSAGE_TIME);

    given(scannerProxy.nextLine())
        .willReturn("Alice follows Bob")
        .willThrow(InterruptedException.class);
    List<Command> commandList = generateCommands(clock, console);

    try {
      ParrotterApplication parrotterApplication = new ParrotterApplication(commandList, scannerProxy);
      parrotterApplication.run();
      fail();
    } catch (Exception ex) {
      verify(console, never()).printMessage(anyString());
    }
  }


}
