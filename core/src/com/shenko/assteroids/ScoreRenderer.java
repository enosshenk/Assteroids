package com.shenko.assteroids;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;

public class ScoreRenderer {
	
	// Class that holds the drawpoints and methods to draw numbers on screen
	// Also includes the support to draw little player ships for the UI
	
	private AssteroidsGameScreen Screen;
	
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
	
	public float[] PointsShip = new float[] { 0, 0, 2, 4, 4, 0, 2, 1, 0, 0 };
	
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
	
	public ScoreRenderer(AssteroidsGameScreen inScreen)
	{
		Screen = inScreen;
		
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
	
	public void DrawScore(ShapeRenderer Renderer, int Score, Vector2 Anchor, float Scale)
	{
		// Convert the incoming integer to a string
		String ScoreString = Integer.valueOf(Score).toString();
		int Digits = ScoreString.length();
		
		Renderer.begin(ShapeType.Line);
		Renderer.setColor(1,1,1,1);
		
		for (int i=0; i < Digits; i++)
		{
			// Loop through the characters of the string and draw them
			char c = ScoreString.charAt(i);
			Polyline DrawPolyline = new Polyline(GetPolylineFromChar(c).getVertices());
			DrawPolyline.scale(Scale);
			DrawPolyline.setPosition(Anchor.x + (i * Scale * 4), Anchor.y);
			
			Renderer.polyline( DrawPolyline.getTransformedVertices() );
		}
		
		Renderer.end();
	}
	
	public void DrawShips(ShapeRenderer Renderer, int Ships, Vector2 Anchor, float Scale)
	{
		// Draw little ships on the UI for life display
		Renderer.begin(ShapeType.Line);
		Renderer.setColor(1,1,1,1);
		
		for (int i=0; i<Ships; i++)
		{
			Polyline DrawPolyLine = new Polyline(PointsShip);
			DrawPolyLine.scale(Scale);
			DrawPolyLine.setPosition(Anchor.x + (i * Scale * 5), Anchor.y);
			
			Renderer.polyline(DrawPolyLine.getTransformedVertices());
		}
		
		Renderer.end();
	}
	
	public Polyline GetPolylineFromChar(char C)
	{
		switch (C)
		{
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
		
		return PolylineZero;
	}
}
