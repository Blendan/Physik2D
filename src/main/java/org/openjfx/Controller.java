package org.openjfx;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.openjfx.physik.PhysicEnvironment;
import org.openjfx.physik.objects.PhyCircle;
import org.openjfx.physik.objects.PhyObj;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
	public Pane canvas;
	private PhysicEnvironment physicEnvironment;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		physicEnvironment = new PhysicEnvironment(canvas);
		PhyObj temp = new PhyCircle(new Circle(10, Color.CADETBLUE),physicEnvironment);
		temp.getObj().relocate(5, 5);
		physicEnvironment.addNew(temp);
	}
}
