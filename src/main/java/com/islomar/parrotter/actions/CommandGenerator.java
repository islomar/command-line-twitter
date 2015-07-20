package com.islomar.parrotter.actions;

import com.islomar.parrotter.model.user.User;
import com.islomar.parrotter.services.FollowUserService;
import com.islomar.parrotter.services.PostMessageService;
import com.islomar.parrotter.services.ReadUserTimelineService;
import com.islomar.parrotter.services.ShowUserWallService;
import com.islomar.parrotter.services.UserService;

import static com.islomar.parrotter.actions.CommandType.FOLLOWS;
import static com.islomar.parrotter.actions.CommandType.POST;
import static com.islomar.parrotter.actions.CommandType.WALL;

public class CommandGenerator {

  private final PostMessageService postMessageService;
  private final ReadUserTimelineService readUserTimelineService;
  private final FollowUserService followUserService;
  private final ShowUserWallService showUserWallService;
  private final UserService userService;

  public CommandGenerator(UserService userService, PostMessageService postMessageService, ReadUserTimelineService readUserTimelineService, FollowUserService followUserService, ShowUserWallService showUserWallService) {

    this.userService = userService;
    this.postMessageService = postMessageService;
    this.readUserTimelineService = readUserTimelineService;
    this.followUserService = followUserService;
    this.showUserWallService = showUserWallService;
  }

  public Command createCommandFromInputLine(String inputCommandLine) {

    Command command = new NothingToExecuteCommand();
    if (inputCommandLine.contains(POST.symbol())) {
      command = generatePublishMessageCommand(inputCommandLine);
    } else if (inputCommandLine.contains(WALL.symbol())) {
      command = generateShowUserWallCommand(inputCommandLine);
    } else if (inputCommandLine.contains(FOLLOWS.symbol())) {
      command = generateFollowUserCommand(inputCommandLine, followUserService);
    } else {
      command = generateViewUserTimelineCommand(inputCommandLine);
    }

    return command;
  }

  private Command generateViewUserTimelineCommand(String username) {
    return new ReadUserTimelineCommand(readUserTimelineService, username);
  }

  private Command generateFollowUserCommand(String inputCommandLine, FollowUserService followUserService) {
    String[] inputArguments = inputCommandLine.split(FOLLOWS.symbol());
    String followingUsername = inputArguments[0].trim();
    String followedUsername = inputArguments[1].trim();

    User followingUser = new User(followingUsername);

    followingUser.follow(followedUsername);

    return new FollowUserCommand(userService, followUserService, followingUsername, followedUsername);
  }

  private Command generateShowUserWallCommand(String inputCommandLine) {
    String[] inputArguments = inputCommandLine.split(WALL.symbol());
    String username = inputArguments[0].trim();
    return new ShowWallCommand(showUserWallService, username);
  }

  private Command generatePublishMessageCommand(String inputCommandLine) {

    String[] inputArguments = inputCommandLine.split(POST.symbol());
    String username = inputArguments[0].trim();
    String textMessage = inputArguments[1].trim();
    return new PostMessageCommand(userService, postMessageService, username, textMessage);
  }
}
