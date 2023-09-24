// PROG2 VT2023, Inlamningsuppgift, del 1
// Grupp 077
// Sara Berg sabe4314
import java.util.*;

public class ListGraph<N> implements Graph<N>{
	
	private Map<N, Set<Edge<N>>> nodes;
	
	public ListGraph() {
		this.nodes = new HashMap<>();
	}
	
	public void add(N node) {
		if (!nodes.containsKey(node)) {
			nodes.putIfAbsent(node, new HashSet<>());
		}
	}
	
	public void connect(N node1, N node2, String name, int weight) { //complete

		validateNode(node1);
		validateNode(node2);
		validateWeight(weight);
		if (validateIfEdgeExists(node1, node2)) {
			throw new IllegalStateException("warning: edge already exists");
		}
		
		
		Set<Edge<N>> firstEdges = nodes.get(node1);
        Set<Edge<N>> secondEdges = nodes.get(node2);
        
        firstEdges.add(new Edge<N>(node2, name, weight));
        secondEdges.add(new Edge<N>(node1, name, weight));
		
	}
	
	public void setConnectionWeight(N node1, N node2, int weight) { //complete
		
		validateNode(node1);
		validateNode(node2);
		validateWeight(weight);
		if (!validateIfEdgeExists(node1, node2)) {
			throw new NoSuchElementException("warning: edge does not exist");
		}
		
        
        Edge<N> firstEdge = null;
        for (Edge<N> edge:nodes.get(node1)) {
        	N destination = edge.getDestination();
        	if (destination.equals(node2)) {
        		firstEdge = edge;
        		break;
        	}
        }
        
        Edge<N> secondEdge = null;
        for (Edge<N> edge:nodes.get(node2)) {
        	N destination = edge.getDestination();
        	if (destination.equals(node1)) {
        		secondEdge = edge;
        		break;
        	}
        }
        
        
        firstEdge.setWeight(weight);
        secondEdge.setWeight(weight);
        
        
	}
	
	public Set<N> getNodes(){ //complete
		Set<N> allNodes = new HashSet<>();
		allNodes = nodes.keySet();
		return allNodes;
	}
	
	public Collection<Edge<N>> getEdgesFrom(N node){ //complete
		validateNode(node);
		Set<Edge<N>> edges = nodes.get(node);
		return edges;
	}
	
	public void remove(N node){ //complete
		validateNode(node);
		
		for (N currentNode : nodes.keySet()) {
			Edge foundEdge = null;
			for (Edge<N> edge : nodes.get(currentNode)) {
				if(edge.getDestination().equals(node)) {
					foundEdge = edge;
				}
			}
			if (!(foundEdge == null)) {
				Set<Edge<N>> edges = nodes.get(currentNode);
				edges.remove(foundEdge);
			}
		}
		nodes.remove(node);
	}
	
	public void disconnect(N node1, N node2) {
		validateNode(node1);
		validateNode(node2);
		if (!validateIfEdgeExists(node1, node2)) {
			throw new IllegalStateException("warning: edge does not exist");
		}
		
		Edge edge1 = getEdgeBetween(node1, node2);
		Edge edge2 = getEdgeBetween(node2, node1);
		
		Set<Edge<N>> firstEdges = nodes.get(node1);
        Set<Edge<N>> secondEdges = nodes.get(node2);
        
        firstEdges.remove(edge1);
        secondEdges.remove(edge2);
        
	}
	

	public Edge<N> getEdgeBetween(N node1, N node2){ //complete
		validateNode(node1);
		validateNode(node2);
        
        Edge<N> firstEdge = null;
        for (Edge<N> edge:nodes.get(node1)) {
        	N destination = edge.getDestination();
        	if (destination.equals(node2)) {
        		firstEdge = edge;
        		break;
        	}
        }
        return firstEdge;
	}
	
	public boolean pathExists(N root, N end) {
		if (!nodes.containsKey(root)||!nodes.containsKey(end)) {
			return false;
		} else {
			Set<N> visited = new HashSet<>();
			visited = dfs(root, visited);
			return visited.contains(end);

		}
	}
	
