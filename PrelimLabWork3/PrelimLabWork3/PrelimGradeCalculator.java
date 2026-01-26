package PrelimLabWork3;
import java.util.Scanner;

public class PrelimGradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Attendance Grade: ");
        double attendance = scanner.nextDouble();

        System.out.print("Enter Lab Work 1 Grade: ");
        double lab1 = scanner.nextDouble();

        System.out.print("Enter Lab Work 2 Grade: ");
        double lab2 = scanner.nextDouble();

        System.out.print("Enter Lab Work 3 Grade: ");
        double lab3 = scanner.nextDouble();

        double labAverage = (lab1 + lab2 + lab3) / 3;
        double classStanding = (attendance * 0.40) + (labAverage * 0.60);

        double requiredExamPass = (75 - (classStanding * 0.30)) / 0.70;
        double requiredExamExcellent = (100 - (classStanding * 0.30)) / 0.70;

        System.out.println("\n----- PRELIM GRADE SUMMARY -----");
        System.out.println("Attendance: " + attendance);
        System.out.println("Lab Work 1: " + lab1);
        System.out.println("Lab Work 2: " + lab2);
        System.out.println("Lab Work 3: " + lab3);
        System.out.println("Lab Work Average: " + String.format("%.2f", labAverage));
        System.out.println("Class Standing: " + String.format("%.2f", classStanding));

        System.out.println("\nRequired Prelim Exam Grade:");
        System.out.println("To Pass (75): " + String.format("%.2f", requiredExamPass));
        System.out.println("To Excel (100): " + String.format("%.2f", requiredExamExcellent));

        if (requiredExamPass > 100) {
            System.out.println("Remark: Passing is no longer possible.");
        } else {
            System.out.println("Remark: You can still pass the Prelim period.");
        }

        scanner.close();
    }
}
