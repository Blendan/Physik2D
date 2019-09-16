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
	private double airResistance = 0.001;
	private boolean pause = false;

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
					if(!pause)
					{
						phyObj.update(pane);

						phyObj.getObj().setLayoutX(phyObj.getObj().getLayoutX() + phyObj.getDx());
						phyObj.getObj().setLayoutY(phyObj.getObj().getLayoutY() + phyObj.getDy());
					}
				}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		physicObjs.add(phyObj);
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

	public ArrayList<PhyObj> getObjs()
	{
		return new ArrayList<PhyObj>(physicObjs);
	}

	public boolean isPause()
	{
		return pause;
	}

	public void setPause(boolean pause)
	{
		this.pause = pause;
	}
}
