package com.shenko.assteroids;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;

public class TextRenderer {
	
	private AssteroidsGameScreen Screen;

	private float[] PointsA = new float[] { 1, 0, 1, 3, 3, 4, 3, 1, 3, 2, 1, 2 };
	private float[] PointsB = new float[] { 1, 0, 1, 3, 3, 4, 3, 3, 1, 2, 3, 1, 1, 0 };
	private float[] PointsC = new float[] { 3, 1, 1, 0, 1, 3, 3, 4 };
	private float[] PointsD = new float[] { 1, 0, 1, 3, 3, 2, 3, 1, 1, 0 };
	private float[] PointsE = new float[] { 3, 1, 1, 0, 1, 3, 3, 4, 1, 3, 1, 2, 2, 2 };
	private float[] PointsF = new float[] { 1, 0, 1, 3, 3, 2, 3, 1, 1, 0 };
	private float[] PointsG = new float[] { 3, 4, 1, 3, 1, 0, 3, 1, 3, 2, 2, 2 };
	private float[] PointsH = new float[] { 1,0,1,3,1,2,3,2,3,4,3,1 };
	private float[] PointsI = new float[] { 1,3,3,4,2,3,2,1,3,1,1,0 };
	private float[] PointsJ = new float[] { 1,1,2,0,3,1,3,4,1,3 };
	private float[] PointsK = new float[] { 1,0,1,3,1,2,3,4,1,2,3,0 };
	private float[] PointsL = new float[] { 1,3,1,0,3,1 };
	private float[] PointsM = new float[] { 1,0,1,3,2,2,3,4,3,1 };
	private float[] PointsN = new float[] { 1,0,1,3,3,1,3,4 };
	private float[] PointsO = new float[] { 1,0,1,3,3,4,3,1,1,0 };
	private float[] PointsP = new float[] { 1,0,1,3,3,4,3,2,1,1 };
	private float[] PointsQ = new float[] { 1,0,1,3,3,4,3,0,3,1,1,0 };
	private float[] PointsR = new float[] { 1,0,1,3,3,4,3,2,1,1,3,0 };
	private float[] PointsS = new float[] { 1,0,3,1,3,2,1,2,1,3,3,4 };
	private float[] PointsT = new float[] { 1,3,3,4,2,3,2,0 };
	private float[] PointsU = new float[] { 1,3,1,0,3,1,3,4 };
	private float[] PointsV = new float[] { 1,3,2,1,3,4 };
	private float[] PointsW = new float[] { 1,3,1,0,2,2,3,1,3,4 };
	private float[] PointsX = new float[] { 1,0,3,4,2,2,1,3,3,1 };
	private float[] PointsY = new float[] { 1,1,1,0,3,2,3,4,3,2,1,3,1,4 };
	private float[] PointsZ = new float[] { 1,3,3,4,1,0,3,1 };
	
	private Polyline PolylineA;
	private Polyline PolylineB;
	private Polyline PolylineC;
	private Polyline PolylineD;
	private Polyline PolylineE;
	private Polyline PolylineF;
	private Polyline PolylineG;
	private Polyline PolylineH;
	private Polyline PolylineI;
	private Polyline PolylineJ;
	private Polyline PolylineK;
	private Polyline PolylineL;
	private Polyline PolylineM;
	private Polyline PolylineN;
	private Polyline PolylineO;
	private Polyline PolylineP;
	private Polyline PolylineQ;
	private Polyline PolylineR;
	private Polyline PolylineS;
	private Polyline PolylineT;
	private Polyline PolylineU;
	private Polyline PolylineV;
	private Polyline PolylineW;
	private Polyline PolylineX;
	private Polyline PolylineY;
	private Polyline PolylineZ;
	
