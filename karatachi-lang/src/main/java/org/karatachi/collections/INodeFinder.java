package org.karatachi.collections;

import java.io.Serializable;

public interface INodeFinder<T extends Serializable & Comparable<? super T>> extends Serializable {
    TreeNode<T> find(T key);
}
