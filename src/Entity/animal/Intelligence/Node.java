package Entity.animal.Intelligence;

public class Node {
    private int hVal;
    private int gVal;
    private int fVal;
    private int row;
    private int col;
    private boolean is_block;   // This variable to check if the object is blocks.
    private Node parent;

    // Constructor to create object Node.
    public Node(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    // Method Getter hVal
    public int getHVal() {
        return hVal;
    }

    // Method Setter hVal
    public void setHVal(int h) {
        this.hVal = h;
    }

    // Method Getter gVal
    public int getGVal() { return gVal; }

    // Method Setter gVal
    public void setGVal(int g) {
        this.gVal = g;
    }

    // Method Getter fVal
    public int getFVal() {
        return fVal;
    }

    // Method Setter fVal
    public void setFVal(int f) {
        this.fVal = f;
    }

    // Method Getter row
    public int getRow() {
        return row;
    }

    // Method Setter row
    public void setRow(int row) {
        this.row = row;
    }

    // Method Getter column
    public int getCol() {
        return col;
    }

    // Method Setter column
    public void setCol(int col) {
        this.col = col;
    }

    // Method Getter return boolean check if "is block?"
    public boolean isIs_block() {
        return is_block;
    }

    // Method Setter set boolean variable to "is block?"
    public void setIs_block(boolean is_block) {
        this.is_block = is_block;
    }

    // Method Getter to return parent object in Node class
    public Node getParent() {
        return parent;
    }

    // Method Setter to set parent object
    public void setParent(Node parent) {
        this.parent = parent;
        // Calculate cost based on position
        double distance = Math.sqrt(
                Math.pow(this.row - parent.getRow(), 2) +
                        Math.pow(this.col - parent.getCol(), 2)
        );
        this.gVal = (int) (parent.getGVal() + distance);
        this.fVal = this.gVal + this.hVal;
    }

    // Method calculateHeuristic() with parameter finalNode in Node class to apply AI in Doll enemy.
    public void calculateHeuristic(Node finalNode) {
        this.hVal = Math.abs(finalNode.getRow() - getRow()) + Math.abs(finalNode.getCol() - getCol());
    }

    public void setNodeData(Node currentNode) {
        int gCost = currentNode.getGVal();
        setParent(currentNode);
        setGVal(gCost);
        calculateFinalCost();
    }

    public boolean checkBetterPath(Node currentNode) {
        int gCost = currentNode.getGVal();
        if (gCost < getGVal()) {
            setNodeData(currentNode);
            return true;
        }
        return false;
    }

    public void calculateFinalCost() {
        int finalCost = getGVal() + getHVal();
        setFVal(finalCost);
    }

    // Override "equals" method with obj parameter in Object class to determine the feature in game.
    @Override
    public boolean equals(Object obj) {
        Node other = (Node) obj;
        return this.getRow() == other.getRow() && this.getCol() == other.getCol();
    }

    // Override output string statement
    @Override
    public String toString() {
        return "Node[row=" + row + ",col=" + col + "]";
    }
}
