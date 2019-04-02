import java.util.*;

/**
 * Arpad Szell arsz4862
 * Andreas Ährlund-Richter anah4939
 */

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

    int numberNodes;
    int numberEdges;
    public HashMap<T, Node<T>> nodes = new HashMap<>();

    //OBS! Our node assumes theres only one possible edge per two nodes
    private static class Node<T>{
        public T id;
        public HashMap<T, Integer> adjecent = new HashMap<>();

        public Node(T id){
            this.id = id;
        }

       }

       @Override
    public int getNumberOfNodes(){return numberNodes;}

    @Override
    public int getNumberOfEdges(){return numberEdges;}

    public boolean add(T newNode){
        //no duplicate nodes
        if(nodes.containsKey(newNode))
            return false;

        nodes.put(newNode, new Node(newNode));
        numberNodes++;
        return true;
    }

    @Override
    public boolean connect(T nodeOne, T nodeTwo, int cost){
        //the nodes must exist
        if((cost > 0) && nodes.containsKey(nodeOne) && nodes.containsKey(nodeTwo)){
            nodes.get(nodeOne).adjecent.put(nodeTwo,cost);
            nodes.get(nodeTwo).adjecent.put(nodeOne,cost);

            return true;
        }
        return false;
    }

    @Override
    public boolean isConnected(T nodeOne, T nodeTwo) {
        Node myNodeOne = nodes.get(nodeOne);
        if(myNodeOne != null)
            return  myNodeOne.adjecent.containsKey(nodeTwo);

        return false;
    }

    @Override
    public int getCost(T nodeOne, T nodeTwo){
        Integer value = null;
        if(nodes.get(nodeOne) != null)
            value = nodes.get(nodeOne).adjecent.get(nodeTwo);
        if(value != null)
            return value;

        return -1;
    }

    @Override
    public List<T> depthFirstSearch(T start, T end){
        HashSet<T> visited = new HashSet<>();
        List<T> path = new Stack<>();
        depthFirstSearch(start,end,visited,(Stack)path);

        return path;
    }

    //helper method
    private void depthFirstSearch(T start, T end, HashSet<T> visited, Stack<T> path){

        Node<T> currentNode = nodes.get(start);
        visited.add(start);
        path.push(start);
        Iterator<Map.Entry<T, Integer>> children = currentNode.adjecent.entrySet().iterator();
        if(visited.contains(end)){
            return;
        }

        while(children.hasNext()){
            Map.Entry<T,Integer> current = children.next();
            if(visited.contains(end))
                return;

            if(!visited.contains(current.getKey()))
                depthFirstSearch(current.getKey(), end, visited, path);
            //might be redundant
            if(visited.contains(end))
                return;
        }
        path.pop();
    }
    @Override
    public List<T> breadthFirstSearch(T start, T end){

        //kid, parent
        HashMap<T,T> parentsIds = new HashMap<>();
        LinkedList<T> workingNodesIds = new LinkedList<>();

        //start has no parent
        parentsIds.put(start,null);
        workingNodesIds.addLast(start);

        T currentNodeIds;
        while(!workingNodesIds.isEmpty()){
            currentNodeIds = workingNodesIds.removeFirst();
            workingNodesIds.addAll(workingNodesIds.size(), nodes.get(currentNodeIds).adjecent.keySet());
            //probably redundent and can be done with out
            LinkedList<T> children = new LinkedList<>();
            children.addAll(nodes.get(currentNodeIds).adjecent.keySet());
            T currentChildId = null;
            while(!children.isEmpty()){
                currentChildId = children.removeFirst();

                if(!parentsIds.containsKey(currentChildId)){
                    parentsIds.put(currentChildId, currentNodeIds);
                }
                if(currentChildId.equals((end))){
                    return shortestPath(parentsIds, start, end);
                }
            }
        }
        return shortestPath(parentsIds, start, end);
    }
    //helper method
    private List<T> shortestPath(HashMap<T,T> parents, T start, T end){
        List<T> shortestPath = new LinkedList<>();
        shortestPath.add(0,end);
        T next = end;
        if(parents.get(end) !=null){
            while((next = parents.get(next)) != null)
                shortestPath.add(0,next);
        }
        return shortestPath;
    }

    @Override
    public UndirectedGraph<T> minimumSpanningTree(){
        MyUndirectedGraph<T> minTree = new MyUndirectedGraph<>();
        Iterator<Map.Entry<T, Node<T>>> allNodes = nodes.entrySet().iterator();
        Node currentNode = allNodes.next().getValue();
        minTree.add((T) currentNode.id);

        int minDistance = -1;
        boolean loop = true;
        Map.Entry<T, Integer> minConnection;
        Node<T> hookNode;
        while(loop){ //adds one node at a ti,e
            Iterator<Map.Entry<T, Node<T>>> treeNodes = minTree.nodes.entrySet().iterator();
            minConnection = null;
            hookNode = null;
            while(treeNodes.hasNext()){
                Node<T> currentTreeNode = treeNodes.next().getValue();
                Iterator<Map.Entry<T,Integer>> adjacentConnections =
                        nodes.get(currentNode.id).adjecent.entrySet().iterator();
                while(adjacentConnections.hasNext()){
                    Map.Entry<T, Integer> currenctAllNodeConnection = adjacentConnections.next();
                    if(minConnection == null && !minTree.nodes.containsKey(currenctAllNodeConnection.getKey())) ||
                    (minConnection != null && 0 < (minConnection.getValue()).compareTo(currenctAllNodeConnection.getValue()))
                }
            }

        }

    }
}
