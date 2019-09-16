package org.openjfx.physik.objects;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.openjfx.physik.PhysicEnvironment;

import java.util.ArrayList;

public abstract class PhyObj
{
	Shape obj;
	private double mass = 1;
	double dx;
	double dy;
	PhysicEnvironment phyEn;
	private boolean calculated = false;

	PhyObj(Shape obj, PhysicEnvironment phyEn)
	{
		this.obj = obj;
		this.phyEn = phyEn;
	}

	public Shape getObj()
	{
		return obj;
	}

	public abstract void update(Pane bounds);

	public double getDx()
	{
		return dx;
	}

	public void setDx(double dx)
	{
		this.dx = dx;
	}

	public double getDy()
	{
		return dy;
	}

	public void setDy(double dy)
	{
		this.dy = dy;
	}

	public boolean isCalculated()
	{
		return calculated;
	}

	public void setCalculated(boolean calculated)
	{
		this.calculated = calculated;
	}

	void collision()
	{
		ArrayList<PhyObj> list = phyEn.getObjs();

		for (PhyObj o: list)
		{
			if(o!=this)
			{
				Shape shape = o.getObj();

				if(collides(o))
				{
					boolean noX = false;

					if(o.getDx()==0&&getDx()==0)
					{
						noX = true;
					}
					else if(o.getDx()<0&&getDx()>0) // <-o this->
					{
						if(shape.getLayoutX()<obj.getLayoutX())
						{
							noX = true;
						}
					}
					else if(o.getDx()>0&&getDx()<0)// <-this o->
					{
						if(shape.getLayoutX()>obj.getLayoutX())
						{
							noX = true;
						}
					}

					boolean noY = false;
					if(o.getDy()==0&&getDy()==0)
					{
						noY = true;
					}
					else if(o.getDy()<0&&getDy()>0) // <-o this->
					{
						if(shape.getLayoutY()<obj.getLayoutY())
						{
							noY = true;
						}
					}
					else if(o.getDy()>0&&getDy()<0)// <-this o->
					{
						if(shape.getLayoutY()>obj.getLayoutY())
						{
							noY = true;
						}
					}

					if(!noX||!noY)
					{
						System.out.println("DONG");
						/*
						double vx = shape.getLayoutX() - obj.getLayoutX();
						double vy = shape.getLayoutY() - obj.getLayoutY();

						double dotPro = vx * 0 + vy * -1;
						double mag1 = Math.sqrt(vx * vx + vy * vy);
						double mag2 = Math.sqrt(1);

						double deg = Math.cos(dotPro / (mag1 * mag2));

						double number = deg / 360;

						double percentHorizontal;

						if ((number / 0.25) % 2 == 0) //vertical to horizontal
						{
							percentHorizontal = 1 - (number % 0.25) * 4;
						}
						else //horizontal to vertical
						{
							percentHorizontal = (number % 0.25) * 4;
						}
						*/


						if(!noX)
						{
							double tempDx = dx;
							double tempDxO = o.dx;

							dx = newVelocity(mass, o.mass, tempDx, tempDxO);
							o.dx = newVelocity(o.mass, mass, tempDxO, tempDx);
						}

						if(!noY)
						{
							double tempDy = dy;
							double tempDyO = o.dy;

							dy = newVelocity(mass, o.mass, tempDy, tempDyO);
							o.dy = newVelocity(o.mass, mass, tempDyO, tempDy);
						}

					}
				}
			}
		}
	}

	private boolean collides(PhyObj block)
	{
		boolean collisionDetected = false;

		if (block.obj.getBoundsInParent().intersects(obj.getBoundsInParent()))
		{
			collisionDetected = true;
		}

		return collisionDetected;
	}

	private void checkBounds(Shape block) {

	}

	private double newVelocity(double m1, double m2, double v1, double v2)
	{
		double i1 = ((m1-m2)/(m1+m2))*v1;
		double i2 = ((m2*2)/(m1+m2))*v2;

		return i1 + i2;

	}

	public double getMass()
	{
		return mass;
	}

	public void setMass(double mass)
	{
		this.mass = mass;
	}
}
