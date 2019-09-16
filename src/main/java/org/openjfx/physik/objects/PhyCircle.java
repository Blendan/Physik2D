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


		if(!onFlor)
		{
			dy += phyEn.getGravity();
		}
		else if(dy>0)
		{
			if(obj.getLayoutY() + ((Circle) obj).getRadius() > maxY)
			{
				dy = -dy + ((Circle) obj).getRadius()/5;

				if(dy>-0.3)
				{
					dy = 0;
				}


				System.out.println(dy);
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

		collision();

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
