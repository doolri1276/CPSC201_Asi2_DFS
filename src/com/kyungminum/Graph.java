package com.kyungminum;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author alexguntermann
 */
public class Graph {

    private ArrayList<VertexList> vList;      //= new ArrayList<VertexList>(vCount);

    public Graph() {
        int vCount = 0;
        vCount = readInteger("Number of Vertices", 1);

        vList = new ArrayList<VertexList>(vCount);

        int i;
        for(i = 1; i < vCount+1; i++){
            vList.add(i-1, new VertexList(""+i));

            int nCount;
            do{
                nCount = readInteger("Number of neighbors for vertex # " + i, 0);
            }while(nCount > vCount);

            ArrayList<Vertex> neibs = new ArrayList<Vertex>(nCount);

            for (int k=0; k < nCount;k++){
                int vertexNum;
                boolean exists;
                do{
                    exists = false;
                    vertexNum = readInteger("Enter neighbor # " + (k+1) + " for vertex # " + i, 1);
                    for(Vertex v: neibs){
                        if(!exists && v.toString().equals(vertexNum+"")){
                            exists = true;
                        }
                    }

                } while(vertexNum>vCount || exists);
                neibs.add(new Vertex("" + vertexNum));
            }
            vList.get(i-1).setNeibs(neibs);
        }

    }

    public Graph(String file) throws FileNotFoundException {

        Scanner inFile = new Scanner(new FileReader("graphfile.txt"));

        int vCount = 0;
        vCount = inFile.nextInt();
        String trash = inFile.nextLine();
        vList = new ArrayList<VertexList>(vCount);
        int i = 0;

        for (i = 1; i < vCount + 1; i++) {

            vList.add(i - 1, new VertexList("" + 1));
            String neibslist = inFile.nextLine();
            String[] neibsArray = neibslist.split(" ");
            int[] neibsArray1 = new int[neibsArray.length];

            for (int j = 0; j < neibsArray.length; j++) {
                neibsArray1[j] = Integer.parseInt(neibsArray[j]);
            }

            ArrayList<Vertex> neibs = new ArrayList<Vertex>(neibsArray.length);
            for (int k = 0; k < neibsArray.length; k++) {
                int vertexNum = Integer.parseInt(neibsArray[k]);
                neibs.add(new Vertex("" + vertexNum));
            }
            vList.get(i - 1).setNeibs(neibs);

        }
        inFile.close();
    }

    public void display() {

        if(vListEmptyCheck()) return;

        for (int i = 1; i < vList.size() + 1; i++) {
            ArrayList<Vertex> neibs = vList.get(i - 1).getNeibs();

            System.out.print("neibs of # "+i+" = ");
            Iterator<Vertex> it = neibs.iterator();

            System.out.print("[");

            while (it.hasNext()) {
                System.out.print(it.next());
                if (it.hasNext()) {
                    System.out.print(", ");
                }
            }

            System.out.println("]");

        }

        System.out.println();
        System.out.println();
    }

    private boolean vListEmptyCheck(){
        if(vList == null || vList.size() == 0){
            showAlert("vList is empty.");
            return true;
        }
        return false;
    }

    private void showAlert(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }

    private int readInteger(String prompt, int min) {

        int val = 0;

        do {
            String ans = JOptionPane.showInputDialog(null, prompt, "");

            try {
                val = Integer.parseInt(ans);
            } catch (NumberFormatException nfe) {
                val = 0;
            }
        } while (val < min);

        return val;

    }

    public void BFS(int v){
        BFS(v+"");
    }
    public void BFS(String v){
        ArrayList<String> result = new ArrayList<>();
        Integer [] visited = new Integer[vList.size()];
        Arrays.fill(visited,0);
        Queue<String> queue = new LinkedList<>();

        int vIdx = Integer.parseInt(v)-1;
        if(vIdx>vList.size()){
            showAlert("Index "+v+"doesn't exist.");
            return;
        }

        queue.add(v);

        do {
            v=queue.poll();
            vIdx = Integer.parseInt(v)-1;

            result.add(v);
            visited[vIdx]=1;

            for(Vertex neib: vList.get(vIdx).getNeibs()){
                if(visited[Integer.parseInt(neib.toString())-1]==0 && !queue.contains(neib.toString()))
                    queue.add(neib.toString());
            }
            System.out.println("#" + v+" visited : " + Arrays.toString(visited) + ", Q : " + Arrays.toString(queue.toArray()) + ", Result : " + result.toString());

        }while(queue.size()>0);

        System.out.println("==============");
        System.out.println("BFS : "+result.toString());
        System.out.println();
        System.out.println();

    }

    public void DFS(int v){
        DFS(v+"");
    }
    public void DFS(String v){
        ArrayList<String> result = new ArrayList<>();
        int [] visited = new int[vList.size()];
        Arrays.fill(visited, 0);
        Stack<String> stack = new Stack<>();

        int vIdx = Integer.parseInt(v)-1;
        if(vIdx>vList.size()){
            showAlert("Index "+v+"doesn't exist.");
            return;
        }
        stack.add(v);

        do{

            if(Arrays.stream(visited).allMatch(n->n==1)){
                break;
            }

            v=stack.peek();
            vIdx = Integer.parseInt(v)-1;

            if(visited[vIdx]==0){
                result.add(v);
                visited[vIdx] = 1;
            }

            boolean stackAdded=false;
            for(Vertex neib: vList.get(vIdx).getNeibs()){
                if(visited[Integer.parseInt(neib.toString())-1]==0){

                    stack.add(neib.toString());
                    stackAdded = true;
                    break;
                }
            }
            System.out.println("#"+v+" visited : " + Arrays.toString(visited) + ", S : " + Arrays.toString(stack.toArray()) + ", Result : " + result.toString());

            if(!stackAdded)
                stack.pop();


        }while (stack.size()>0);

        System.out.println("==============");
        System.out.println("DFS : "+result.toString());
        System.out.println();
        System.out.println();
    }



    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Graph graph = null;
        System.out.println("Please input whether you would like to run from a file or input vertex's manually" + "\n" + "Press 1 for manual and"
                + " 2 for file");
        int choose = input.nextInt();
        if (choose == 1) {
            graph = new Graph();
        } else if (choose == 2) {
            try{
                graph = new Graph("file");
            }catch (FileNotFoundException e){
                JOptionPane.showMessageDialog(null, "No such file or directory.");
                return;
            }

        }
        graph.display();

        graph.DFS("5");
        graph.DFS(2);

        graph.BFS("5");
        graph.BFS(2);

    }

}

// Make sure that a user enters the number of vertices
// do...while repeates until a successful number entry

