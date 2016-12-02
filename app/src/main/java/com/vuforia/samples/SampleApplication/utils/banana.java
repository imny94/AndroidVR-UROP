package com.vuforia.samples.SampleApplication.utils;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

/**
 * This file created by dragon on 2016/7/29 21:58,belong to com.vuforia.samples.SampleApplication.utils .
 */
public class banana extends MeshObject {
    private  static final String TAG="modelTest";

    private Buffer mVertBuff;
    private Buffer mTexCoordBuff;
    private Buffer mNormBuff;//normal
    private int verticesNumber = 0;
    private AssetManager assetManager;

    public banana(AssetManager inputassetManager){
        this.assetManager = inputassetManager;
        setVerts();
        setTexCoords();
        setNorms();
    }
    double[] banana_VERTS;
    double[] banana_TEX_COORDS;
    double[] banana_NORMS;

    InputStream inputFile = null;

    private int loadVertsFromModel(String fileName) throws IOException{
        try{
            inputFile = assetManager.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputFile));
            String line = reader.readLine();
            int floatsToRead = Integer.parseInt(line);
            banana_VERTS = new double[3*floatsToRead];
            for(int i=0;i<floatsToRead;i++){
                String curline = reader.readLine();
                if(curline.indexOf('/')>=0){
                    i--;
                    continue;
                }

                String floatStrs[] = curline.split(",");
                banana_VERTS[3 * i] = Float.parseFloat(floatStrs[0]);
                banana_VERTS[3 * i + 1] = Float.parseFloat(floatStrs[1]);
                banana_VERTS[3 * i + 2] = Float.parseFloat(floatStrs[2]);

            }
            return floatsToRead;
        } finally {
            if(inputFile !=null){
                inputFile.close();
            }
        }
    }
    private int loadTexCoordsFromModel(String fileName)
            throws IOException
    {
        try
        {
            inputFile = assetManager.open(fileName);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputFile));

            String line = reader.readLine();

            int floatsToRead = Integer.parseInt(line);
            banana_TEX_COORDS = new double[2*floatsToRead];


            for (int i = 0; i < floatsToRead; i++)
            {

                String curline = reader.readLine();
                if( curline.indexOf('/') >= 0 ){
                    i--;
                    continue;
                }


                String floatStrs[] = curline.split(",");

                banana_TEX_COORDS[2*i] = Float.parseFloat(floatStrs[0]);
                banana_TEX_COORDS[2*i+1] = Float.parseFloat(floatStrs[1]);
            }
            return floatsToRead;
        } finally
        {
            if (inputFile != null)
                inputFile.close();
        }
    }

    private int loadNormsFromModel(String fileName)
            throws IOException
    {
        try
        {
            inputFile = assetManager.open(fileName);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputFile));

            String line = reader.readLine();
            int floatsToRead = Integer.parseInt(line);
            banana_NORMS = new double[3*floatsToRead];


            for (int i = 0; i < floatsToRead; i++)
            {

                String curline = reader.readLine();
                if( curline.indexOf('/') >= 0 ){
                    i--;
                    continue;
                }


                String floatStrs[] = curline.split(",");

                banana_NORMS[3*i] = Float.parseFloat(floatStrs[0]);
                banana_NORMS[3*i+1] = Float.parseFloat(floatStrs[1]);
                banana_NORMS[3*i+2] = Float.parseFloat(floatStrs[2]);
            }

            return floatsToRead;

        } finally
        {
            if (inputFile != null)
                inputFile.close();
        }
    }

    private void setVerts(){
        int num = 0;
        try{
            num = loadVertsFromModel("ImageTargets/model/verts.txt");

        } catch(IOException e){
            e.printStackTrace();
        }
        mVertBuff = fillBuffer(banana_VERTS);
        verticesNumber = num;
    }
    private void setTexCoords()
    {
        int num = 0;
        try {
            num = loadTexCoordsFromModel("ImageTargets/model/texcoords.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mTexCoordBuff = fillBuffer(banana_TEX_COORDS);

    }
    private void setNorms()
    {
        int num = 0;
        try {
            num = loadNormsFromModel("ImageTargets/model/norms.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mNormBuff = fillBuffer(banana_NORMS);
    }
    public int getNumObjectIndex()
    {
        return 0;
    }


    @Override
    public int getNumObjectVertex()
    {
        return verticesNumber;
    }
    @Override
    public Buffer getBuffer(BUFFER_TYPE bufferType)
    {
        Buffer result = null;
        switch (bufferType)
        {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff;
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = mTexCoordBuff;
                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff;
                break;
            default:
                break;

        }

        return result;
    }

}