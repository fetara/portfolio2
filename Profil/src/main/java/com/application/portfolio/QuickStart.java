package com.application.portfolio;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;

public class QuickStart {
    public static void main(String[] args) {
    	AnthropicClient client = AnthropicOkHttpClient.builder()
    			.apiKey(System.getenv("ANTHROPIC_API_KEY"))
    			.build();
        //AnthropicClient client = AnthropicOkHttpClient.fromEnv();

        MessageCreateParams params = MessageCreateParams.builder()        		
                .model("claude-sonnet-4-5-20250929")
                .maxTokens(1000)
                .addUserMessage("What should I search for to find the latest developments in renewable energy?")
                .build();

        Message message = client.messages().create(params);
        System.out.println(message.content());
    }
}