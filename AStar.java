package Entity.animal.Intelligence;

import java.util.*;
import java.util.Objects;

public class AStar {
    private Node[][] searchArea;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node initialNode;
    private Node finalNode;

    public Node[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Node[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Node> openList) {
        this.openList = openList;
    }

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Node> closedSet) {
        this.closedSet = closedSet;
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.isEmpty();
    }

    private void setBlock(int row, int col) {
        this.searchArea[row][col].setIs_block(true);
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
    }

    public AStar(int rows, int cols, Node initialNode, Node finalNode) {

        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new Node[rows][cols];
        this.openList = new PriorityQueue<>(Comparator.comparing(Node::getFVal));
        this.closedSet = new HashSet<>();
        setNodes();
    }

    public void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Node node = new Node(i, j);
                node.calculateHeuristic(getFinalNode());
                this.searchArea[i][j] = node;
            }
        }
    }

    public void setBlocks(int[][] blocksArray, int count) {
        for (int i = 0; i < count; i++) {
            int row = blocksArray[i][0];
            int col = blocksArray[i][1];
            setBlock(row, col);
        }
    }

    public List<Node> findPath() {
        openList.add(initialNode);
        while (!isEmpty(openList)) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            assert currentNode != null;
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<Node>();
    }

    public List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<Node>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void checkNode(Node current_node, int col, int row) {
        Node adjacentNode = getSearchArea()[row][col];
        int dRow = row - current_node.getRow();
        int dCol = col - current_node.getCol();

        // If diagonal, prevent corner cutting
        if (Math.abs(dRow) == 1 && Math.abs(dCol) == 1) {
            if (searchArea[current_node.getRow()][current_node.getCol() + dCol].isIs_block()
                    || searchArea[current_node.getRow() + dRow][current_node.getCol()].isIs_block()) {
                return;
            }
        }

        if (!adjacentNode.isIs_block() && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setParent(current_node);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(current_node);
                if (changed) {
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private void addAdjacentUpperRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int upper_row = row - 1;
        if (upper_row >= 0) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, upper_row);
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 1, upper_row);
            }
            checkNode(currentNode, col, upper_row);
        }
    }

    private void addAdjacentLowerRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int lower_row = row + 1;
        if (lower_row < getSearchArea().length) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, lower_row);
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 1, lower_row);
            }
            checkNode(currentNode, col, lower_row);
        }
    }

    private void addAdjacentMiddleRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, row);
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNode(currentNode, col + 1, row);
        }
    }

    private void addAdjacentNodes(Node currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentLowerRow(currentNode);
        addAdjacentMiddleRow(currentNode);
    }

}