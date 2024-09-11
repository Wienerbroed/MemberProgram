package org.example.repository;

import org.example.filehandler.FileHandler;
import org.example.model.Member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MemberFinance {
    FileHandler fileHandler = new FileHandler("//src:members.csv");
    private static final int youthMembershipCost = 1000;
    private static final int membershipCost = 1500;
    private static final int seniorMembershipCost = 1200;

    public List<String> paidMember(){
        List<String> results = new ArrayList<>();
        List<Member> members = readMembersFromFile();

        for (Member member : members) {
            String name = member.getName();
            LocalDateTime dateOfPayment = member.getDateOfPayment();
            String status = (dateOfPayment != null && ChronoUnit.DAYS.between(dateOfPayment, LocalDateTime.now()) <= 365)
                    ? "Paid"
                    : "Not Paid";
            results.add(name + ": " + status);
        }
        return results;

    }

    public int totalMemberIncome(){
        List<Member> members = readMembersFromFile();
        int totalMemberIncome = 0;

        for (Member member : members) {
            int age = calculateAge(member.getBirthDate());
            if (age < 18) {
                totalMemberIncome += youthMembershipCost;
            } else if (age >= 18 && age <= 60) {
                totalMemberIncome += membershipCost;
            } else if (age >= 60 && age <= 120) {
                totalMemberIncome += seniorMembershipCost;
            }
        }
        return totalMemberIncome;

    }

    public void totalMemberDebit(){
        List<Member> members = readMembersFromFile();
        int totalMemberDebit = 0;
        List<String> unpaidMembers = new ArrayList<>();

        for (Member member : members) {
            LocalDateTime dateOfPayment = member.getDateOfPayment();
            String status = (dateOfPayment != null && ChronoUnit.DAYS.between(dateOfPayment, LocalDateTime.now()) <= 365)
            ? "Paid"
                    : "Not Paid";
            if (status.equals("Not Paid")){
                int age = calculateAge(member.getBirthDate());
                int debitAmount = 0;

                if (age < 18) {
                    debitAmount = 1000;
                } else if (age >= 18 && age <= 60) {
                    debitAmount = 1500;
                } else if (age > 60) {
                    debitAmount = 1200;
                }

                totalMemberDebit += debitAmount;

                unpaidMembers.add(member.getName() + ": " + debitAmount + " - Not Paid");
            }

        }

    }

    public void MemberIncomeByPaid(){
        List<Member> members = readMembersFromFile();
        int totalMemberIncome = 0;
        List<String> unpaidMembers = new ArrayList<>();

        for (Member member : members) {
            LocalDateTime dateOfPayment = member.getDateOfPayment();
            String status = (dateOfPayment != null && ChronoUnit.DAYS.between(dateOfPayment, LocalDateTime.now()) <= 365)
                    ? "Paid"
                    : "Not Paid";
            if (status.equals("Paid")){
                int age = calculateAge(member.getBirthDate());
                int debitAmount = 0;

                if (age < 18) {
                    debitAmount = 1000;
                } else if (age >= 18 && age <= 60) {
                    debitAmount = 1500;
                } else if (age > 60) {
                    debitAmount = 1200;
                }

                totalMemberIncome += debitAmount;

                unpaidMembers.add(member.getName() + ": " + debitAmount + " - Paid");
            }

        }

    }

    private void writeMembersToFile(List<Member> members) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileHandler.getFilePath()))) {
            for (Member member : members) {
                writer.write(member.getName() + "," +
                        member.getBirthDate() + "," +
                        member.getGender() + "," +
                        member.getJoinDate() + "," +
                        member.getDateOfPayment());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Member> readMembersFromFile() {
        List<Member> members = new ArrayList<>();
        try (BufferedReader reader = fileHandler.getBufferedReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) { // Ensure there are enough fields
                    Member member = new Member(
                            data[0],
                            LocalDate.parse(data[1]),
                            data[2],
                            LocalDateTime.parse(data[3]),
                            LocalDateTime.parse(data[4])
                    );
                    members.add(member);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return members;
    }

    public void updatePaymentDate(String memberName){
        List<Member> members = readMembersFromFile();
        boolean memberFound = false;

        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(memberName)) {
                member.setDateOfPayment(LocalDateTime.now());
                memberFound = true;
                System.out.println("Updated member: " + member.getName());
                break;
            }
        }
        if (memberFound) {
            writeMembersToFile(members);

        }else {
            System.out.println("Member not found");
        }
    }
    
    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}
