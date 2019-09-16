package org.openjfx.physik.objects;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.openjfx.physik.PhysicEnvironment;

public class PhyCircle extends PhyObj
{
	private boolean onFlor = false;

	public PhyCircle(Shape obj, PhysicEnvironment phyEn)
	{
		super(obj, phyEn);
	}


	@Override
	public void update(Pane pane)
	{
		Bounds bounds = pane.getBoundsInLocal();
		onFlor = obj.getLayoutY() + ((Circle) obj).getRadius() >= bounds.getHeight();

		if(!onFlor)
		{
			dy += phyEn.getGravity();
		}
		else if(dy>0)
		{
			if(obj.getLayoutY() + ((Circle) obj).getRadius() > bounds.getHeight())
			{
				dy = -dy + ((Circle) obj).getRadius()/10;

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
			obj.setLayoutY(bounds.getMaxY()-((Circle) obj).getRadius());
			System.out.println("DY: "+bounds.getMaxY());
			System.out.println(obj.getLayoutY() + ((Circle) obj).getRadius() - bounds.getHeight());
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
