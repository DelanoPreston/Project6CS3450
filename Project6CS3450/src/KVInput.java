import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KVInput extends KeyAdapter{
	
	Adventure a;
	
	public KVInput(Adventure adventure){
		a = adventure;
	}
	public void keyPressed(KeyEvent e){
		a.keyPressed(e);
	}
	/*public void keyReleased(KeyEvent e){
		a.keyReleased(e);
	}*/
}
