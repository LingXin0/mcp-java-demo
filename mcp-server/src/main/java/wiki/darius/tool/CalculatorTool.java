package wiki.darius.tool;

import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CalculatorTool {


    /**
     * 加法运算
     */
    @McpTool(name = "calculator_add", description = "执行两个数字的加法运算")
    public double add(
            @McpToolParam(description = "第一个数字") double a,
            @McpToolParam(description = "第二个数字") double b) {
        log.info("执行加法运算：{} + {}", a, b);
        return a + b;
    }

    /**
     * 减法运算
     */
    @McpTool(name = "calculator_subtract", description = "执行两个数字的减法运算")
    public double subtract(
            @McpToolParam(description = "被减数") double a,
            @McpToolParam(description = "减数") double b) {
        log.info("执行减法运算：{} - {}", a, b);
        return a - b;
    }

    /**
     * 乘法运算
     */
    @McpTool(name = "calculator_multiply", description = "执行两个数字的乘法运算")
    public double multiply(
            @McpToolParam(description = "第一个数字") double a,
            @McpToolParam(description = "第二个数字") double b) {
        log.info("执行乘法运算：{} * {}", a, b);
        return a * b;
    }

    /**
     * 除法运算
     */
    @McpTool(name = "calculator_divide", description = "执行两个数字的除法运算")
    public double divide(
            @McpToolParam(description = "被除数") double a,
            @McpToolParam(description = "除数") double b) {
        if (b == 0) {
            throw new IllegalArgumentException("除数不能为零");
        }
        log.info("执行除法运算：{} / {}", a, b);
        return a / b;
    }

    /**
     * 幂运算
     */
    @McpTool(name = "calculator_power", description = "计算一个数的幂次方")
    public double power(
            @McpToolParam(description = "底数") double base,
            @McpToolParam(description = "指数") double exponent) {
        log.info("执行幂运算：{} ^ {}", base, exponent);
        return Math.pow(base, exponent);
    }

    /**
     * 平方根
     */
    @McpTool(name = "calculator_sqrt", description = "计算一个数的平方根")
    public double sqrt(
            @McpToolParam(description = "要计算平方根的数字") double value) {
        if (value < 0) {
            throw new IllegalArgumentException("不能计算负数的平方根");
        }
        log.info("执行平方根运算：sqrt({})", value);
        return Math.sqrt(value);
    }
}
