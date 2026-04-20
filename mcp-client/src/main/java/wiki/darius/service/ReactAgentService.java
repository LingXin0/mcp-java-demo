package wiki.darius.service;

import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReactAgentService {

    private final ChatModel chatModel;

    private final ToolCallbackProvider mcpToolCallbackProvider;

    private ReactAgent reactAgent;

    private static final String SYSTEM_PROMPT = """
            你是一个集成了MCP工具的智能服务助手，拥有强大的工具调用能力。
            
            你当前可以使用以下类型的工具：
            - 数学计算：加法、减法、乘法、除法、幂运算、开方
            - 天气查询：当前天气、多日天气预报、空气质量指数
            
            工作原则：
            1. 对于需要计算或查询的问题，优先调用相关工具获取准确结果
            2. 对于多步骤复杂问题，请分步骤推理并依次调用工具
            3. 工具调用结果需整合后再给出最终回答
            4. 如果问题不需要工具，直接回答即可
            """;

    @PostConstruct
    public void init(){

        ToolCallback[] toolCallbacks = mcpToolCallbackProvider.getToolCallbacks();

        log.info("======加载{}个工具======", toolCallbacks.length);
        for(ToolCallback toolCallback : toolCallbacks){
            log.info("======加载工具: 名称{}，描述{}======", toolCallback.getToolDefinition().name(), toolCallback.getToolDefinition().description());
        }

        reactAgent = ReactAgent.builder()
                .name("mcp-react-agent")
                .model(chatModel)
                .tools(toolCallbacks)
                .systemPrompt(SYSTEM_PROMPT)
                .saver(new MemorySaver()) //基于内存的会话存储
                .build();

    }



    public String chat(String message) {

        log.info("ReactAgentService chat 单次调用: {}", message);

        try {
            AssistantMessage assistantMessage = reactAgent.call(message);
            String messageText = assistantMessage.getText();
            log.info("ReactAgentService chat AI的回答: {}", messageText);
            return messageText;
        }catch (Exception e){
            log.error("ReactAgentService chat 异常: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String chat(String message, String sessionId) {
        log.info("ReactAgentService chat 带有记忆功能 单次调用: {}", message);

        RunnableConfig runnableConfig = RunnableConfig.builder().threadId(sessionId).build();

        try {
            AssistantMessage assistantMessage = reactAgent.call(message,runnableConfig);
            String messageText = assistantMessage.getText();
            log.info("ReactAgentService chat 带有记忆功能 AI的回答: {}", messageText);
            return messageText;
        }catch (Exception e){
            log.error("ReactAgentService chat 带有记忆功能 异常: {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
