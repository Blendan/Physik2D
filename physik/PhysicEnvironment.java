package org.openjfx.physik;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.openjfx.physik.objects.PhyObj;

import java.util.ArrayList;

public class PhysicEnvironment
{
	private Pane pane;
	private ArrayList<PhyObj> physicObjs = new ArrayList<>();
	private ArrayList<Timeline> timelines = new ArrayList<>();

	private double speedPercent = 1;
	private double gravity = 0.2;
	private double airResistance = 0.001;
	private boolean pause = false;
	private ArrayList<EventHandler<ActionEvent>> keyframeEvents = new ArrayList<>();

	public PhysicEnvironment(Pane pane)
	{
		this.pane = pane;
	}

	public void clear()
	{
		physicObjs.clear();
		pane.getChildren().clear();

		timelines.forEach(Timeline::stop);
		timelines.clear();
		keyframeEvents.clear();
	}

	public void addNew(PhyObj phyObj)
	{
		pane.getChildren().add(phyObj.getObj());

		EventHandler<ActionEvent> temp =  t ->
		{
			if(!pause)
			{
				phyObj.update(pane);

				phyObj.getObj().setLayoutX(phyObj.getObj().getLayoutX() + phyObj.getDx());
				phyObj.getObj().setLayoutY(phyObj.getObj().getLayoutY() + phyObj.getDy());
			}
		};

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20*speedPercent),temp));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		timelines.add(timeline);
		keyframeEvents.add(temp);

		physicObjs.add(phyObj);
	}

	public void addForce(double x, double y, double force, double falloff)
	{
		System.out.println("TTTTTTTTTTTTTT");
		for (PhyObj v : physicObjs)
		{
			v.addForce(x,y,force,falloff);
		}
	}

	public void setSpeed(double percent)
	{
		timelines.forEach(Timeline::stop);

		speedPercent = percent;

		double newTime = percent * 20;
		timelines.forEach(v->{
			v.getKeyFrames().clear();
			v.getKeyFrames().add(new KeyFrame(Duration.millis(newTime),keyframeEvents.get(timelines.indexOf(v))));
		});

		timelines.forEach(Animation::play);
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
