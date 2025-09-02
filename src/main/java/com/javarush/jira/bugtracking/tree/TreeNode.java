package com.javarush.jira.bugtracking.tree;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javarush.jira.bugtracking.ObjectType;
import org.springframework.lang.NonNull;

import java.util.LinkedList;
import java.util.List;

/*
TreeNode — это record, который реализует интерфейс ITreeNode и представляет собой
узел в древовидной структуре, готовой для отображения на клиенте. Он добавляет свойства,
необходимые для работы UI-компонентов деревьев.
 */

public record TreeNode(@NonNull String code, @NonNull Long id, @NonNull ObjectType nodeType,
                       List<TreeNode> subNodes) implements ITreeNode<NodeTo, TreeNode> {
/*
Реализует ITreeNode<NodeTo, TreeNode> - рекурсивное определение, где:
NodeTo - тип данных узла (T)
TreeNode - тип самого узла дерева (R)

Компоненты record:
@NonNull String code - код узла (для отображения).
@NonNull Long id - ID узла.
@NonNull ObjectType nodeType - тип узла (определяет поведение).
List<TreeNode> subNodes - список дочерних узлов.
 */


    public TreeNode(NodeTo node) {
        this(node.getCode(), node.getId(), node.getType(), new LinkedList<>());
        //Преобразует NodeTo в TreeNode. Инициализирует subNodes пустым списком.
    }

    //----------- properties for tree constructing ----------

    @JsonProperty
    // будет сериализован в JSON.
    public String getKey() {
        return nodeType.name() + "-" + id;
    }

    //if node is task there is nothing to load - return all subtasks, else return null to enable children lazy loading
    @JsonProperty
    public List<TreeNode> getChildren() {
        if (nodeType == ObjectType.TASK) {
            return subNodes;
        }
        return null;
    }

    @JsonProperty
    public String getTitle() {
        return code;
    }

    @JsonProperty
    public boolean isLazy() {
        return nodeType != ObjectType.TASK;
    }
}
