package org.openjfx;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.openjfx.physik.PhysicEnvironment;
import org.openjfx.physik.objects.PhyCircle;
import org.openjfx.physik.objects.PhyObj;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
	public Pane canvas;
	public Button btnRender;
	public TextField txtGravity;
	public Button btnPause;
	public Button btnRestart;
	public TextField txtSpeed;
	private PhysicEnvironment physicEnvironment;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		physicEnvironment = new PhysicEnvironment(canvas);

		btnPause.setOnAction(e->physicEnvironment.setPause(!physicEnvironment.isPause()));

		btnRestart.setOnAction(e->start());

		btnRender.setOnAction(e->renderSettings());

		physicEnvironment.setGravity(0.2);
		physicEnvironment.setAirResistance(0);
		NumberFormat formatter = new DecimalFormat("#0.00");
		txtGravity.setText(formatter.format(physicEnvironment.getGravity()));
		physicEnvironment.setPause(true);

		start();
	}

	private void start()
	{
		physicEnvironment.clear();

		PhyObj temp;

		for (int i = 0; i < 30; i ++)
		{
			double size = Math.random()*20+5;
			temp = new PhyCircle(new Circle(size, Color.CADETBLUE),physicEnvironment);
			temp.getObj().relocate(Math.random()*400, (Math.random()+size)*10);
			temp.setDx(Math.random()*40-10);
			temp.setDy(Math.random()*20-5);
			temp.setMass(size);
			physicEnvironment.addNew(temp);
		}

		temp = new PhyCircle(new Circle(40, Color.RED),physicEnvironment);
		temp.getObj().relocate(Math.random()*400, (Math.random())*10);
		temp.setDx(Math.random()*40-10);
		temp.setDy(Math.random()*20-5);
		temp.setMass(999999);
		//physicEnvironment.addNew(temp);
	}

	private void renderSettings()
	{
		try
		{
			double gravity = Double.parseDouble(txtGravity.getText());
			double speed = Double.parseDouble(txtSpeed.getText());

			physicEnvironment.setGravity(gravity);
			physicEnvironment.setSpeed(speed);
		}
		catch (Exception e)
		{
			System.out.println("Der DEPP");
		}
	}
}
