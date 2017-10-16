package mainProgram;
import javafx.scene.control.Tab;

public class Page extends Tab{
	
	protected String name;
	
	public Page(String name) {
		this.name = name;
		this.setText(name);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
}
