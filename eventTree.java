class EventNode {
    String eventDate;
    String eventName;
    String location;
    EventNode left, right;
    int height;

    public EventNode(String eventDate, String eventName, String location) {
        this.eventDate = eventDate;
        this.eventName = eventName;
        this.location = location;
        this.height = 1;
    }
}

class EventAVLTree {
    private EventNode root;
    public int getHeight(EventNode N) {

        return (N == null) ? 0 : N.height;
    }

    public EventNode rightRotate(EventNode y) {
        EventNode x = y.left;
        EventNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    public EventNode leftRotate(EventNode x) {
        EventNode y = x.right;
        EventNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }

    public int getBalance(EventNode N) {

        return (N == null) ? 0 : getHeight(N.left) - getHeight(N.right);
    }

    public EventNode insert(EventNode node, String eventDate, String eventName, String location) {
        if (node == null)
            return new EventNode(eventDate, eventName, location);

        if (eventDate.compareTo(node.eventDate) < 0)
            node.left = insert(node.left, eventDate, eventName, location);
        else if (eventDate.compareTo(node.eventDate) > 0)
            node.right = insert(node.right, eventDate, eventName, location);
        else
            return node;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);

        if (balance > 1 && eventDate.compareTo(node.left.eventDate) < 0)
            return rightRotate(node);

        if (balance < -1 && eventDate.compareTo(node.right.eventDate) > 0)
            return leftRotate(node);

        if (balance > 1 && eventDate.compareTo(node.left.eventDate) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && eventDate.compareTo(node.right.eventDate) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void inOrder(EventNode node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println("Event: " + node.eventName + " | Date: " + node.eventDate + " | Location: " + node.location);
            inOrder(node.right);
        }
    }

    public void addEvent(String eventDate, String eventName, String location) {
        root = insert(root, eventDate, eventName, location);
    }

    public void displayEvents() {
        inOrder(root);
    }
}
