package org.example.repository;

import org.example.filehandler.FileHandler;
import org.example.model.Member;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MemberRegistration {
    ArrayList<Member> memberList = new ArrayList<Member>();
    FileHandler fileHandler = new FileHandler("//src:members.csv");

    //Svaing member to CSV file
    private void saveMember(){
        for (Member member : memberList) {
            String[] data = {
                    member.getName(),
                    member.getBirthDate().toString(),
                    member.getGender(),
                    member.getJoinDate().toString(),
                    member.getDateOfPayment().toString()
            };
            fileHandler.writeFile(data);
        }
    }

    //Loading member CSV file
    private void loadMember(){
        fileHandler.readFile();
    }


    //Add method
    public void addMember(Member member) {
        memberList.add(member);
        saveMember();

    }

    //Delete method
    public void deleteMember(Member member) {
        memberList = (ArrayList<Member>) memberList.stream()
                .filter(m -> m.getName().equals(member.getName()))
                .collect(Collectors.toList());
        saveMember();
    }

    //Edit method
    public void editMember(Member oldMember, Member newMember) {
        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getName().equals(oldMember.getName())) {
                memberList.set(i, newMember);
                break;
            }
        }
        saveMember();

    }

}
