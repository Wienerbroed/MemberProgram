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
import java.util.*;

public class MemberCompetition {
    private FileHandler fileHandler;

    public MemberCompetition(String filePath) {
        this.fileHandler = new FileHandler(filePath);
    }

    public void addSwimTime(String memberName, String swimForm, double swimTime){
        List<Member> members = readMembersFromFile();
        boolean memberFound = false;

        for (Member member : members) {
            if(member.getName().equals(memberName)){
                List<Double> times = member.getSwimTimes().getOrDefault(swimForm, new ArrayList<>());

                times.add(swimTime);
                times.sort(Double::compareTo);

                if(times.size() > 3) {
                    times.remove(times.size()-1);
                }

                member.getSwimTimes().put(swimForm, times);

                memberFound = true;
                System.out.println(memberName + swimForm);
                break;
            }
        }
        if(memberFound) {
            writeMembersToFile(members);
        } else {
            System.out.println("No member found" + memberName);
        }
    }

    public void showMemberBySwimmingForm(String swimForm){
        List<Member> members = readMembersFromFile();
        List<Object[]> swimTimeList = new ArrayList<>();

        for (Member member : members) {
            List<Double> times = member.getSwimTimes().get(swimForm);
            if (times != null && !times.isEmpty()) {
                double bestTime = Collections.min(times);
                swimTimeList.add(new Object[]{member.getName(), bestTime});

            }
        }
        swimTimeList.sort((o1, o2) -> Double.compare((double) o1[1], (double) o2[1]));

        System.out.printf("%-20s %-10s\n", "Member Name", "Best Time");
        for (Object[] entry : swimTimeList) {
            System.out.printf("%-20s %-10.2f\n", entry[0], entry[1]);
        }
    }

    private List<Member> readMembersFromFile() {
        List<Member> members = new ArrayList<>();
        try (BufferedReader reader = fileHandler.getBufferedReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) { // Ensure there are enough fields
                    // Parse member data and swim times
                    Member member = new Member(
                            data[0],
                            LocalDate.parse(data[1]),
                            data[2],
                            LocalDateTime.parse(data[3]),
                            LocalDateTime.parse(data[4])
                    );

                    // Add swimming times for each form (if present)
                    if (data.length > 5) {
                        Map<String, List<Double>> swimTimes = new HashMap<>();
                        swimTimes.put("Freestyle", parseTimes(data[5]));
                        swimTimes.put("Backstroke", parseTimes(data[6]));
                        swimTimes.put("Butterfly", parseTimes(data[7]));
                        member.setSwimTimes(swimTimes);
                    }

                    members.add(member);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return members;
    }


    private void writeMembersToFile(List<Member> members) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileHandler.getFilePath()))) {
            for (Member member : members) {
                writer.write(member.getName() + "," +
                        member.getBirthDate() + "," +
                        member.getGender() + "," +
                        member.getJoinDate() + "," +
                        member.getDateOfPayment() + "," +
                        formatTimes(member.getSwimTimes().get("Freestyle")) + "," +
                        formatTimes(member.getSwimTimes().get("Backstroke")) + "," +
                        formatTimes(member.getSwimTimes().get("Butterfly")));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<Double> parseTimes(String timeString) {
        List<Double> times = new ArrayList<>();
        if (timeString != null && !timeString.isEmpty()) {
            String[] timeParts = timeString.split(";");
            for (String time : timeParts) {
                times.add(Double.parseDouble(time));
            }
        }
        return times;
    }


    private String formatTimes(List<Double> times) {
        if (times == null || times.isEmpty()) {
            return "";
        }
        return String.join(";", times.stream().map(String::valueOf).toArray(String[]::new));
    }


}
