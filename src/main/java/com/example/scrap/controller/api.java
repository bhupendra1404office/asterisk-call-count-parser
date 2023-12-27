package com.example.scrap.controller;

import com.example.scrap.model.Id;
import com.example.scrap.model.IncomingCallsDetails;
import com.example.scrap.repository.IncomingCallsRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class api {

    @Value("${app.log.file.location}")
    private String filePath;

    @Value("${app.server.name}")
    private String serverName;

    @Autowired
    IncomingCallsRepository incomingCallsRepository;

    @GetMapping("/update-data")
    public String updateData(){
        long ans = 0l;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try{
                    // Compile the regex pattern
                    String regex = "^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+)(\\+\\d{4})\\sINFO,\\s(\\d+)\\sactive\\schannels\\s\\d+\\sof\\s(\\d+)\\smax\\sactive\\scalls\\s\\(.*\\)\\s(\\d+)\\scalls\\sprocessed$";

                    // Compile the regex pattern
                    Pattern pattern = Pattern.compile(regex);

                    // Match the pattern against the log line
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {
                        // Extract the matched groups
                        String datetimeString = matcher.group(1) + matcher.group(2);
                        long activeChannels = Integer.parseInt(matcher.group(3));
                        long maxActiveCalls = Integer.parseInt(matcher.group(4));
                        long callsProcessed = Integer.parseInt(matcher.group(5));

                        // Create a custom DateTimeFormatter to handle the time zone offset
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

                        // Parse the LocalDateTime from the extracted string using the custom formatter
                        LocalDateTime localDateTime = LocalDateTime.parse(datetimeString, formatter);

                        long lastNum = 0l;
                        try{
                            Pageable pageable = PageRequest.of(0, 1);
                            List<IncomingCallsDetails> temp = incomingCallsRepository.findLastData(serverName,localDateTime,pageable);
                            if(temp==null && temp.size()!=1){
                                lastNum = callsProcessed;
                            }else{
                                if((callsProcessed-temp.get(0).getLastCallProcessed())>0){
                                    lastNum =callsProcessed-temp.get(0).getLastCallProcessed();
                                }else{
                                    lastNum =callsProcessed;
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            log.info("Error in getting last data");
                        }

                        IncomingCallsDetails details = new IncomingCallsDetails();
                        Id id = new Id(serverName,localDateTime);
                        details.setId(id);
                        details.setChannelUsed(activeChannels);
                        details.setChannelActive(maxActiveCalls);
                        details.setLastCallProcessed(callsProcessed);
                        details.setIncrementalCalls(lastNum);

                        incomingCallsRepository.save(details);
                        ans++;
                    } else {
                        System.out.println("No match found in the log line.");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    log.info("Error in reading line {}",line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Total entries added :"+ans;
    }



}
