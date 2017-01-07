package com.softsite.window;

import java.util.List;

import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Grid;
import totalcross.ui.Label;
import totalcross.ui.Spacer;
import totalcross.ui.Toast;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.gfx.Rect;

import com.softsite.auxiliaries.FieldsValidator;
import com.softsite.model.Manager;
import com.softsite.model.Person;

public class ListarPessoas extends Container {

	private List<Person> pessoas;
	private Button btBack;
	private FieldsValidator fieldsValidator;

	public ListarPessoas(List<Person> pessoas) {
		this.pessoas = pessoas;
		this.fieldsValidator = new FieldsValidator();
	}

	public void onEvent(Event e) {

		try {
			switch (e.type) {
			case ControlEvent.PRESSED:
				if (e.target == btBack){
					removeAll();
					sendToBack();
				}
				break;
			}
		} catch (Exception ee) {
			MessageBox.showException(ee, true);
		}
	}

	public void initUI() {
		add(new Label("Total cadastrados: " + pessoas.size()), LEFT + 50,
				AFTER + 30);
		
		Rect r = getClientRect();
		String []gridCaptions = {" NOME "," CPF "," SEXO "," FUNÇÃO "};
		int gridWidths[] = 
		{
			-25, // negative numbers are percentage of width
			fm.stringWidth(" 05079337531 "),
			-5,
			-20
		};
		int gridAligns[] = { LEFT, CENTER, CENTER, CENTER };
		Grid grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
		add(grid, LEFT+30,TOP+158,r.width - 10,r.height - 115);
		grid.secondStripeColor = Color.getRGB(235,235,235);

		String data[][] = new String[pessoas.size()][4]; 
		for(int i = 0; i < pessoas.size(); i++){
			data[i][0] = pessoas.get(i).getName();
			data[i][1] = pessoas.get(i).getCpf();
			data[i][2] = pessoas.get(i).getSex().toString().substring(0, 1);
			if (pessoas.get(i) instanceof Manager) {
				data[i][3] = "Gerente";
			}else{
				data[i][3] = "Empregado";
			}
			
		}
			
		grid.setItems(data);  
		
		Spacer sp = new Spacer(0, 0);
		add(sp, CENTER, BOTTOM - 510, 0, PREFERRED);
		add(btBack = new Button(fieldsValidator.capitalizeFirstLetter("Voltar")), LEFT+185, SAME,
				PARENTSIZE + 80, PREFERRED, sp);

		btBack.setBackColor(Color.YELLOW);
		
		Toast.posY = CENTER;
		
	}

	public List<Person> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Person> pessoas) {
		this.pessoas = pessoas;
	}

}
