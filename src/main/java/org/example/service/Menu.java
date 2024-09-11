package org.example.service;

import org.example.model.Member;
import org.example.repository.MemberCompetition;
import org.example.repository.MemberFinance;
import org.example.repository.MemberRegistration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Menu {

    private MemberRegistration memberRegistration = new MemberRegistration();
    private MemberFinance memberFinance = new MemberFinance();
    private MemberCompetition memberCompetition = new MemberCompetition("//src:members.csv");

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int roleChoice;

        do {
            System.out.println("Welcome to the Sports Club Management System");
            System.out.println("Please select your role:");
            System.out.println("1. Chairman");
            System.out.println("2. Treasurer");
            System.out.println("3. Coach");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            roleChoice = scanner.nextInt();

            switch (roleChoice) {
                case 1:
                    chairmanMenu(scanner);
                    break;
                case 2:
                    treasurerMenu(scanner);
                    break;
                case 3:
                    coachMenu(scanner);
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (roleChoice != 4);
    }

    private void chairmanMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n-- Chairman Menu --");
            System.out.println("1. Add Member");
            System.out.println("2. Edit Member");
            System.out.println("3. Delete Member");
            System.out.println("4. Back to Role Selection");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addMember(scanner);
                    break;
                case 2:
                    editMember(scanner);
                    break;
                case 3:
                    deleteMember(scanner);
                    break;
                case 4:
                    System.out.println("Returning to role selection...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 4);
    }

    private void treasurerMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n-- Treasurer Menu --");
            System.out.println("1. View Paid Members");
            System.out.println("2. View Total Member Income");
            System.out.println("3. View Total Member Debit");
            System.out.println("4. Back to Role Selection");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    memberFinance.paidMember();
                    break;
                case 2:
                    memberFinance.totalMemberIncome();
                    break;
                case 3:
                    memberFinance.totalMemberDebit();
                    break;
                case 4:
                    System.out.println("Returning to role selection...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 4);
    }

    private void coachMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n-- Coach Menu --");
            System.out.println("1. Add Swimming Times");
            System.out.println("2. Show Members by Swimming Form");
            System.out.println("3. Back to Role Selection");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addSwimmingTimes(scanner);
                    break;
                case 2:
                    showMembersBySwimmingForm(scanner);
                    break;
                case 3:
                    System.out.println("Returning to role selection...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 3);
    }

    private void addMember(Scanner scanner) {
        System.out.println("Enter Member Details: Name, Birthdate (yyyy-mm-dd), Gender:");
        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Birthdate (yyyy-mm-dd): ");
        String birthDateString = scanner.next();
        LocalDate birthDate = LocalDate.parse(birthDateString);
        System.out.print("Gender: ");
        String gender = scanner.next();

        Member newMember = new Member(name, birthDate, gender, LocalDateTime.now(), LocalDateTime.now());
        memberRegistration.addMember(newMember);
        System.out.println("Member added successfully!");
    }

    private void editMember(Scanner scanner) {
        System.out.print("Enter the name of the member to edit: ");
        String name = scanner.next();

        Member existingMember = memberRegistration.findMemberByName(name);
        if (existingMember != null) {
            System.out.println("Editing member " + name);

            System.out.print("Enter new Name (or press Enter to skip): ");
            String newName = scanner.next();
            if (!newName.isEmpty()) {
                existingMember.setName(newName);
            }

            System.out.print("Enter new Birthdate (yyyy-mm-dd) (or press Enter to skip): ");
            String birthDateInput = scanner.next();
            if (!birthDateInput.isEmpty()) {
                existingMember.setBirthDate(LocalDate.parse(birthDateInput));
            }

            System.out.print("Enter new Gender (or press Enter to skip): ");
            String newGender = scanner.next();
            if (!newGender.isEmpty()) {
                existingMember.setGender(newGender);
            }

            memberRegistration.editMember(existingMember, existingMember);
            System.out.println("Member details updated!");
        } else {
            System.out.println("Member not found.");
        }
    }

    private void deleteMember(Scanner scanner) {
        System.out.print("Enter the name of the member to delete: ");
        String name = scanner.next();

        Member member = memberRegistration.findMemberByName(name);
        if (member != null) {
            memberRegistration.deleteMember(member);
            System.out.println("Member deleted successfully.");
        } else {
            System.out.println("Member not found.");
        }
    }

    private void addSwimmingTimes(Scanner scanner) {
        System.out.print("Enter Member Name: ");
        String memberName = scanner.next();
        System.out.print("Enter Swimming Form: ");
        String form = scanner.next();
        System.out.print("Enter Time: ");
        double time = scanner.nextDouble();

        memberCompetition.addSwimTime(memberName, form, time);
        System.out.println("Swimming time added successfully!");
    }

    private void showMembersBySwimmingForm(Scanner scanner) {
        System.out.print("Enter Swimming Form: ");
        String form = scanner.next();
        memberCompetition.showMemberBySwimmingForm(form);
    }
}