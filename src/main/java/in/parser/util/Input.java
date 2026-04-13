package in.parser.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    private Input() {}

    static Scanner scan = new Scanner(System.in);


    public static double getDouble(String str) {
        while (true) {
            try {
                System.out.print("📐 " + str + " → ");
                double value = scan.nextDouble();
                scan.nextLine();
                return value;
            } catch (InputMismatchException ie) {
                System.err.println("⚠️ Please enter a valid decimal number (e.g., 3.14).");
                scan.nextLine();
            }
        }
    }

    public static float getFloat(String str) {
        while (true) {
            try {
                System.out.print("📏 " + str + " → ");
                float value = scan.nextFloat();
                scan.nextLine();
                return value;
            } catch (InputMismatchException ie) {
                System.err.println("⚠️ Please enter a valid float (e.g., 2.5).");
                scan.nextLine();
            }
        }
    }

    public static long getLong(String str) {
        while (true) {
            try {
                System.out.print("🏗️ " + str + " → ");
                long value = scan.nextLong();
                scan.nextLine();
                return value;
            } catch (InputMismatchException ie) {
                System.err.println("⚠️ Please enter a valid long number (e.g., 123456789).");
                scan.nextLine();
            }
        }
    }


    public static byte getByte(String str) {
        while (true) {
            try {
                System.out.print("💾 " + str + " → ");
                byte value = scan.nextByte();
                scan.nextLine();
                return value;
            } catch (InputMismatchException ie) {
                System.err.println("⚠️ Please enter a valid byte (-128 to 127).");
                scan.nextLine();
            }
        }
    }


    public static short getShort(String str) {
        while (true) {
            try {
                System.out.print("📏 " + str + " → ");
                short value = scan.nextShort();
                scan.nextLine();
                return value;
            } catch (InputMismatchException ie) {
                System.err.println("⚠️ Please enter a valid short (-32,768 to 32,767).");
                scan.nextLine();
            }
        }
    }


    public static String getNext(String str) {
        System.out.print("✏️ " + str + " → ");
        return scan.next();
    }


    public static String getLine(String str) {
        System.out.print("\n" + str + " → ");
        String sc=scan.nextLine();
        while(true) {
            if (sc.isEmpty()) {
                System.err.println("Please enter the valid Input");
                System.out.print("\n" + str + " → ");
                sc=scan.nextLine();
            }
            else{
                break;
            }
        }
        return sc;
    }



    public static int getInt(String str) {
        while (true) {
            try {
                System.out.print(str + " → ");
                int value = scan.nextInt();
                scan.nextLine();
                return value;
            } catch (InputMismatchException ie) {
                System.err.println("\n⚠️ Please enter a valid integer.");
                scan.nextLine();
            }
        }
    }


    public static boolean getBoolean(String str) {
        while (true) {
            System.out.print("❓ " + str + " (yes/no) → ");
            String value = scan.next().toLowerCase();
            scan.nextLine();

            if (value.equals("yes") || value.equals("y")) return true;
            if (value.equals("no") || value.equals("n")) return false;

            System.err.println("⚠️ Please answer with yes or no.");
        }
    }


    public static int getChoice(String str, int lowerBound, int upperBound) {
        while (true) {
            System.out.print(str);

            try {
                int choice = scan.nextInt();
                scan.nextLine();

                if (choice >= lowerBound && choice <= upperBound) {
                    return choice;
                }
                else {
                    System.err.println("⚠️ Please enter a number between " + lowerBound + " and " + upperBound + ".");
                }
            }
            catch (InputMismatchException ie) {
                System.err.println("⚠️ Invalid input! Kindly provide a number between " + lowerBound + " and " + upperBound + ".");
                scan.nextLine();
            }
        }
    }
}

