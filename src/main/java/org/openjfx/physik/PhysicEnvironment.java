package org.openjfx.physik;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.openjfx.physik.objects.PhyObj;

import java.util.ArrayList;

public class PhysicEnvironment
{
	private Pane pane;
	private ArrayList<PhyObj> physicObjs = new ArrayList<>();

	private double gravity = 0.2;
	private double airResistance = 0.1;

	public PhysicEnvironment(Pane pane)
	{
		this.pane = pane;
	}

	public void addNew(PhyObj phyObj)
	{
		pane.getChildren().add(phyObj.getObj());

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
				t ->
				{
					phyObj.update(pane);

					phyObj.getObj().setLayoutX(phyObj.getObj().getLayoutX() + phyObj.getDx());
					phyObj.getObj().setLayoutY(phyObj.getObj().getLayoutY() + phyObj.getDy());
				}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	public double getGravity()
	{
		return gravity;
	}

	public void setGravity(double gravity)
	{
		this.gravity = gravity;
	}

	public double getAirResistance()
	{
		return airResistance;
	}

	public void setAirResistance(double airResistance)
	{
		this.airResistance = airResistance;
	}
}
