package org.example.repository;

import org.example.filehandler.FileHandler;
import org.example.model.Member;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MemberFinance {
    FileHandler fileHandler = new FileHandler("//src:members.csv");

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

    public void totalMemberIncome(){

    }

    public void totalMemberDebit(){

    }

    public void MemberIncomeByPaid(){

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

}
