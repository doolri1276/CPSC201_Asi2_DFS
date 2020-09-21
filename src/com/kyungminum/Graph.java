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

            System.out.print("neibs = ");
            Iterator<Vertex> it = neibs.iterator();

            System.out.print("[");

            while (it.hasNext()) {
                System.out.print(it.next());
                if (it.hasNext()) {
                    System.out.print(", ");
                }
            }

            System.out.print("]");

            System.out.println();

        }
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

    /*public VertexList getVertexListByName( ArrayList<VertexList> vList, String name){
        for(VertexList v: vList){
            if(v.getName().equals(name)){
                return v;
            }
        }
        return null;
    }

    public void DFS(String name){
        if(vListEmptyCheck()) return;

        VertexList root = getVertexListByName(vList, name);
        if(root == null){
            showAlert("name "+ name +" not found.");
            return;
        }

        DFS(root);



    }

    public void DFS(int idx){
        if(vListEmptyCheck()) return;

        if(idx > vList.size()){
            showAlert("index " + idx + " doesn't exist.");
            return;
        }

        DFS(vList.get(idx));

    }*/

    /*private void DFS(VertexList root){
        ArrayList<String> result = getDFS(new ArrayList<String>(), root);

        System.out.println("DFS result of # "+root.getName()+" : " + result.toString());
    }

    private ArrayList<String> getDFS(ArrayList<String> result, VertexList root){
        if(result == null){
            result = new ArrayList<>();
        }


        while(root != null){

            if(!result.contains(root.getName())) {
                result.add(root.getName());
                if(result.size() == 1){
                    result = getDFS(result, root);
                }
            }

            System.out.println(result.toString());

            root = getVertexListByName(vList, getNeibName(result, root));
            result = getDFS(result, root);
        }


        return result;


    }*/

    /*private ArrayList<String> getBFS(ArrayList<String> result, VertexList root){
        if(result == null){
            result = new ArrayList<>();
        }

        while(root != null){

            if(!result.contains(root.getName())) {
                result.add(root.getName());
                if(result.size() == 1){
                    result = getDFS(result, root);
                }
            }

            System.out.println(result.toString());

            root = getVertexListByName(vList, getNeibName(result, root));
            result = getDFS(result, root);
        }


        return result;


    }*/

    /*private String getNeibName(ArrayList<String> result, VertexList root){
        ArrayList<Vertex> neibs = root.getNeibs();
        for(int i=0;i<neibs.size();i++){
            boolean exist = false;
            for(String name : result){
                if(name.equals(neibs.get(i).getName())){
                    exist = true;
                    break;
                }
            }
            if(!exist)
                return neibs.get(i).getName();
        }

        return null;

    }*/

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

        do {
            vIdx = Integer.parseInt(v)-1;

            result.add(v);
            visited[vIdx]=1;
            System.out.println(vList.get(vIdx).getNeibs().toString());
            for(Vertex neib: vList.get(vIdx).getNeibs()){
                if(visited[Integer.parseInt(neib.toString())-1]==0 && !queue.contains(neib.toString()))
                    queue.add(neib.toString());
            }
            System.out.println("visited : " + Arrays.toString(visited) + ", Q : " + Arrays.toString(queue.toArray()) + ", Result : " + result.toString());

            v=queue.poll();
        }while(queue.size()>0);

        System.out.println();
        System.out.println("==============");
        System.out.println("BFS : "+result.toString());
        System.out.println();
        System.out.println();

    }

    public void DFS(String v){
        ArrayList<String> result = new ArrayList<>();

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
        graph.BFS("5");
        graph.BFS(2);
    }

}

// Make sure that a user enters the number of vertices
// do...while repeates until a successful number entry

