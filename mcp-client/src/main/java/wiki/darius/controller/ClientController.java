package wiki.darius.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wiki.darius.service.ReactAgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ai/mcp")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ReactAgentService reactAgentService;


    @GetMapping("/react")
    public ResponseEntity<Map<String,Object>> reactAgent(@RequestParam String message){

        log.info("收到请求: {}", message);
        String response = reactAgentService.chat(message);
        Map<String, Object> result = Map.of("message", response);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/react_memory")
    public ResponseEntity<Map<String,Object>> reactAgentMemory(@RequestParam String message,@RequestParam String sessionId){

        log.info("收到请求: {}", message);
        String response = reactAgentService.chat(message,sessionId);
        Map<String, Object> result = Map.of("message", response);
        return ResponseEntity.ok(result);
    }

}
