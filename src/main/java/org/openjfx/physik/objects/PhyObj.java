package org.openjfx.physik.objects;

import javafx.scene.layout.Pane;
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
		setCalculated(false);


		for (PhyObj o : list)
		{
			if (o != this && !o.isCalculated())
			{
				Shape shape = o.getObj();

				if (collides(o))
				{
					boolean noX = false;

					if (o.getDx() == 0 && getDx() == 0)
					{
						noX = true;
					}
					else if (o.getDx() < 0 && getDx() > 0) // <-o this->
					{
						if (shape.getLayoutX() < obj.getLayoutX())
						{
							noX = true;
						}
					}
					else if (o.getDx() > 0 && getDx() < 0)// <-this o->
					{
						if (shape.getLayoutX() > obj.getLayoutX())
						{
							noX = true;
						}
					}

					boolean noY = false;
					if (o.getDy() == 0 && getDy() == 0)
					{
						noY = true;
					}
					else if (o.getDy() < 0 && getDy() > 0) // <-o this->
					{
						if (shape.getLayoutY() < obj.getLayoutY())
						{
							noY = true;
						}
					}
					else if (o.getDy() > 0 && getDy() < 0)// <-this o->
					{
						if (shape.getLayoutY() > obj.getLayoutY())
						{
							noY = true;
						}
					}

					if (!noX || !noY)
					{
						double vx = shape.getLayoutX() - obj.getLayoutX();
						double vy = shape.getLayoutY() - obj.getLayoutY();

						double deg = Math.toDegrees(Math.atan2(vy, vx));

						if (deg < 0)
						{
							deg += 360;
						}

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


						double tempDx = dx;
						double tempDxO = o.dx;
						if (!noX)
						{
							dx = newVelocity(mass, o.mass, tempDx, tempDxO);
							o.dx = newVelocity(o.mass, mass, tempDxO, tempDx);
						}

						double deltaDx = tempDx - dx;
						double deltaDxO = tempDxO - o.dx;


						double tempDy = dy;
						double tempDyO = o.dy;
						if (!noY)
						{
							dy = newVelocity(mass, o.mass, tempDy, tempDyO);
							o.dy = newVelocity(o.mass, mass, tempDyO, tempDy);
						}

						double deltaDy = tempDy - dy;
						double deltaDyO = tempDyO - o.dy;

						if (percentHorizontal >= 1)
						{
							percentHorizontal = 0.9999;
						}
						else if (percentHorizontal <= 0)
						{
							percentHorizontal = 0.0001;
						}

						//TODO correct direction of force + add fro other object
						/*
							alpha = 90° - beta
							a = c·cos(beta)
							b = sqrt(c² - a²)

							c = dx
							a = new dx
							b = to add to dy

							first i get c from deltaDy and deltaDy.
							that c will now be rotated to the right angel
							then will be separated to deltaDy and deltaDx again and teh applied to the dx and dy
						 */
						//noinspection ConstantConditions
						if (true)
						{
							deg = percentHorizontal * 90;

							double[] temp =  getRightDDelta(deltaDx,deltaDy,deg);
							dx = -deltaDx + temp[0];
							dy = -deltaDy + temp[1];

							temp =  getRightDDelta(deltaDxO,deltaDyO,deg);
							o.dx = -deltaDxO + temp[0];
							o.dy = -deltaDyO + temp[1];
						}
					}

					 o.setCalculated(true);
					setCalculated(true);
					break;
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

	private double[] getRightDDelta(double deltaDx, double deltaDy, double deg)
	{
		double c = Math.sqrt(deltaDx*deltaDx+deltaDy*deltaDy);

		if(c>10)
		{
			c = 10;
		}

		double a = c * Math.cos(Math.toRadians(deg));
		double b = Math.sqrt(c * c - a * a);

		System.out.println(a);

		if (deltaDy < 0)
		{
			deltaDy = -a;
		}
		else
		{
			deltaDy = a;
		}


		if (deltaDx < 0)
		{
			deltaDx = -b;
		}
		else
		{
			deltaDx = b;
		}

		if(deltaDx > 10)
		{
			deltaDx = 10;
		}

		if(deltaDy > 10)
		{
			deltaDy = 10;
		}

		double[] temp = new double[2];
		temp[0] = deltaDx;
		temp[1] = deltaDy;

		return temp;
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
