package com.example.demo.Controller;

import com.example.demo.Model.AidsModel;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/predict")
public class AidsController {
    // Endpoint baru untuk menampilkan info atribut dan tipe datanya
    @GetMapping("/form")
    public String showForm() {
        return "form";
    }
    @GetMapping("/info")
    public Map<String, Object> getModelInfo() {
        Map<String, Object> info = new HashMap<>();

        info.put("description", "Informasi tentang atribut-atribut yang digunakan dalam prediksi AIDS beserta tipe datanya.");

        Map<String, String> attributes = new HashMap<>();
        attributes.put("time", "Integer - Time to failure or censoring.");
        attributes.put("trt", "Integer - Treatment indicator (0=ZDV only, 1=ZDV+ddI, 2=ZDV+Zal, 3=ddI only).");
        attributes.put("age", "Integer - Age (years) at baseline.");
        attributes.put("wtkg", "Float - Weight (kg) at baseline.");
        attributes.put("hemo", "Integer - Hemophilia (0=no, 1=yes).");
        attributes.put("homo", "Integer - Homosexual activity (0=no, 1=yes).");
        attributes.put("drugs", "Integer - History of IV drug use (0=no, 1=yes).");
        attributes.put("karnof", "Integer - Karnofsky score (on a scale of 0-100).");
        attributes.put("oprior", "Integer - Non-ZDV antiretroviral therapy pre-175 (0=no, 1=yes).");
        attributes.put("z30", "Integer - ZDV in the 30 days prior to 175 (0=no, 1=yes).");
        attributes.put("preanti", "Integer - Days pre-175 anti-retroviral therapy.");
        attributes.put("race", "Integer - Race (0=White, 1=Non-white).");
        attributes.put("gender", "Integer - Gender (0=Female, 1=Male).");
        attributes.put("str2", "Integer - Antiretroviral history (0=naive, 1=experienced).");
        attributes.put("strat", "Integer - Antiretroviral history stratification (1=Antiretroviral Naive, 2=>1 but <=52 weeks, 3=>52 weeks).");
        attributes.put("symptom", "Integer - Symptomatic indicator (0=asymptomatic, 1=symptomatic).");
        attributes.put("treat", "Integer - Treatment indicator (0=ZDV only, 1=others).");
        attributes.put("offtrt", "Integer - Indicator of off-treatment before 96+/-5 weeks (0=no, 1=yes).");
        attributes.put("cd40", "Integer - CD4 at baseline.");
        attributes.put("cd420", "Integer - CD4 at 20+/-5 weeks.");
        attributes.put("cd80", "Integer - CD8 at baseline.");
        attributes.put("cd820", "Integer - CD8 at 20+/-5 weeks.");
        info.put("attributes", attributes);
        return info;
    }

    @PostMapping("/aids")
    public Map<String, String> runPythonScript(@RequestBody AidsModel request) {
        Map<String, String> response = new HashMap<>();

        try {
            String pythonInterpreter = "C:\\Users\\Dimas Tri Wicaksono\\OneDrive - untirta.ac.id\\Documents\\TIA ACADEMY\\Project Akhir\\projectakhir_env\\Scripts\\python.exe";
            String scriptPath = "C:\\Users\\Dimas Tri Wicaksono\\OneDrive - untirta.ac.id\\Documents\\TIA ACADEMY\\Project Akhir\\projectakhir_env\\prediction.py";

            String[] command = {
                    pythonInterpreter,
                    scriptPath,
                    String.valueOf(request.getTime()),
                    String.valueOf(request.getTrt()),
                    String.valueOf(request.getAge()),
                    String.valueOf(request.getWtkg()),
                    String.valueOf(request.getHemo()),
                    String.valueOf(request.getHomo()),
                    String.valueOf(request.getDrugs()),
                    String.valueOf(request.getKarnof()),
                    String.valueOf(request.getOprior()),
                    String.valueOf(request.getZ30()),
                    String.valueOf(request.getPreanti()),
                    String.valueOf(request.getRace()),
                    String.valueOf(request.getGender()),
                    String.valueOf(request.getStr2()),
                    String.valueOf(request.getStrat()),
                    String.valueOf(request.getSymptom()),
                    String.valueOf(request.getTreat()),
                    String.valueOf(request.getOfftrt()),
                    String.valueOf(request.getCd40()),
                    String.valueOf(request.getCd420()),
                    String.valueOf(request.getCd80()),
                    String.valueOf(request.getCd820())
            };

            // Jalankan proses Python
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Ambil output script Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String predictionResult = "";

            while ((line = reader.readLine()) != null) {
                predictionResult = line.trim();
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                response.put("status", "error");
                response.put("message", "Python script exited with code " + exitCode);
                return response;
            }

            response.put("status", "success");
            response.put("prediction", predictionResult);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }
}
