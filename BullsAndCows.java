package bullscows;

import java.util.*;

class BullsAndCows {

    String answer;
    String secret;
    String possibleDigits;
    String alphabetMax;
    int bull;
    int cow;
    int digitMax;
    boolean win;

    public BullsAndCows() {
        this.answer = "";
        this.secret = "";
        this.possibleDigits = "0123456789abcdefghijklmnopqrstuvwxyz";
        this.alphabetMax = "";
        this.digitMax = 0;
        this.bull = 0;
        this.cow = 0;
        this.win = false;
    }

    public void checkMatch(String guess) {
        String[] guessParts = String.valueOf(guess).split("");
        String[] answerParts = answer.split("");

        for (int x = 0; x < guessParts.length; x++) {
            if (guessParts[x].equals(answerParts[x])) {
                bull++;
            }
            if (!guessParts[x].equals(answerParts[x])) {
                cow++;
            }
        }
    }

    public void getAnswer(int codeLength, int rangeOfChars) {
        Random random = new Random();

        while (answer.length() != codeLength) {
            char symbol = Character.forDigit(random.nextInt(rangeOfChars), possibleDigits.length());
            if (symbol == '0' && answer.length() == 0) {
                continue;
            }
            if (!answer.contains(String.valueOf(symbol))) {
                answer += symbol;
            }
        }
    }

    public void resetBullsAndCows() {
        bull = 0;
        cow = 0;
    }

    public int result() {
        if (bull == answer.length()) {
            return 1;
        } else if (bull > 0 && cow == 0) {
            return 2;
        } else if (bull == 0 && cow > 0) {
            return 3;
        } else if (bull > 0 && cow > 0) {
            return 4;
        } else {
            return 5;
        }
    }

    public void starsAndHint(int length) {
        for (int x = 0; x < answer.length(); x++) {
            secret += "*";
        }
        if (length < 10) {
            System.out.printf("The secret is prepared: %s (0-%d).\n", secret, digitMax);
        } else {
            System.out.printf("The secret is prepared: %s (0-%d, a-%s).\n", secret, digitMax, alphabetMax);
        }
    }

    public void setDigitMax(int length) {
        digitMax = length > 10 ? 9 : length - 1;
    }

    public void setAlphabetMax(int length) {
        alphabetMax = String.valueOf(possibleDigits.charAt(length - 1));
    }

    public void game() {
        Scanner scanner = new Scanner(System.in);
        int turn = 1;

        while (!win) {
            System.out.printf("Turn %d:\n", turn);
            String input = scanner.nextLine();
            checkMatch(input);

            switch (result()) {
                case 1:
                    System.out.printf("Grade: %d bull(s)\n", bull);
                    System.out.println("Congratulations! You guessed the secret code.");
                    win = true;
                    break;
                case 2:
                    System.out.printf("Grade: %d bull(s)\n", bull);
                    break;
                case 3:
                    System.out.printf("Grade: %d cow(s)\n", cow);
                    break;
                case 4:
                    System.out.printf("Grade: %d bull(s) and %d cow(s)\n", bull, cow);
                    break;
                case 5:
                    System.out.println("None\n");
                    break;
            }
            resetBullsAndCows();
            turn++;
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter the secret code's length:");
        String input = scanner.nextLine();
        // Handling if the input is not a number
        if (!input.matches("\\d+")) {
            System.out.printf("Error: \"%s\" isn't a valid number.%n", input);
        } else {
            System.out.println("Input the number of possible symbols in the code:");
            String inputTwo = scanner.nextLine();

            if (Integer.parseInt(inputTwo) < Integer.parseInt(input)) {
                System.out.printf("Error: it's not possible to generate a code with a " +
                        "length of %s with %s unique symbols.%n", input, inputTwo);
            } else if (Integer.parseInt(inputTwo) > 36 || Integer.parseInt(inputTwo) <= 0) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            } else if (Integer.parseInt(input) > 36 || Integer.parseInt(input) <= 0) {
                System.out.printf("Error: can't generate a secret number with a length of %s", input);
            } else {
                getAnswer(Integer.parseInt(input), Integer.parseInt(inputTwo));
                setDigitMax(Integer.parseInt(inputTwo));
                setAlphabetMax(Integer.parseInt(inputTwo));
                starsAndHint(Integer.parseInt(inputTwo));
                System.out.println("Okay, let's start a game!");
                game();

            }
        }
    }
}
