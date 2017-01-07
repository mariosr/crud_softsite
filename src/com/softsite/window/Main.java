package com.softsite.window;

import java.sql.SQLException;
import java.util.List;

import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Radio;
import totalcross.ui.RadioGroupController;
import totalcross.ui.Spacer;
import totalcross.ui.Toast;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.util.InvalidDateException;

import com.softsite.auxiliaries.FieldsValidator;
import com.softsite.auxiliaries.Gender;
import com.softsite.model.Employee;
import com.softsite.model.Manager;
import com.softsite.model.Person;
import com.softsite.service.PersonService;

public class Main extends MainWindow {

	private Edit edName, edBorn, edCpf;
	private String cpf, name, birthday;
	private Integer roleType;
	private Gender sex;
	private RadioGroupController rgGender;
	private RadioGroupController rgRole;
	private FieldsValidator fieldsValidator;
	private Button btInsert, btClear, btList,btRemove;
	private int heightDefault = 30;
	private int widthDefault = 300;
	private Person funcionario;
	private PersonService personService = new PersonService();

	public Main() {
		super("SOFTSITE", VERTICAL_GRADIENT);
		gradientTitleStartColor = 0;
		gradientTitleEndColor = 0xAAAAFF;

		// sets the default user interface style to Android
		// There are others, like
		setUIStyle(Settings.Android);
		// Use font height for adjustments, not pixels
		Settings.uiAdjustmentsBasedOnFontHeight = true;
		setBackColor(Color.WHITE);
	}

	// Initialize the user interface
	public void initUI() {

		fieldsValidator = new FieldsValidator();

		add(new Label("PREENCHA AS INFORMAÇÕES"), CENTER, TOP + 50);

		add(new Label("Nome: "), LEFT + 50, AFTER);
		add(edName = new Edit(), LEFT + 50, AFTER, widthDefault, heightDefault);

		add(new Label("Data de Nascimento"), LEFT + 50, AFTER + 30);
		add(edBorn = new Edit(), LEFT + 50, AFTER, widthDefault, heightDefault);
		edBorn.setMode(Edit.DATE);

		add(new Label("Sexo"), LEFT + 50, AFTER + 30);
		rgGender = new RadioGroupController();
		Radio radioMale = new Radio(
				fieldsValidator.capitalizeFirstLetter(Gender.MASCULINO
						.toString()), rgGender);
		Radio radioFemale = new Radio(
				fieldsValidator.capitalizeFirstLetter(Gender.FEMININO
						.toString()), rgGender);
		add(radioMale, LEFT + 50, AFTER, 110, 30);
		add(radioFemale, AFTER + 50, SAME, 90, 30);
		// default option
		rgGender.setSelectedItem(radioMale);

		add(new Label("CPF"), LEFT + 50, AFTER, heightDefault, 30);
		// x y w h
		add(edCpf = new Edit(), LEFT + 50, AFTER, widthDefault, heightDefault);
		edCpf.setMode(Edit.KBD_KEYBOARD);

		add(new Label("Função"), LEFT + 50, AFTER + 30);
		rgRole = new RadioGroupController();
		Radio radioManager = new Radio(
				fieldsValidator.capitalizeFirstLetter("gerente"), rgRole);
		Radio radioEmployee = new Radio(
				fieldsValidator.capitalizeFirstLetter("empregado"), rgRole);
		add(radioManager, LEFT + 50, AFTER, 80, 30);
		add(radioEmployee, AFTER + 50, SAME, 110, 30);
		// default option
		rgRole.setSelectedItem(radioMale);

		Spacer sp = new Spacer(0, 0);
		add(sp, CENTER, BOTTOM - 550, PARENTSIZE + 10, PREFERRED);
		add(btInsert = new Button(
				fieldsValidator.capitalizeFirstLetter("cadastrar")), BEFORE,
				SAME, PARENTSIZE + 40, PREFERRED, sp);

		add(btRemove = new Button(fieldsValidator.capitalizeFirstLetter("remover")),
				LEFT+90, AFTER + 175, PARENTSIZE +40, PREFERRED, sp);
		add(btClear = new Button(
				fieldsValidator.capitalizeFirstLetter("limpar")), AFTER, SAME,
				PARENTSIZE + 40, PREFERRED, sp);
		add(btList = new Button(fieldsValidator.capitalizeFirstLetter("listar")),
				AFTER, AFTER + 170, PARENTSIZE + 40, PREFERRED, sp);

		btInsert.setBackColor(Color.GREEN);
		btClear.setBackColor(Color.PINK);
		btList.setBackColor(Color.BLUE);
		btRemove.setBackColor(Color.RED);

		if (Settings.onJavaSE || Settings.platform.equals(Settings.WIN32))
			add(new Label("Pressione f11 para abrir o teclado virtual."),
					CENTER, BOTTOM);

		Toast.posY = CENTER;

	}

	public void onEvent(Event e) {

		try {
			switch (e.type) {
			case ControlEvent.PRESSED:
				if (e.target == btClear)
					clear();
				else if (e.target == btInsert) {
					doInsert();
				} else if (e.target == btList) {
					listPersons();
				}
				else if (e.target == btRemove) {
					removePerson();
				}
				break;
			}
		} catch (Exception ee) {
			MessageBox.showException(ee, true);
		}
	}

	public void removePerson(){
		
		RemoverPessoa c = new RemoverPessoa();
		swap(c);
		
	}
	
	public void listPersons() {
		List<Person> funcionarios = personService.listarPessoas();
		ListarPessoas c = new ListarPessoas(funcionarios);
		swap(c);
	}

	public void doInsert() throws SQLException, InvalidDateException {
		if (edName.getLength() == 0 || edBorn.getLength() == 0
				|| edCpf.getLength() == 0)
			Toast.show("Por favor, preencha todos os dados.", 2000);
		else if (fieldsValidator.validarCpf(edCpf.getText()) == false) {
			Toast.show("Por favor, digite um CPF válido.", 2000);
		} else if (fieldsValidator.apenasLetras(edName.getText()) == false) {
			Toast.show("Por favor, digite apenas letras no seu nome.", 2000);
		} else {
			if (edCpf.getTrimmedLength() > 0)
				cpf = edCpf.getText();
			if (edName.getTrimmedLength() > 0)
				name = edName.getText();
			if (edBorn.getTrimmedLength() > 0)
				birthday = edBorn.getText();
			int male = rgGender.getSelectedIndex();
			if (male == 0) sex = Gender.MASCULINO;
			else if(male == 1) sex = Gender.FEMININO;

			roleType = rgRole.getSelectedIndex();
			if (roleType == 0) {
				funcionario = new Manager();
				funcionario.setName(name);
				funcionario.setCpf(cpf);
				funcionario.setSex(sex);
				funcionario.setBirthday(birthday);
			} else if (roleType == 1) {
				funcionario = new Employee();
				funcionario.setName(name);
				funcionario.setCpf(cpf);
				funcionario.setSex(sex);
				funcionario.setBirthday(birthday);
			}
			personService.salvarPessoa(funcionario);
			clear();
			Toast.show("Cadastro salvo com sucesso.", 2000);
		}
	}
}