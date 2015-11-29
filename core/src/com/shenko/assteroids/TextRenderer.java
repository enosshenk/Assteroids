package com.shenko.assteroids;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;

public class TextRenderer {
	
	// A class that renders letters as polylines
	
	private AssteroidsGameScreen Screen;

	// Raw coordinate points for each letter
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
	private float[] PointsOne = new float[] { 1, 3, 2, 4, 2, 0 };
	private float[] PointsTwo = new float[] { 1, 3, 2, 4, 3, 3, 3, 2, 1, 2, 1, 1, 2, 0, 3, 1 };
	private float[] PointsThree = new float[] { 1, 3, 2, 4, 3, 3, 3, 2, 2, 2, 3, 2, 3, 1, 2, 0, 1, 1 };
	private float[] PointsFour = new float[] { 2, 0, 2, 4, 1, 2, 3, 2 };
	private float[] PointsFive = new float[] { 1, 1, 2, 0, 3, 1, 3, 2, 1, 2, 1, 3, 2, 4, 3, 3 };
	private float[] PointsSix = new float[] { 3, 3, 2, 4, 1, 3, 1, 1, 2, 0, 3, 1, 2, 2, 1, 1 };
	private float[] PointsSeven = new float[] { 1, 4, 3, 4, 1, 0 };
	private float[] PointsEight = new float[] { 1, 3, 2, 4, 3, 3, 1, 1, 2, 0, 3, 1, 1, 3 };
	private float[] PointsNine = new float[] { 1, 1, 2, 0, 3, 1, 3, 3, 2, 4, 1, 3, 2, 2, 3, 3 };
	private float[] PointsZero = new float[] { 2, 0, 1, 1, 1, 3, 2, 4, 3, 3, 3, 1, 2, 0 };	
	
	
	// Polylines for each letter
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
	private Polyline PolylineOne;
	private Polyline PolylineTwo;
	private Polyline PolylineThree;
	private Polyline PolylineFour;
	private Polyline PolylineFive;
	private Polyline PolylineSix;
	private Polyline PolylineSeven;
	private Polyline PolylineEight;
	private Polyline PolylineNine;
	private Polyline PolylineZero;
	
	public TextRenderer()
	{
		
		// This is so awful.
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
		PolylineOne = new Polyline(PointsOne);
		PolylineTwo = new Polyline(PointsTwo);
		PolylineThree = new Polyline(PointsThree);
		PolylineFour = new Polyline(PointsFour);
		PolylineFive = new Polyline(PointsFive);
		PolylineSix = new Polyline(PointsSix);
		PolylineSeven = new Polyline(PointsSeven);
		PolylineEight = new Polyline(PointsEight);
		PolylineNine = new Polyline(PointsNine);
		PolylineZero = new Polyline(PointsZero);		
	}
	
	public void DrawText(ShapeRenderer Renderer, String inText, Vector2 Anchor, float Scale)
	{
		// Draws the given string at the given coordinates, left justified with scale
		
		int Digits = inText.length();	// Count the characters of the string
		
		Renderer.begin(ShapeType.Line);
		Renderer.setColor(1,1,1,1);
		
		for (int i=0; i < Digits; i++)
		{
			char c = inText.charAt(i);		// Get the current character
			Polyline DrawPolyline = new Polyline(GetPolylineFromChar(c).getVertices());	// Make dat polyline
			DrawPolyline.scale(Scale);
			DrawPolyline.setPosition(Anchor.x + (i * Scale * 4), Anchor.y);		// Position the current polyline spaced out and scaled
			
			Renderer.polyline( DrawPolyline.getTransformedVertices() );
		}
		
		Renderer.end();
	}
	
	public void DrawTextCentered(ShapeRenderer Renderer, String inText, Vector2 Anchor, float Scale)
	{
		// Draws given string at given location but centered
		
		int Digits = inText.length();
		float width = Digits * Scale * 4;	// Get estimated screen width of the whole drawn string. Each character is 4 units wide.
		
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
		case '1':
			return PolylineOne;
		case '2':
			return PolylineTwo;
		case '3':
			return PolylineThree;
		case '4':
			return PolylineFour;
		case '5':
			return PolylineFive;
		case '6':
			return PolylineSix;
		case '7':
			return PolylineSeven;
		case '8':
			return PolylineEight;
		case '9':
			return PolylineNine;
		case '0':
			return PolylineZero;
		}
		
		return PolylineA;
	}
}
