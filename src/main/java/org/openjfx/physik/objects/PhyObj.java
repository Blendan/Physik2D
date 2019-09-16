package org.openjfx.physik.objects;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import org.openjfx.physik.PhysicEnvironment;

public abstract class PhyObj
{
	Shape obj;
	double dx;
	double dy;
	PhysicEnvironment phyEn;

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

}
