package com.eli.urlshortener.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping
    public ResponseEntity<String> askChat(@RequestBody String prompt){
        String instructions = """
                You are a chat bot for a url shortening app
                
                You can only discuss the following:
                - How to shorten a Url
                - How to use a shortened url to redirect
                - How to remove a Url
                - How to register
                - How to see analytics for your Url
                
                If asked about anything else, respond with "I can only help with the following" and list the list aforementioned
                """;


        String chatResponse =  chatClient.prompt()
                .user(prompt)
                .system(instructions)
                .call()
                .content();

        return ResponseEntity.ok(chatResponse);
    }
}
