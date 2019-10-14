package org.openjfx.physik.objects;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.openjfx.physik.PhysicEnvironment;

import java.util.Random;

public class PhyRectangle extends PhyObj
{
	Random rand = new Random();
	private boolean onFlor = false;

	public PhyRectangle(Shape obj, PhysicEnvironment phyEn)
	{
		super(obj, phyEn);
	}

	@Override
	public void update(Pane pane)
	{
		double maxY = pane.getHeight();
		double maxX = pane.getWidth();
		boolean collided = false;


		onFlor = obj.getLayoutY() + ((Rectangle) obj).getHeight() >= maxY;


		if(!onFlor||phyEn.getGravity()<0)
		{
			dy += phyEn.getGravity()*(getMass()/5);
		}
		else if(dy>0)
		{
			if(obj.getLayoutY() + ((Rectangle) obj).getHeight() > maxY)
			{
				collided = true;
				dy = -dy + getMass()*phyEn.getGravity();

				if(dy>-0.3&&phyEn.getGravity()!=0)
				{
					dy = 0;
				}
			}
			else
			{
				dy = 0;
			}
		}
		else if(dy==0)
		{
			obj.setLayoutY(maxY-((Rectangle) obj).getHeight());
		}

		collision(false);


		if(obj.getLayoutY() + ((Rectangle) obj).getHeight() > maxY)
		{
			if(dy>0)
			{
				dy = -dy;
				collided = true;
			}
		}

		if(obj.getLayoutY()<=0)
		{
			if(dy<0)
			{
				dy = -dy;
				collided = true;
			}
		}

		if(obj.getLayoutX()+((Rectangle) obj).getWidth()>=maxX)
		{
			if(dx>0)
			{
				dx = -dx;
				collided = true;
			}
		}
		else if(obj.getLayoutX()<0)
		{
			if(dx<0)
			{
				dx = -dx;
				collided = true;
			}
		}


		if(collided)
		{
			int r = rand.nextInt(255);
			int g = rand.nextInt(255);
			int b = rand.nextInt(255);

			obj.setFill(Color.rgb(r, g, b));
		}
	}
}
