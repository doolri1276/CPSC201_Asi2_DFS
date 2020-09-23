package com.kyungminum;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author alexguntermann
 */
public class Graph {

    private ArrayList<VertexList> vList;      //= new ArrayList<VertexList>(vCount);

    public Graph() throws CancelException {
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

        if(!file.contains(".txt")){
            file+=".txt";
        }

        System.out.println("filename : " + file);

        Scanner inFile = new Scanner(new FileReader(file));

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
        String message = "";

        for (int i = 1; i < vList.size() + 1; i++) {
            ArrayList<Vertex> neibs = vList.get(i - 1).getNeibs();

            message+="neibs of # "+i+" = ";
            Iterator<Vertex> it = neibs.iterator();

            message+="[";

            while (it.hasNext()) {
                message+=it.next();
                if (it.hasNext()) {
                    message+=", ";
                }
            }

            message+="]\n";

        }
        JOptionPane.showMessageDialog(null, message, "DFS result", JOptionPane.INFORMATION_MESSAGE);

    }

    private boolean vListEmptyCheck(){
        if(vList == null || vList.size() == 0){
            showAlert("vList is empty.");
            return true;
        }
        return false;
    }

    private static void showAlert(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }

    private int readInteger(String prompt, int min) throws CancelException {

        int val = 0;

        do {
            String ans = JOptionPane.showInputDialog(null, prompt, "");

            if(ans == null){
                throw new CancelException();
            }


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
        String message = "";

        do {
            v=queue.poll();
            vIdx = Integer.parseInt(v)-1;

            result.add(v);
            visited[vIdx]=1;

            for(Vertex neib: vList.get(vIdx).getNeibs()){
                if(visited[Integer.parseInt(neib.toString())-1]==0 && !queue.contains(neib.toString()))
                    queue.add(neib.toString());
            }
            message+="#" + v+" visited : " + Arrays.toString(visited) + ", Q : " + Arrays.toString(queue.toArray()) + ", Result : " + result.toString()+"\n";

        }while(queue.size()>0);


        message+="====================\n";
        message+="BFS : "+result.toString();
        JOptionPane.showMessageDialog(null, message, "BFS result", JOptionPane.INFORMATION_MESSAGE);

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

        String message = "";

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
            message +="#"+v+" visited : " + Arrays.toString(visited) + ", S : " + Arrays.toString(stack.toArray()) + ", Result : " + result.toString()+"\n";

            if(!stackAdded)
                stack.pop();


        }while (stack.size()>0);

        message+="====================\n";
        message+="DFS : "+result.toString();
        JOptionPane.showMessageDialog(null, message, "DFS result", JOptionPane.INFORMATION_MESSAGE);

    }



    public static void main(String[] args) {
        String[] buttons1 = {"QUIT", "file", "manual"};
        String[] buttons2 = {"QUIT", "reset vertices", "BFS", "DFS", "display" };
        Graph graph = null;
        while (true){

            if(graph == null){
                int num = JOptionPane.showOptionDialog(null, "Please choose whether you would like to \nrun from a file or input vertex's manually","input vertices", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons1, null );

                switch (num){
                    case 0:
                        return;

                    case 1:
                        String fname = JOptionPane.showInputDialog(null, "Please enter file name\nex. graphfile.txt", "");

                        if(fname == null || fname.length() == 0){
                            showAlert("The file name is empty.");
                            break;
                        }

                        try{
                            graph = new Graph("./src/"+fname);//저장위치는 src바깥쪽임
                        }catch (FileNotFoundException e){
                            JOptionPane.showMessageDialog(null, "No such file or directory.");
                            graph = null;
                        }
                        break;
                    case 2:
                        try {
                            graph = new Graph();
                        } catch (CancelException e) {
                            graph = null;
                        }
                        break;
                }
            }else{
                int num = JOptionPane.showOptionDialog(null, "Please choose your action","input vertices", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons2, null );

                switch (num){
                    case 4:
                        graph.display();
                        break;
                    case 3:
                        try {
                            graph.DFS(graph.readInteger("[DFS] Please input first vertex", 1));
                        } catch (CancelException e) {

                        }

                        break;
                    case 2:
                        try {
                            graph.BFS(graph.readInteger("[DFS] Please input first vertex", 1));
                        } catch (CancelException e) {

                        }
                        break;
                    case 1:
                        graph = null;
                        break;
                    case 0:
                        return;
                }
            }

        }
    }

}

class CancelException extends Exception{

}
