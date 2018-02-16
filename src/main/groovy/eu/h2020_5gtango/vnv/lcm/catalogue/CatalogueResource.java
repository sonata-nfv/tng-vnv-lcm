package com.huawei.edgefabric.ai.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.edgefabric.ai.rest.ResourceAllocationOutput;
import com.huawei.edgefabric.ai.rest.ResourceAllocator;
import com.huawei.edgefabric.ai.onlinelearning.impl.DataManagerEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/api/v1/ai/resource-allocator")
public class ResultGenerationResource {

    private static String resultFolder = "results/runs/";

    @Value("${from:2013-11-11 00:00:00}")
    String from;


    @Value("${to:2013-11-12 00:00:00}")
    String to;

    @PostConstruct
    public void triggerRun() throws Exception {
        System.out.println(generateTimeseriesHistory(from, to));
    }


    @Autowired
    private ResourceAllocator resourceAllocator;


    @GetMapping("/allocate")
    public ResourceAllocationOutput allocateResource(@RequestParam(name = "timestamp", defaultValue = "2013-11-01 09:00:00") String timestamp) {
        return resourceAllocator.allocateResource(timestamp);
    }

    @GetMapping("/generate-timeseries-history")
    public String generateTimeseriesHistory(
            @RequestParam(name = "from", defaultValue = "2013-11-01 09:00:00") final String from,
            @RequestParam(name = "to", defaultValue = "2013-11-01 09:00:00") final String to
    ) {
        final String runFolder = resultFolder + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + "/";
        try {
            generateAndSaveResult(runFolder, from, to);
        } catch (Exception e) {
            throw new RuntimeException("failed to generate results", e);
        }
        StringBuilder result = new StringBuilder();
        result.append("\nInput " + from);
        result.append(" ~ " + to);
        result.append("  \n");
        result.append("Result Output Folder: " + runFolder + "\n");
        return result.toString();
    }

    public void generateAndSaveResult(String runFolder, String from, String to) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Calendar timestamp = Calendar.getInstance();
        timestamp.setTime(DataManagerEdge.formatter.parse(from));
        long toTimestamp = DataManagerEdge.formatter.parse(to).getTime();
        SimpleDateFormat filenameTimestampFormat = new SimpleDateFormat("yyyy-MM/dd/HH/mm");

        while (true) {
            File outputFile = new File(runFolder + filenameTimestampFormat.format(timestamp.getTime()) + ".json");
            outputFile.getParentFile().mkdirs();
            String logLine = new Date() + " generating result for " + outputFile.getPath() + " ...";
            System.out.println(logLine);
            FileWriter fileWritter = new FileWriter(runFolder + "logs.txt", true);
            fileWritter.append(logLine + '\n');
            fileWritter.close();
            ResourceAllocationOutput message = allocateResource(DataManagerEdge.formatter.format(timestamp.getTime()));
            mapper.writeValue(outputFile, message);
            timestamp.add(Calendar.MINUTE, 10);
            if (timestamp.getTimeInMillis() > toTimestamp) {
                break;
            }
        }
        System.out.println("Run finished for " + runFolder);
    }


}
