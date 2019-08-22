import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

     static char [] array_of_position = nullifyArray();
     final static int size_of_field = 3;
     static boolean flag_of_game;
     static int number_of_moves = nullifyMoves();


     static Player player1;
     static Player player2;


    public static void main(String[] args) throws IOException, InterruptedException {


        printDescriprion();
        identifyPlayers();
        chooseSidesPlayers();
        logicOfGame();


    }

    private static void chooseSidesPlayers() {

        String line;
        System.out.print("Player1 choose side(\"X\" or \"O\"): ");

        while (true) {
            try (Scanner scanner = new Scanner(System.in)) {
                line = scanner.nextLine();
                if (line.equals("X")) {
                    initializationOfSides('X', 'O');
                    break;

                } else if (line.equals("O")) {
                    initializationOfSides('O', 'X');
                    break;

                } else {
                    throw new NumberFormatException();

                }
            } catch (NumberFormatException exception) {
                System.out.print("Incorrect input. Enter again(\"X\" or \"O\"): ");
            }
        }

    }

    private static void initializationOfSides(char side1, char side2){

        ((Player.Human) player1).setElement(side1);
        if (player2 instanceof Player.Human) {
            ((Player.Human) player2).setElement(side2);

        } else{
            ((Player.Bot) player2).setElement(side2);

        }
    }

    private static void identifyPlayers() {
        player1 = new Player().new Human();
        String line;

        System.out.print("Select game mode:(\nEnter \"1\" - game with a bot, " +
                         "\"2\" -  game for two): ");

            while (true) {
                try (Scanner scanner = new Scanner(System.in)) {
                    line = scanner.nextLine();
                    if (line.equals("1")) {
                        player2 = new Player().new Bot();
                        break;

                    } else if (line.equals("2")) {
                        player2 = new Player().new Human();
                        break;

                    } else{
                        throw new NumberFormatException();

                    }
                } catch (NumberFormatException exception) {
                    System.out.print("Incorrect input. Enter again(\"1\" or \"2\"): ");
                    continue;

                }
            }

    }

    private static void logicOfGame() throws IOException, InterruptedException {
        String line;

        while(!flag_of_game) {
            drawIndent();
            System.out.println("Start to game.");
            drawField(array_of_position);

            if(player2 instanceof Player.Bot) {
                playWithBot();
            } else {
                playWithHuman();
            }

            System.out.println("Do you want to play again?( y - \"Yes\", n - \"No\")");
            while (true) {
                try (Scanner scanner =  new Scanner(System.in)) {
                    line = scanner.nextLine();
                    if (line.equals("y")) {
                        changeFlags();
                        break;

                    } else if (line.equals("n")) {
                        System.out.println("Thank you for the game!");
                        break;

                    } else {
                        throw new NumberFormatException();

                    }
                } catch (NumberFormatException e) {
                    System.out.print("Something wrong. Try again: ");
                }
            }

        }
    }

    private static void changeFlags(){
        flag_of_game = false;
        array_of_position = nullifyArray();
        number_of_moves = nullifyMoves();
    }



    private static void playWithHuman() {
        while (!flag_of_game){
            if (returnElement(player1) == 'X' && number_of_moves % 2 == 0 ||
                    returnElement(player1) != 'X' && number_of_moves % 2 != 0) {
                array_of_position[enterPosition() - 1] = returnElement(player1);
                number_of_moves++;

            } else {
                array_of_position[enterPosition() - 1] = returnElement(player2);
                number_of_moves++;

            }

            drawIndent();
            drawField(array_of_position);

            if (resultOfGame()) {
                continue;
            }
        }
    }




    private static void playWithBot() throws InterruptedException {
        while (!flag_of_game){

            if(resultOfGame()){
                continue;
            }
            if (returnElement(player1) == 'X' && number_of_moves % 2 == 0 ||
                returnElement(player1) != 'X' && number_of_moves % 2 != 0) {
                array_of_position[enterPosition() - 1] = returnElement(player1);
                number_of_moves++;

            } else {
                array_of_position[((Player.Bot) player2).botPosition(array_of_position)] = returnElement(player2);
                number_of_moves++;

            }

            drawIndent();
            drawField(array_of_position);



        }
    }

    private static int enterPosition() {
        int your_choise = 0;

        System.out.print("Enter your position:  ");
        while (true) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                if ((your_choise = Integer.parseInt(bufferedReader.readLine())) < 1 || your_choise > 9) {
                    System.out.print("Enter another position(1 - 9): ");
                    continue;
                } else if (array_of_position[your_choise - 1] != ' ') {
                    System.out.print("This position has been defined.Try again: ");
                    continue;
                } else {
                    break;
                }
            } catch (NumberFormatException exception) {
                System.out.println("This isn't a valid value.Tre again: ");
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return your_choise;
    }



    private static char returnElement(Player this_player){
        if (this_player instanceof Player.Human)
            return ((Player.Human) this_player).getElement();
        else
            return ((Player.Bot) this_player).getElement();

    }


    private static boolean resultOfGame() {
        if (winning(player1)){
            System.out.println(returnElement(player1) + " win!");
            return flag_of_game = true;

        }

        if (winning(player2)){
            System.out.println(returnElement(player2) + " win!");
            return flag_of_game = true;
        }

        if (!haveMoves()){
            System.out.println("You have a tie.");
            return flag_of_game = true;
        }

        return flag_of_game;
    }



    private static char[] nullifyArray(){
        return new char[] {' ', ' ', ' ',
                           ' ', ' ', ' ',
                           ' ', ' ', ' '};
    }

    private static int nullifyMoves() {
        return 0;
    }


    private static boolean haveMoves() {
        for (char element : array_of_position) {
            if (element == ' ')
                return true;
        }
        return false;
    }

    private static boolean winning(Player this_player){

        char element = returnElement(this_player);
        int flag_of_end = 0;

        // horizontally
        for (int index = 0; index < size_of_field; index++) {
            for (int i = index; i < size_of_field; i++) {
                if (array_of_position[i] == element)
                    flag_of_end++;
            }

            if (flag_of_end == 3){
                return true;
            }
            flag_of_end = 0;
        }

        // vertically
        for (int index = 0; index < size_of_field; index++) {
            for (int i = index; i < size_of_field * size_of_field; i+=3) {
                if (array_of_position[i] == element)
                    flag_of_end++;
            }

            if (flag_of_end == 3){
                return true;
            }
            flag_of_end = 0;
        }

        // main diagonal
        for (int index = 0; index < (size_of_field * size_of_field); index+=4) {
            if (array_of_position[index] == element)
                flag_of_end++;
        }
        if (flag_of_end == 3){
            return true;
        }

        flag_of_end = 0;

        // antidiagonal
        for (int index = 2; index < size_of_field * size_of_field + 1; index+=2) {
            if (array_of_position[index] == element)
                flag_of_end++;
        }
        if (flag_of_end == 3){
            return true;
        }
        return false;

    }

    private static void drawIndent() {
        for (int i = 0; i < 10; i++) {
            System.out.println('\b');
        }
    }

    private static void drawField(char [] array) {
       int counter = 0;
            for (int index = 0; index < array.length; index++) {
                counter++;
                System.out.print("|-" + array[index] + "-");
                if (counter == 3){
                    System.out.print("|\n");
                    counter = 0;
                }

            }
        System.out.print("\n");
    }


    public static void printDescriprion() {
        char [] example = {
                '1','2','3',
                '4','5','6',
                '7','8','9'
        };
        drawField(example);
        System.out.println("Game description:\n" +
                "You have a board with the nine positions numbered as follows.\n" +
                "If you want to take a step, enter the appropriate cell number.\n" +
                "The player who succeeds in placing three of their marks in a horizontal,\n" +
                "vertical, or diagonal row wins the game. X always goes first.\n");
    }







}
