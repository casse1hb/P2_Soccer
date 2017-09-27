import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeagueManager {
  public static List<Team> seasonTeams = new ArrayList<>();

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);


    int userMenuSelection;

    do {
      // Displays the main menu and allows the user to enter an item number
      Prompter.displayMainMenu();
      userMenuSelection = Prompter.promptMenuChoice();

      switch (userMenuSelection) {

        case 1:
          int availablePlayers = 0;
          for (Player player : players) {
            if (player.getAvailablity()) {
              availablePlayers++;
            }
          }

          System.out.println("\n" + availablePlayers + " available players.");
          System.out.println(seasonTeams.size() + " existing teams.\n");

          /* If there are no available players, or there are as many teams as available players,
             prevent the user from creating more teams */
          if (availablePlayers == 0 || seasonTeams.size() == availablePlayers) {
            System.out.println("There are not enough available players to establish a new team.");
            break;
          }

          String[] teamInfo = Prompter.promptTeamCreation();
          if (teamInfo != null) {

            if (teamInfo[0].equalsIgnoreCase("back")) {
              break;
            } else {
                Team team = new Team(teamInfo[0], teamInfo[1]);
                System.out.println("Team successfully created.");
                seasonTeams.add(team);
              }
            }
            break;



        // Allows the user to add players to a team of choice
        case 2:
          if (seasonTeams.size() == 0) {
            System.out.println("There are currently no teams to which you can add players.");
            System.out.println("Please create a team before continuing.");
          }

          else {
            Prompter.displayAllPlayers(players);
            boolean invalid = true;

            do {
              try {
                int playerIndex = Prompter.promptPlayerSelection();

                // Allows the user to back out of the submenu
                if (playerIndex == -100) {
                  break;
                } else if (!players[playerIndex].getAvailablity()) {
                  System.out.println("This player is already on a team and is not currently available.");
                  break;
                }

                System.out.println("\nThe selected player is " + players[playerIndex].getFirstName() + " " + players[playerIndex].getLastName());

                Prompter.displayTeamEditMenu(seasonTeams);
                int teamIndex = Prompter.promptTeamSelection();

                try {
                  // Allows the user to back out of the Team Edit menu
                  if (teamIndex == -100) {
                    break;
                  } else if (seasonTeams.get(teamIndex).mTeamPlayers.size() == 11) {
                    System.out.println("\nThis team already has the maximum amount of players allowable.");
                    System.out.println("In order to add new players, first remove those you wish to replace.");
                    break;
                  }

                  players[playerIndex].toggleAvailability();
                  seasonTeams.get(teamIndex).mTeamPlayers.add(players[playerIndex]);
                  invalid = false;

                } catch (IndexOutOfBoundsException | NumberFormatException exception) {
                    System.out.println("Invalid entry. Please enter a team number.");
                }


              } catch (ArrayIndexOutOfBoundsException | NumberFormatException exception) {
                System.out.println("Invalid entry. Please enter a player number.");
              }

            } while (invalid);

          }
          break;



        // Allows the user to remove players from an existing team
        case 3:
          if (seasonTeams.size() == 0) {
            System.out.println("There are currently no teams from which to remove players.");
          }

          else {
            Prompter.displayTeamEditMenu(seasonTeams);
            boolean invalidInput = false;

            do {
              try {
                int teamIndex = Prompter.promptTeamSelection();

                // Allows the user to back out of the Team Edit menu
                if (teamIndex == -100) {
                  break;
                } else if (seasonTeams.get(teamIndex).mTeamPlayers.size() == 0) {
                    System.out.println("This team currently has no players to remove.");
                    break;
                }

                Collections.sort(seasonTeams.get(teamIndex).mTeamPlayers);
                Prompter.displayTeamPlayers(seasonTeams.get(teamIndex));

                boolean keepRemoving = false;
                List<Integer> numbersToRemove = new ArrayList<>();

                do {
                  try {
                    int playerIndex = Prompter.promptPlayerSelection();

                    // Allows the user to back out of the Add Player menu
                    if (playerIndex == -100) {
                      break;
                    }

                    numbersToRemove.add(playerIndex);

                    if (Prompter.promptContinue()) {
                      if (seasonTeams.get(teamIndex).mTeamPlayers.size() > 0) {
                        keepRemoving = true;
                      } else {
                        System.out.println("This team currently has no players to remove.");
                      }
                    } else {
                      keepRemoving = false;
                    }

                  } catch (IndexOutOfBoundsException | NumberFormatException exception) {
                      invalidInput = true;
                      System.out.println("Invalid entry. Please enter a player number.");
                  }

                } while (keepRemoving);

                List<Player> playersToRemove = new ArrayList<>();
                for (int number : numbersToRemove) {
                  playersToRemove.add(seasonTeams.get(teamIndex).mTeamPlayers.get(number));
                  if (!seasonTeams.get(teamIndex).mTeamPlayers.get(number).getAvailablity()) {
                    seasonTeams.get(teamIndex).mTeamPlayers.get(number).toggleAvailability();
                  }
                }

                seasonTeams.get(teamIndex).mTeamPlayers.removeAll(playersToRemove);

              } catch (IndexOutOfBoundsException | NumberFormatException exception) {
                  invalidInput = true;
                  System.out.println("Invalid entry. Please enter a player number.");
              }

            } while (invalidInput);
          }
          break;



        // Displays existing teams
        case 4:
          if (seasonTeams.size() > 0) {
            Prompter.displayTeams(seasonTeams);
          } else {
            System.out.println("There currently are no teams.");
          }
          break;



        // View team reports sorted by height
        case 5:
          // Prompt team selection (available)
          if (seasonTeams.size() == 0) {
            System.out.println("There are currently no teams to view.");
          }

          else {
            boolean isInvalid = false;
            Prompter.displayTeamEditMenu(seasonTeams);

            do {

              try {
                int teamIndex = Prompter.promptTeamSelection();

                // Allows the user to back out of the Add Player menu
                if (teamIndex == -100) {
                  break;
                }


                // Sorts the list using height
                Collections.sort(seasonTeams.get(teamIndex).mTeamPlayers, new Player());
                List<Player> categoryOne = new ArrayList<>();
                List<Player> categoryTwo = new ArrayList<>();
                List<Player> categoryThree = new ArrayList<>();

                for (Player player : seasonTeams.get(teamIndex).mTeamPlayers) {

                  int height = player.getHeightInInches();
                  if (height < 38) {
                    categoryOne.add(player);
                  } else if (height >= 38 && height <= 43) {
                    categoryTwo.add(player);
                  } else {
                    categoryThree.add(player);
                  }

                }

                System.out.println("\n\nSHORTER THAN 38\"" + " (" + categoryOne.size() + " players)");
                System.out.println("=========================");
                Prompter.iteratePlayerList(categoryOne);

                System.out.println("\n\n38\" UP TO 43\"" + " (" + categoryTwo.size() + " players)");
                System.out.println("=========================");
                Prompter.iteratePlayerList(categoryTwo);

                System.out.println("\n\nTALLER THAN 43\"" + " (" + categoryThree.size() + " players)");
                System.out.println("=========================");
                Prompter.iteratePlayerList(categoryThree);
                System.out.println();


              } catch (IndexOutOfBoundsException | NumberFormatException exception) {
                isInvalid = true;
                System.out.println("Invalid entry. Please enter a player number.");
              }


            } while (isInvalid);


          }
          break;



        // League Balance Report
        case 6:
          // Determine percentage of experienced players
          if (seasonTeams.size() == 0) {
            System.out.println("There are currently no teams to view.");
          }

          else {
            System.out.println();
            for (Team team : seasonTeams) {

              float experiencedPlayers = 0;
              float totalTeamPlayers = team.mTeamPlayers.size();
              float percentExperienced;
              DecimalFormat dformat = new DecimalFormat("#%");


              if (totalTeamPlayers > 0) {

                for (Player player : team.mTeamPlayers) {
                  if (player.isPreviousExperience()) {
                    experiencedPlayers++;
                  }
                }

                percentExperienced = experiencedPlayers / totalTeamPlayers;

                String message = team.mTeamName + ": ";
                message += (int) experiencedPlayers + " experienced, ";
                message += (int) (totalTeamPlayers - experiencedPlayers) + " inexperienced | ";
                message += "Team Experience Level: " + dformat.format(percentExperienced);

                System.out.println(message);
              }
            }
          }
          break;



        // Allows the user to print a team roster organized alphabetically by name
        case 7:
          if (seasonTeams.size() == 0) {
            System.out.println("There are currently no teams to view.");
          }

          else {
            boolean isInvalid = false;

            do {
              try {
                Prompter.displayTeamEditMenu(seasonTeams);
                int teamIndex = Prompter.promptTeamSelection();

                // Allows the user to back out of the Add Player menu
                if (teamIndex == -100) {
                  break;
                }

                Collections.sort(seasonTeams.get(teamIndex).mTeamPlayers);
                for (int i = 0; i < 50; i++) System.out.println();
                Prompter.displayTeamPlayers(seasonTeams.get(teamIndex));


              } catch (IndexOutOfBoundsException | NumberFormatException exception) {
                isInvalid = true;
                System.out.println("Invalid entry. Please enter a player number.");
              }

            } while (isInvalid);

          }
          break;



        // Delete existing team
        case 8:

          if (seasonTeams.size() == 0) {
            System.out.println("There are currently no existing teams.");
            break;
          }

          Prompter.displayTeamEditMenu(seasonTeams);
          int teamIndex = Prompter.promptTeamSelection();
          boolean isInvalid = false;

          // Allows the user to back out of the Add Player menu
          if (teamIndex == -100) {
            break;
          }

          do {
            try {
              // user prompter to check to see if the user is sure they want to delete team
              if (Prompter.promptDeletionCheck()) {
                seasonTeams.remove(teamIndex);
                System.out.println("Team deleted.");
              }
            } catch (IndexOutOfBoundsException | NumberFormatException exception) {
              isInvalid = true;
              System.out.println("Invalid entry. Please enter a team number.");
            }
          } while (isInvalid);
          break;



        // Allows the user to exit the program
        case 9:
          System.out.println("Logging off... Goodbye.");
          System.exit(0);
          break;



        // Handles all cases outside menu options
        default:
          break;

      } // End of Switch

    } while (true); // End of Do While

  }

}
