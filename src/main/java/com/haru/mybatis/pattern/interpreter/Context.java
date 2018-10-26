package com.haru.mybatis.pattern.interpreter;

import java.util.*;

/**
 * @author HARU
 * @since 2018/10/26
 */
public class Context {
    private String text;
    private Map<String, Float> node;

    public Context(String text) {
        this.text = text;
        node = new LinkedHashMap<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Float> getNode() {
        return node;
    }

    public void interpret() {
        String[] split = text.split(" ");
        for (String textSplit : split) {
            if (!"+".equals(textSplit) && !"-".equals(textSplit) && !"*".equals(textSplit) && !"/".equals(textSplit)) {
                node.put(textSplit, new Random().nextFloat());
            }
        }
        Node leftNode = null;
        Node rightNode = null;
        LinkedList<Node> nodeList = new LinkedList<Node>();
        nodeList.push(new NumberNode(split[0]));
        for (int i = 1; i < split.length; i++) {
            if ("+".equals(split[i]) || "-".equals(split[i]) ||"*".equals(split[i]) || "/".equals(split[i])) {
                leftNode = nodeList.pop();
                rightNode = new NumberNode(split[i + 1]);
                nodeList.push(new SymbolNode(leftNode, rightNode, split[i]));
            }
        }
        System.out.println(nodeList.pop().interpret(this));
    }
}
