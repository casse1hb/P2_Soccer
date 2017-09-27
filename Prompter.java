import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Prompter {


    // Takes the user's input, captures and returns it
    public static String read() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }



    public static void displayMainMenu() {
        String mainMenu = "\nMAIN MENU\n===========================\n";
        mainMenu += "1) Create new team\n";
        mainMenu += "2) Add players to team\n";
        mainMenu += "3) Remove players from team\n";
        mainMenu += "4) Display all current teams\n";
        mainMenu += ("5) View team rosters by height\n");
        mainMenu += ("6) View League Balance Report\n");
        mainMenu += ("7) Print team rosters\n");
        mainMenu += ("8) Delete existing team\n");
        mainMenu += ("9) Exit\n");
        mainMenu += ("===========================");
        System.out.println(mainMenu);
    }




    public static int promptMenuChoice() {
        System.out.print("\nEnter a menu item number: ");
        try {
            String userInput = read();
            if (userInput != null) {
                return Integer.parseInt(userInput);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Menu item selection must be a number.");
        }
        return 0;
    }




    public static String[] promptTeamCreation() {
        String[] responses = new String[2];
        String teamName;
        String coach;

        do {
            System.out.print("Please enter a team name or type 'BACK': ");
            teamName = read();
            responses[0] = teamName;

            if (teamName != null && teamName.equalsIgnoreCase("back")) {
                responses[0] = "back";
                break;
            }


        } while (teamName == null || teamName.length() == 0);

        if (!teamName.equalsIgnoreCase("back")) {

            do {
                System.out.print("Enter the name of this team's coach or type 'BACK': ");
                coach = read();
                responses[1] = coach;

                if (coach != null && coach.equalsIgnoreCase("back")) {
                    responses[0] = "back";
                    break;
                }

            } while (coach == null || coach.length() == 0);

        }
        return responses;
    }



    public static void displayTeamEditMenu(List<Team> seasonTeams) {
        if (seasonTeams.size() == 0) {
            System.out.println("There are currently no teams to to edit.");
            System.out.println("Please create a team before continuing.");
        } else {
            System.out.println("\nAVAILABLE TEAMS\n===========================");
            Collections.sort(seasonTeams);
            for (int i = 0; i < seasonTeams.size(); i++) {
                String message = i + 1 + ") ";
                message += seasonTeams.get(i).mTeamName + "\n";
                System.out.print(message);
            }
        }
    }



    public static int promptTeamSelection() {
        System.out.printf("\nEnter the number of a team or type 'BACK': ");
        String response = read();

        if (response != null) {

            if (response.equalsIgnoreCase("back")) {
                return -100;
            }

            return (Integer.parseInt(response) - 1);
        }
        return -1;
    }



    public static void displayAllPlayers(Player[] players) {
        Arrays.sort(players);
        System.out.println("\nAVAILABLE PLAYERS\n===========================");
        for (int i = 0; i < players.length; i++) {
            String message = i + 1 + ") ";

            message += players[i].getFirstName() + " " + players[i].getLastName() + ", ";
            message += players[i].getHeightInInches() + "\", ";

            if (players[i].isPreviousExperience()) {
                message += "Previous Exp." + ", ";
            } else {
                message += "No Previous Exp." + ", ";
            }

            if (players[i].getAvailablity()) {
                message += "Available\n";
            } else {
                message += "Unavailable\n";
            }
            System.out.print(message);
        }
    }



    public static void displayTeamPlayers(Team team) {

        if (team.mTeamPlayers.size() > 0) {
            System.out.println("\nPLAYERS ON " + team.mTeamName.toUpperCase());
            System.out.println("===========================");

            for (int i = 0; i < team.mTeamPlayers.size(); i++) {
                String message = i + 1 + ") ";
                message += team.mTeamPlayers.get(i).getFirstName() + " " + team.mTeamPlayers.get(i).getLastName() + ", ";
                message += team.mTeamPlayers.get(i).getHeightInInches() + "\", ";
                if (team.mTeamPlayers.get(i).isPreviousExperience()) {
                    message += "Previous Exp." + "\n";
                } else {
                    message += "No Previous Exp." + "\n";
                }
                System.out.print(message);
            }
        } else {
            System.out.println("This team currently has no players.");
        }
    }



    public static int promptPlayerSelection() {
        System.out.printf("\nEnter the player number or type 'BACK': ");
        String response = read();
        if (response != null) {

            if (response.equalsIgnoreCase("back")) {
                return -100;
            }

            return (Integer.parseInt(response) - 1);
        } else {
            return -100;
        }
    }



    public static boolean promptContinue() {
        System.out.print("Continue adding to removal list? ");
        String response = read();
        return response != null && (response.equalsIgnoreCase("yes") ||
               response.equalsIgnoreCase("yeah") || response.equalsIgnoreCase("y"));
    }



    public static void displayPlayers(Team teamName) {
        System.out.println("\nPLAYERS ON " + teamName.mTeamName.toUpperCase());
        System.out.println("==========================");
        for (Player player : teamName.mTeamPlayers) {
            System.out.println(player.getFirstName() + " " + player.getLastName() + ", " + player.getHeightInInches() + "\"");
        }
        System.out.println("==========================\n");
    }



    public static void displayTeams(List<Team> seasonTeams) {
        System.out.println("\nCURRENT TEAMS");
        System.out.println("==========================");
        for (Team team : seasonTeams) {
            System.out.printf("Team Name: ");
            System.out.println(team.mTeamName);
            System.out.printf("Coach: ");
            System.out.println(team.mCoach);

            if (team.mTeamPlayers.size() != 0) {
                displayPlayers(team);
            } else {
                System.out.println();
            }
        }
    }

    public static void iteratePlayerList(List<Player> category) {
        for (int i = 0; i < category.size(); i++) {
            System.out.println((i + 1) + ") " + category.get(i).getFirstName() + " " + category.get(i).getLastName() +
                    ", " + category.get(i).getHeightInInches() + "\"");
        }
    }


    public static boolean promptDeletionCheck() {
        System.out.print("Are you sure you would like to delete this team? ");
        String response = read();

        return response != null && (response.equalsIgnoreCase("yes") ||
               response.equalsIgnoreCase("yeah") || response.equalsIgnoreCase("y"));
    }




}
