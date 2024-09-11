package org.example.repository;

import org.example.filehandler.FileHandler;
import org.example.model.Member;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MemberRegistration {
    ArrayList<Member> memberList = new ArrayList<Member>();
    FileHandler fileHandler = new FileHandler("//src:members.csv");

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

    private void loadMember(){
        fileHandler.readFile();
    }


    public void addMember(Member member) {
        memberList.add(member);
        saveMember();

    }

    public void deleteMember(Member member) {
        memberList = (ArrayList<Member>) memberList.stream()
                .filter(m -> m.getName().equals(member.getName()))
                .collect(Collectors.toList());
        saveMember();
    }

    public void editMember(Member oldMember, Member newMember) {
        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getName().equals(oldMember.getName())) {
                memberList.set(i, newMember);
                break;
            }
        }
        saveMember();

    }

    public Member findMemberByName(String name) {
        for (Member member : memberList) {
            if (member.getName().equalsIgnoreCase(name)) {
                return member;
            }
        }
        return null;
    }


}
