package org.openjfx.physik.objects;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.openjfx.physik.PhysicEnvironment;

public class PhyCircle extends PhyObj
{
	@SuppressWarnings("FieldCanBeLocal")
	private boolean onFlor = false;

	public PhyCircle(Shape obj, PhysicEnvironment phyEn)
	{
		super(obj, phyEn);
	}


	@Override
	public void update(Pane pane)
	{
		double maxY = pane.getHeight();
		double maxX = pane.getWidth();


		onFlor = obj.getLayoutY() + ((Circle) obj).getRadius() >= maxY;


		if(!onFlor||phyEn.getGravity()<0)
		{
			dy += phyEn.getGravity()*(getMass()/5);
		}
		else if(dy>0)
		{
			if(obj.getLayoutY() + ((Circle) obj).getRadius() > maxY)
			{
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
			obj.setLayoutY(maxY-((Circle) obj).getRadius());
		}

		collision(true);

		if(obj.getLayoutY() + ((Circle) obj).getRadius() > maxY)
		{
			if(dy>0)
			{
				dy = -dy;
			}
		}

		if(obj.getLayoutY()-((Circle) obj).getRadius()<=0)
		{
			if(dy<0)
			{
				dy = -dy;
			}
		}

		if(obj.getLayoutX()+((Circle) obj).getRadius()>=maxX)
		{
			if(dx>0)
			{
				dx = -dx;
			}
		}
		else if(obj.getLayoutX()<((Circle) obj).getRadius())
		{
			if(dx<0)
			{
				dx = -dx;
			}
		}



		if(dy>0)
		{
			dy -= phyEn.getAirResistance();
		}
		else if(dy<0)
		{
			dy += phyEn.getAirResistance();
		}

		if(dx>0)
		{
			dx -= phyEn.getAirResistance();
		}
		else if(dx<0)
		{
			dx += phyEn.getAirResistance();
		}

	}
}
