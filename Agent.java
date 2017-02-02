import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.*;
import javax.swing.SwingUtilities;

class Agent {

	MyPlanner p;
	MyState start;
	MyState goal;
	int click;
	TreeSet<MyState> visited;

	Agent()
	{
		start = new MyState(0, null);
		goal = new MyState(0, null);
		goal.state[0] = 100;
		goal.state[1] = 100;
		click = 0;
		}


	void drawPlan(Graphics g, Model m) {


		if(!reachedGoal(m))
		{

			//draw path
			Stack<MyState> displayPath = p.path;
			MyState current = new MyState(0, null);
			MyState next = new MyState(0, null);
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(Color.red);
			while(displayPath.size() > 2)
			{
				current = displayPath.pop();
				next = displayPath.pop();
				g2.setStroke(new BasicStroke(3));
				g2.drawLine(current.state[0], current.state[1], next.state[0], next.state[1]);
			}


			//draw fronteir
			PriorityQueue<MyState> fronteir = p.frontier;
			int radius = 3;
			if(click == 1)
				g.setColor(Color.cyan);

			if(click == 3)
				g.setColor(Color.magenta);

			while(fronteir.size() > 0)
			{
				MyState fronteirState = fronteir.poll();
				int x = fronteirState.state[0] - radius;
				int y = fronteirState.state[1] - radius;

				g.drawOval(x, y, radius*2, radius*2 );
				g.fillOval(x, y, radius*2, radius*2 );
			}
		}
	}

	boolean reachedGoal(Model m)
	{
		if(goal.state[0] == m.getX() && goal.state[1] == m.getY())
			return true;

		return false;
	}

	void updateGoal(int destX, int destY)
	{
		goal.state[0] = destX;
		goal.state[1] = destY;
	}



	void update(Model m)
	{

		p = new MyPlanner(m);
		Controller c = m.getController();
		StateComparator comp = new StateComparator();
		visited = new TreeSet(comp);

		while(true)
		{
				if(!reachedGoal(m))
				{

					start.state[0] = (int) m.getX();
					start.state[1] = (int) m.getY();


					if(click == 1)
					{
							start = findNextMove(p.UCS(start, goal));
							m.setDestination(start.state[0], start.state[1]);

					}
					else if(click == 3)
					{

							start = findNextMove(p.aStar(start, goal));
							m.setDestination(start.state[0], start.state[1]);


					}



				}

				MouseEvent e = c.nextMouseEvent();
				if(e == null)
				{
					break;
				}
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					click = 1;
				}
				else if(e.getButton() == MouseEvent.BUTTON3)
				{
					click = 3;
				}
				updateGoal(e.getX(), e.getY());
		}

	}

	public MyState findNextMove(MyState f)
	{
		Stack<MyState> s = new Stack<>();
		MyState current = f;
		while(current != null)
		{
			s.push(current);
			current = current.parent;
		}
		if(!s.empty())
			current = s.pop();
		if(!s.empty())
			current =  s.pop();


		if(current == null)
		System.out.println("prob");

		return current;
	}

	public static void main(String[] args) throws Exception
	{
		Controller.playGame();
	}
}
