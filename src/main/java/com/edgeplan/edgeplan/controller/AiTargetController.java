package com.edgeplan.edgeplan.controller;

import com.edgeplan.edgeplan.dto.TargetCreateRequest;
import com.edgeplan.edgeplan.service.TargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/ai/target")
@RequiredArgsConstructor
public class AiTargetController {

    private final OllamaChatModel chatModel;
    private final TargetService targetService;

    @PostMapping("/create")
    public String createTarget(@RequestBody String userPrompt) {

        String response = chatModel.call("""
            Şu cümleden bir JSON üret:
            Cümle: "%s"
            JSON şu formatta olmalı:
            {
              "title": "...",
              "description": "...",
              "endDate": "YYYY-MM-DD"
            }
            Sadece JSON döndür.
            """.formatted(userPrompt));

        try {
            String title = response.split("\"title\":")[1].split(",")[0].replaceAll("[\"{}]", "").trim();
            String description = response.split("\"description\":")[1].split(",")[0].replaceAll("[\"{}]", "").trim();
            String endDateStr = response.split("\"endDate\":")[1].split("}")[0].replaceAll("[\"{}]", "").trim();

            TargetCreateRequest target = new TargetCreateRequest();
            target.setTitle(title);
            target.setDescription(description);
            target.setEndDate(LocalDate.parse(endDateStr));

            targetService.create(target);
            return "✅ Yeni hedef oluşturuldu: " + title;

        } catch (Exception e) {
            return "❌ Hedef oluşturulamadı: " + e.getMessage() + "\nAI cevabı: " + response;
        }
    }
}