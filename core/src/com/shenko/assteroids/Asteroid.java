package com.shenko.assteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Intersector;

public class Asteroid implements AsteroidInterface {
	
	// Class of an asteroid
	
	private Intersector Intersector;
	
	public AssteroidsGameScreen TheScreen;

	public Vector2 Location;
	public Vector2 Velocity;
	
	public float Rotation;
	public float RotationRate;
	
	public enum ESize { LARGE, MEDIUM, SMALL };
	public ESize Size; 
	
	public Polygon DrawPolygon;
	public Circle Collision;
	
	public boolean debug = false;
	
	// Draw points for the various asteroid sizes
	
	public float[] DrawPointsLarge = { 
			3.6526f, -4.0582f,
			1.4628f, -4.6098f,
			-0.3080f, -3.6778f,
			-2.7262f, -3.9250f,
			-3.9258f, -2.7457f,
			-4.4019f, -1.0527f,
			-3.1261f, 0.9636f,
			-3.8687f, 2.3713f,
			-3.3736f, 3.8930f,
			-1.2981f, 3.8930f,
			-0.0795f, 4.7871f,
			1.4438f, 4.4066f,
			1.6216f, 4.007f,
			0.4734f, 4.007f,
			1.6216f, 4.007f,
			1.7485f, 3.7218f,
			2.8858f, 3.3032f,
			0.740f, 3.3032f,
			2.8858f, 3.3032f,
			3.1956f, 3.1892f,
			3.4596f, 2.5614f,
			1.0828f, 2.5614f,
			3.4596f, 2.5614f,
			3.8434f, 1.6483f,
			1.3874f, 1.6483f,
			3.8434f, 1.6483f,
			3.9953f, 1.287f,
			3.7505f, 0.8494f,
			1.2541f, 0.8494f,
			3.7505f, 0.8494f,
			3.2718f, -0.0065f,
			1.2351f, -0.0065f,
			3.2718f, -0.0065f,
			3.6554f, -0.9007f,
			1.5588f, -0.9007f,
			3.6554f, -0.9007f,
			3.9953f, -1.7186f,
			1.9206f, -1.7186f,
			3.9953f, -1.7186f,
			4.3816f, -2.5936f,
			2.2252f, -2.5936f,
			4.3816f, -2.5936f,
			4.049f, -3.3355f,
			1.7682f, -3.3355f,
			4.049f, -3.3355f,
			3.5766f, -4.0773f,
			1.8635f, -4.0773f,
			3.5766f, -4.0773f
	};
	
	public float[] DrawPointsMedium = { 
			0.1703f, -1.0335f,
			-0.7104f, -0.8575f,
			-0.6807f, -0.9628f,
			-1.1416f, -0.9628f,
			-0.6807f, -0.9628f,
			-0.5240f, -1.5187f,
			-1.5361f, -1.5187f,
			-0.5240f, -1.5187f,
			-0.3902f, -1.9935f,
			-0.8545f, -2.4419f,
			-2.7601f, -1.8495f,
			-3.0644f, -0.8895f,
			-2.2477f, 0.1664f,
			-2.5199f, 1.0623f,
			-2.0555f, 1.6703f,
			-1.7993f, 2.3102f,
			-0.0859f, 2.7262f,
			0.0637f, 2.2350f,
			-0.5859f, 2.2350f,
			0.0637f, 2.2350f,
			0.1383f, 1.9903f,
			1.5635f, 1.9903f,
			1.4842f, 1.6979f,
			0.0822f, 1.6979f,
			1.4842f, 1.6979f,
			1.3276f, 1.1205f,
			0.1833f, 1.1205f,
			1.3276f, 1.1205f,
			1.2541f, 0.8494f,
			1.2475f, 0.5433f,
			0.3755f, 0.5433f,
			1.2475f, 0.5433f,
			1.2351f, -0.0256f,
			1.4446f, -0.2248f,
			0.6488f, -0.2248f,
			1.4446f, -0.2248f,
			1.7744f, -0.5384f,
			1.2154f, -0.5384f,
			1.7744f, -0.5384f,
			2.3271f, -1.0639f,
			1.0535f, -1.0639f,
			2.3271f, -1.0639f,
			2.3962f, -1.1295f,
			2.2503f, -1.4884f,
			1.4278f, -1.4884f,
			2.2503f, -1.4884f,
			1.9798f, -2.1535f,
			1.2592f, -2.5214f
		};

	public float[] DrawPointsSmall = { 
			0.8129f, -0.7726f,
			0.1001f, -0.5285f,
			-0.6841f, -0.8235f,
			-1.0711f, -0.6200f,
			-1.0711f, 0.2855f,
			-0.5313f, 0.9977f,
			0.2019f, 1.1097f,
			0.181f, 0.9388f,
			-0.0654f, 0.9388f,
			0.181f, 0.9388f,
			0.151f, 0.6925f,
			0.4771f, 0.5622f,
			0.0049f, 0.5622f,
			0.4771f, 0.5622f,
			0.8129f, 0.428f,
			0.8758f, 0.1919f,
			0.2414f, 0.1919f,
			0.8758f, 0.1919f,
			0.9729f, -0.173f,
			0.4266f, -0.173f,
			0.9729f, -0.173f,
			1.0613f, -0.505f,
			0.6503f, -0.505f,
			1.0613f, -0.505f			
		};

	
	
	public void Update(float Delta) {
		
		// Wrap location if offscreen
		if (Location.x < (Collision.radius * -1))
		{
			Location.x = Gdx.graphics.getWidth() + Collision.radius;
		}
		else if (Location.x > Gdx.graphics.getWidth() + Collision.radius)
		{
			Location.x = (Collision.radius * -1);
		}		
		
		if (Location.y < (Collision.radius * -1))
		{
			Location.y = Gdx.graphics.getHeight() + Collision.radius;
		}
		else if (Location.y > Gdx.graphics.getHeight() + Collision.radius)
		{
			Location.y = (Collision.radius * -1);
		}
		
		
		// Check collision vs the player ship
		if (!TheScreen.Ship.IsInvulnerable && !TheScreen.Ship.IsDead)
		{
			if ( Intersector.overlaps(Collision, TheScreen.Ship.Collision) )
			{
				// Collided with the player ship, blow it up
				TheScreen.PlayShipExplosion(TheScreen.Ship.Location, TheScreen.Ship.Velocity);
				TheScreen.Ship.Hit();
				System.out.print("Collision");
			}
		}
	}

	public void Render(ShapeRenderer Renderer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void TakeHit() {
		
		TheScreen.AsteroidExpired(this);
	}
	
}
