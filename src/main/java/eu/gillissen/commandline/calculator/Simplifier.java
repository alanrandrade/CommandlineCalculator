package eu.gillissen.commandline.calculator;

import eu.gillissen.commandline.calculator.node.BaseNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simplifier {

    private static BaseNode eliminateSubExpressions(BaseNode node, Map<String, BaseNode> map) {
        String str = node.toString();
        BaseNode refNode = map.get(str);
        if(refNode != null) {
            System.out.println("duplicate expression " + str);
            return refNode;
        } else {
            map.put(str, node);
            List<BaseNode> children = node.getChildren();
            for(int i = 0; i < children.size(); i++) {
                BaseNode childNode = children.get(i);
                int index = children.indexOf(childNode);
                children.add(index, eliminateSubExpressions(childNode, map));
                children.remove(childNode);
            }
            return node;
        }
    }

    public static void simplify(BaseNode rootNode) {
        Map<String, BaseNode> map = new HashMap<String, BaseNode>();
        eliminateSubExpressions(rootNode, map);
    }

}
