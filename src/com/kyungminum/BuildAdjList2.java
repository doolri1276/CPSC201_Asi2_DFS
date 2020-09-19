package com.kyungminum;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class BuildAdjList2 {

    public static void main(String[] s) {
	    int vCount = 0;

	    vCount = readInteger("Number of Vertices", 1);

        ArrayList<VertexList> vList = new ArrayList<VertexList>(vCount);

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

        for (i=1; i<vCount+1;i++){
            ArrayList<Vertex> neibs = vList.get(i-1).getNeibs();
            System.out.print("neibs = ");
            Iterator<Vertex> it = neibs.iterator();

            System.out.print("[");

            while (it.hasNext()){
                System.out.print(it.next());
                if (it.hasNext())
                    System.out.print(", ");
            }

            System.out.print("]");
            System.out.println();
        }



    }

    private static int readInteger(String prompt, int min){
        int val = 0;

        do {
            String ans = JOptionPane.showInputDialog(null, prompt, "");
            try{
                val = Integer.parseInt(ans);
            }catch (NumberFormatException nfe){
                val = 0;
            }
        }while (val < min);

        return val;


    }
}