	private Set<N> dfs (N root, Set<N> visited){
		Stack<N> stack = new Stack<>();
		stack.push(root);
		
		while(!stack.isEmpty()) {
			N currentNode = stack.pop();
			if (!visited.contains(currentNode)) {
				visited.add(currentNode);
				for (Edge<N> edge : nodes.get(currentNode)) {
					N neighbor = edge.getDestination();
					stack.push(neighbor);
				}
			}
		}
		return visited;
	}
	
	public List<Edge<N>> getPath(N from, N to){
		//List<Edge<N>> listOfEdges = new ArrayList<>();
		Set<N> visited = new HashSet<>();
		//Stack<N> path = new Stack<>();
		Stack<Edge<N>> path = new Stack<>();
		
		//path.push(from);

		dfsGetPath(from, to, visited, path);
		//return path;
		
		if (path.isEmpty()) {
			return null;
		} else {
			List <Edge<N>> listOfEdges = (List<Edge<N>>)(Object) Arrays.asList(path.toArray());
			return listOfEdges;
		}

	}
	
	private Stack<Edge<N>> dfsGetPath(N current, N destination, Set<N> visited, Stack<Edge<N>> pathSoFar) {
		visited.add(current);
		if(current.equals(destination)) {
			//pathSoFar.push(current);
			return pathSoFar;
		}
		
		for (Edge<N> edge : nodes.get(current)) {
			if(!visited.contains(edge.getDestination())) {
				pathSoFar.push(edge);
				Stack<Edge<N>> p = dfsGetPath(edge.getDestination(), destination, visited, pathSoFar);
				
				if(!p.isEmpty()) {
					return p;
				} else {
					pathSoFar.pop();
				}
			}
		}
		return new Stack<Edge<N>>();
		
	}
	
/*	private Stack<N> dfsGetPath(N current, N destination, Set<N> visited, Stack<N> pathSoFar) {
		visited.add(current);
		if(current.equals(destination)) {
			pathSoFar.push(current);
			System.out.print("found");
			return pathSoFar;
		}
		
		for (Edge<N> edge : nodes.get(current)) {
			if(!visited.contains(edge.getDestination())) {
				pathSoFar.push(current);
				Stack<N> p = dfsGetPath(edge.getDestination(), destination, visited, pathSoFar);
				
				if(!p.isEmpty()) {
					return p;
				} else {
					pathSoFar.pop();
				}
			}
		}
		return new Stack<N>();
		
	}*/
	


	private void validateNode(N node) {
		if (!nodes.containsKey(node)) {
			throw new NoSuchElementException("warning: node does not exist");
		}
	}
	
	private void validateEdge(N node1, N node2) { //ex if edge exists
		Set<Edge<N>> firstEdges = nodes.get(node1);
        
        Edge<N> firstEdge = null;
        for (Edge<N> edge:firstEdges) {
        	N destination = edge.getDestination();
        	if (destination.equals(node2)) {
        		firstEdge = edge;
        		break;
        	}
        }
        
        if(firstEdge!=null) {
        	throw new IllegalStateException("warning: edge already exists");
        }
	}
	
	public boolean validateIfEdgeExists(N node1, N node2) { //see if edge exists var förut private
		Edge<N> firstEdge = null;
        for (Edge<N> edge:nodes.get(node1)) {
        	N destination = edge.getDestination();
        	if (destination.equals(node2)) {
        		firstEdge = edge;
        		break;
        	}
        }
        
        return firstEdge != null;
        
        /*if (firstEdge == null) {
        	return false;
        } else {
        	return true;
        }*/
        
	}
	

	
	private void validateWeight(int weight) { //varning
		if (weight < 0) {
			throw new IllegalArgumentException("warning: weight can't be negative");
		}
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();  
		for(N v: nodes.keySet()) {
            b.append(v.toString() + " -> ");
            for(Edge<N> e: nodes.get(v)) {
                b.append(e.toString() + " ");
            }
            b.append("\n");
        }
        return b.toString();
	}

}
