package com.softsite.window;

import java.util.List;

import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Spacer;
import totalcross.ui.Toast;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;

import com.softsite.auxiliaries.FieldsValidator;
import com.softsite.model.Person;
import com.softsite.service.PersonService;

public class RemoverPessoa extends Container {

	private List<Person> pessoas;
	private Button btBack, btConfirm;
	private Edit edCpf;
	private FieldsValidator fieldsValidator;
	private int heightDefault = 30;
	private int widthDefault = 300;

	public RemoverPessoa() {
		this.fieldsValidator = new FieldsValidator();
	}

	public Boolean removerFuncionario(String cpf) {
		PersonService personService = new PersonService();
		Toast.show("Usuário "+cpf+" removido com sucesso.", 3000);
		return personService.removerPessoa(cpf);
	}

	@SuppressWarnings("static-access")
	public void onEvent(Event e) {

		try {
			switch (e.type) {
			case ControlEvent.PRESSED:
				if (e.target == btBack) {
					removeAll();
					sendToBack();
				} else if (e.target == btConfirm) {
					if (edCpf.getLength() == 0)
						Toast.show("Por favor, digite o CPF.", 2000);
					else if (fieldsValidator.validarCpf(edCpf.getText()) == false)
						Toast.show("Por favor, digite um CPF válido.", 2000);
					else {
						removerFuncionario(edCpf.getText());
					}
				}
				break;
			}
		} catch (Exception ee) {
			MessageBox.showException(ee, true);
		}
	}

	public void initUI() {
		add(new Label("Digite o CPF do funcionário: "), LEFT + 50, AFTER + 30);

		add(edCpf = new Edit(), LEFT + 50, AFTER + 50, widthDefault,
				heightDefault);
		edCpf.setMode(Edit.KBD_KEYBOARD);

		Spacer sp = new Spacer(0, 0);
		add(sp, CENTER, BOTTOM - 510, 0, PREFERRED);
		add(btConfirm = new Button(fieldsValidator.capitalizeFirstLetter("confirmar")),
				LEFT + 185, BEFORE - 50, PARENTSIZE + 80, PREFERRED, sp);
		add(btBack = new Button(fieldsValidator.capitalizeFirstLetter("voltar")),
				LEFT + 185, SAME, PARENTSIZE + 80, PREFERRED, sp);

		Toast.posY = CENTER;

		btBack.setBackColor(Color.YELLOW);
		btConfirm.setBackColor(Color.GREEN);
		
	}

	public List<Person> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Person> pessoas) {
		this.pessoas = pessoas;
	}

}
