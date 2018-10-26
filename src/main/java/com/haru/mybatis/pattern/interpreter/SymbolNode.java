package com.haru.mybatis.pattern.interpreter;

/**
 * @author HARU
 * @since 2018/10/26
 */
public class SymbolNode implements Node {
    private Node leftNode;
    private Node rightNode;
    private String symbol;

    public SymbolNode(Node leftNode, Node rightNode, String symbol) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.symbol = symbol;
    }

    @Override
    public Float interpret(Context context) {
        switch (this.symbol) {
            case "+":
                return leftNode.interpret(context) + rightNode.interpret(context);
            case "-":
                return leftNode.interpret(context) - rightNode.interpret(context);
            case "*":
                return leftNode.interpret(context) * rightNode.interpret(context);
            case "/":
                return leftNode.interpret(context) / rightNode.interpret(context);
            default:
                return null;
        }
    }
}