	public TextRenderer()
	{
		
		PolylineA = new Polyline(PointsA);
		PolylineB = new Polyline(PointsB);
		PolylineC = new Polyline(PointsC);
		PolylineD = new Polyline(PointsD);
		PolylineE = new Polyline(PointsE);
		PolylineF = new Polyline(PointsF);
		PolylineG = new Polyline(PointsG);
		PolylineH = new Polyline(PointsH);
		PolylineI = new Polyline(PointsI);
		PolylineJ = new Polyline(PointsJ);	
		PolylineK = new Polyline(PointsK);
		PolylineL = new Polyline(PointsL);
		PolylineM = new Polyline(PointsM);
		PolylineN = new Polyline(PointsN);
		PolylineO = new Polyline(PointsO);
		PolylineP = new Polyline(PointsP);
		PolylineQ = new Polyline(PointsQ);
		PolylineR = new Polyline(PointsR);
		PolylineS = new Polyline(PointsS);
		PolylineT = new Polyline(PointsT);
		PolylineU = new Polyline(PointsU);
		PolylineV = new Polyline(PointsV);
		PolylineW = new Polyline(PointsW);
		PolylineX = new Polyline(PointsX);
		PolylineY = new Polyline(PointsY);
		PolylineZ = new Polyline(PointsZ);
	}
	
	public void DrawText(ShapeRenderer Renderer, String inText, Vector2 Anchor, float Scale)
	{
		int Digits = inText.length();
		
		Renderer.begin(ShapeType.Line);
		Renderer.setColor(1,1,1,1);
		
		for (int i=0; i < Digits; i++)
		{
			char c = inText.charAt(i);
			Polyline DrawPolyline = new Polyline(GetPolylineFromChar(c).getVertices());
			DrawPolyline.scale(Scale);
			DrawPolyline.setPosition(Anchor.x + (i * Scale * 4), Anchor.y);
			
			Renderer.polyline( DrawPolyline.getTransformedVertices() );
		}
		
		Renderer.end();
	}
	
	public void DrawTextCentered(ShapeRenderer Renderer, String inText, Vector2 Anchor, float Scale)
	{
		int Digits = inText.length();
		float width = Digits * Scale * 4;
		
		Renderer.begin(ShapeType.Line);
		Renderer.setColor(1,1,1,1);
		
		int j = (int)(Math.floor(Digits / 2) * -1);
		for (int i = 0; i < Digits; i++)
		{
			if (inText.charAt(i) == ' ')
			{
				j++;
			}
			else
			{
				char c = inText.charAt(i);
				Polyline DrawPolyline = new Polyline(GetPolylineFromChar(c).getVertices());
				DrawPolyline.scale(Scale);
				DrawPolyline.setPosition(Anchor.x + (j * Scale * 4), Anchor.y);
				j++;
				Renderer.polyline( DrawPolyline.getTransformedVertices() );
			}
		}
		
		Renderer.end();
	}
	
	public Polyline GetPolylineFromChar(char C)
	{
		switch (C)
		{
		case 'A':
			return PolylineA;
		case 'B':
			return PolylineB;
		case 'C':
			return PolylineC;
		case 'D':
			return PolylineD;
		case 'E':
			return PolylineE;
		case 'F':
			return PolylineF;
		case 'G':
			return PolylineG;
		case 'H':
			return PolylineH;
		case 'I':
			return PolylineI;
		case 'J':
			return PolylineJ;
		case 'K':
			return PolylineK;
		case 'L':
			return PolylineL;
		case 'M':
			return PolylineM;
		case 'N':
			return PolylineN;
		case 'O':
			return PolylineO;
		case 'P':
			return PolylineP;
		case 'Q':
			return PolylineQ;
		case 'R':
			return PolylineR;
		case 'S':
			return PolylineS;
		case 'T':
			return PolylineT;
		case 'U':
			return PolylineU;
		case 'V':
			return PolylineV;
		case 'W':
			return PolylineW;
		case 'X':
			return PolylineX;
		case 'Y':
			return PolylineY;
		case 'Z':
			return PolylineZ;
		}
		
		return PolylineA;
	}
}
